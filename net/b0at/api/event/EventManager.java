package net.b0at.api.event;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;
import net.b0at.api.event.cache.HandlerEncapsulator;
import net.b0at.api.event.cache.HandlerEncapsulatorWithTiming;
import net.b0at.api.event.exceptions.ListenerAlreadyRegisteredException;
import net.b0at.api.event.exceptions.ListenerNotAlreadyRegisteredException;
import net.b0at.api.event.profiler.IEventProfiler;
import net.b0at.api.event.sorting.HandlerEncapsulatorSorter;
import net.b0at.api.event.types.EventTiming;

public final class EventManager<T> {
   private final Comparator<HandlerEncapsulator<T>> ENCAPSULATOR_SORTER = new HandlerEncapsulatorSorter();
   private IEventProfiler<T> eventProfiler = new IEventProfiler<T>() {
   };
   private Map<EventTiming, Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>>> eventEncapsulatorMap = new HashMap();
   private Set<Object> discoveredListeners = new HashSet();
   private Map<Object, Boolean> listenerPersistentStates = new HashMap();
   private Map<Object, Boolean> listenerNonPersistentStates = new HashMap();
   private Map<Object, Set<HandlerEncapsulator<T>>> persistentCache = new HashMap();
   private Map<Object, Set<HandlerEncapsulator<T>>> nonPersistentCache = new HashMap();
   private int registeredListenerCount = 0;
   private Consumer<Exception> exceptionHook;
   private final Class<T> BASE_CLASS;

   public EventManager(Class<T> baseClass) {
      this.BASE_CLASS = baseClass;
      this.eventEncapsulatorMap.put(EventTiming.PRE, new HashMap());
      this.eventEncapsulatorMap.put(EventTiming.POST, new HashMap());
   }

   public void registerListener(Object listener, boolean onlyAddPersistent) throws ListenerAlreadyRegisteredException {
      Map<Object, Boolean> listenerStates = onlyAddPersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
      Boolean state = (Boolean)listenerStates.get(listener);
      if (state == Boolean.TRUE) {
         throw new ListenerAlreadyRegisteredException(listener);
      } else {
         if (!this.discoveredListeners.contains(listener)) {
            this.discoverEventHandlers(listener);
         }

         listenerStates.put(listener, Boolean.TRUE);
         Set<HandlerEncapsulator<T>> encapsulatorSet = onlyAddPersistent ? (Set)this.persistentCache.get(listener) : (Set)this.nonPersistentCache.get(listener);
         Iterator var6 = encapsulatorSet.iterator();

         while(var6.hasNext()) {
            HandlerEncapsulator<T> encapsulator = (HandlerEncapsulator)var6.next();
            encapsulator.setEnabled(true);
         }

         this.registeredListenerCount += encapsulatorSet.size();
         this.eventProfiler.onRegisterListener(listener, onlyAddPersistent);
      }
   }

   public void registerListener(Object listener) throws ListenerAlreadyRegisteredException {
      this.registerListener(listener, false);
   }

   private void discoverEventHandlers(Object listener) {
      this.eventProfiler.preListenerDiscovery(listener);
      Set<HandlerEncapsulator<T>> persistentSet = new HashSet();
      Set<HandlerEncapsulator<T>> nonPersistentSet = new HashSet();
      this.discoveredListeners.add(listener);
      this.persistentCache.put(listener, persistentSet);
      this.nonPersistentCache.put(listener, nonPersistentSet);
      int methodIndex = 0;

      for(Class clazz = listener.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
         Method[] var6 = clazz.getDeclaredMethods();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Method method = var6[var8];
            if ((method.getModifiers() & 2) == 0) {
               if ((method.getParameterCount() == 1 || method.getParameterCount() == 2 && EventTiming.class.isAssignableFrom(method.getParameterTypes()[1])) && method.isAnnotationPresent(EventHandler.class) && this.BASE_CLASS.isAssignableFrom(method.getParameterTypes()[0])) {
                  boolean includesTimingParam = method.getParameterCount() == 2;
                  Class<? extends T> eventClass = method.getParameterTypes()[0];
                  EventHandler eventHandler = (EventHandler)method.getAnnotation(EventHandler.class);
                  Object encapsulator;
                  NavigableSet preSet;
                  if (includesTimingParam) {
                     preSet = this.getOrCreateNavigableSet((Map)this.eventEncapsulatorMap.get(EventTiming.PRE), eventClass);
                     NavigableSet<HandlerEncapsulator<T>> postSet = this.getOrCreateNavigableSet((Map)this.eventEncapsulatorMap.get(EventTiming.POST), eventClass);
                     encapsulator = new HandlerEncapsulatorWithTiming(listener, method, methodIndex, eventHandler.priority(), preSet, postSet);
                  } else {
                     preSet = this.getOrCreateNavigableSet((Map)this.eventEncapsulatorMap.get(eventHandler.timing()), eventClass);
                     encapsulator = new HandlerEncapsulator(listener, method, methodIndex, eventHandler.priority(), preSet);
                  }

                  Set<HandlerEncapsulator<T>> encapsulatorSet = eventHandler.persistent() ? persistentSet : nonPersistentSet;
                  encapsulatorSet.add(encapsulator);
               }

               ++methodIndex;
            }
         }
      }

      this.eventProfiler.postListenerDiscovery(listener);
   }

   private NavigableSet<HandlerEncapsulator<T>> getOrCreateNavigableSet(Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>> encapsulatorMap, Class<? extends T> eventClass) {
      NavigableSet<HandlerEncapsulator<T>> navigableSet = (NavigableSet)encapsulatorMap.get(eventClass);
      if (navigableSet == null) {
         navigableSet = new ConcurrentSkipListSet(this.ENCAPSULATOR_SORTER);
         encapsulatorMap.put(eventClass, navigableSet);
      }

      return (NavigableSet)navigableSet;
   }

   public void deregisterListener(Object listener, boolean onlyRemovePersistent) throws ListenerNotAlreadyRegisteredException {
      Map<Object, Boolean> listenerStates = onlyRemovePersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
      Boolean state = (Boolean)listenerStates.get(listener);
      if (state != Boolean.TRUE) {
         throw new ListenerNotAlreadyRegisteredException(listener);
      } else {
         listenerStates.put(listener, Boolean.FALSE);
         Set<HandlerEncapsulator<T>> encapsulatorSet = onlyRemovePersistent ? (Set)this.persistentCache.get(listener) : (Set)this.nonPersistentCache.get(listener);
         Iterator var6 = encapsulatorSet.iterator();

         while(var6.hasNext()) {
            HandlerEncapsulator<T> encapsulator = (HandlerEncapsulator)var6.next();
            encapsulator.setEnabled(false);
         }

         this.registeredListenerCount -= encapsulatorSet.size();
         this.eventProfiler.onDeregisterListener(listener, onlyRemovePersistent);
      }
   }

   public void deregisterListener(Object listener) throws ListenerNotAlreadyRegisteredException {
      this.deregisterListener(listener, false);
   }

   public void deregisterAll() {
      ((Map)this.eventEncapsulatorMap.get(EventTiming.PRE)).values().forEach(Set::clear);
      ((Map)this.eventEncapsulatorMap.get(EventTiming.POST)).values().forEach(Set::clear);
      this.listenerPersistentStates.keySet().forEach((listener) -> {
         Boolean var10000 = (Boolean)this.listenerPersistentStates.put(listener, Boolean.FALSE);
      });
      this.listenerNonPersistentStates.keySet().forEach((listener) -> {
         Boolean var10000 = (Boolean)this.listenerNonPersistentStates.put(listener, Boolean.FALSE);
      });
      this.registeredListenerCount = 0;
      this.eventProfiler.onDeregisterAll();
   }

   public void cleanup() {
      ((Map)this.eventEncapsulatorMap.get(EventTiming.PRE)).clear();
      ((Map)this.eventEncapsulatorMap.get(EventTiming.POST)).clear();
      this.discoveredListeners.clear();
      this.listenerPersistentStates.clear();
      this.listenerNonPersistentStates.clear();
      this.persistentCache.clear();
      this.nonPersistentCache.clear();
      this.registeredListenerCount = 0;
      this.eventProfiler.onCleanup();
   }

   public int getRegisteredListenerCount() {
      return this.registeredListenerCount;
   }

   public <E extends T> E fireEvent(E event) {
      return this.fireEvent(event, EventTiming.PRE);
   }

   public synchronized <E extends T> E fireEvent(E event, EventTiming timing) {
      NavigableSet<HandlerEncapsulator<T>> encapsulatorSet = (NavigableSet)((Map)this.eventEncapsulatorMap.get(timing)).get(event.getClass());
      if (encapsulatorSet != null && !encapsulatorSet.isEmpty()) {
         this.eventProfiler.preFireEvent(event, timing, encapsulatorSet);
         Iterator var4 = encapsulatorSet.iterator();

         while(true) {
            if (!var4.hasNext()) {
               this.eventProfiler.postFireEvent(event, timing, encapsulatorSet);
               break;
            }

            HandlerEncapsulator encapsulator = (HandlerEncapsulator)var4.next();

            try {
               encapsulator.invoke(event, timing);
            } catch (Exception var7) {
               if (this.exceptionHook == null) {
                  throw var7;
               }

               this.exceptionHook.accept(var7);
            }
         }
      } else {
         this.eventProfiler.onSkippedEvent(event, timing);
      }

      return event;
   }

   public void setEventProfiler(IEventProfiler<T> eventProfiler) {
      this.eventProfiler = eventProfiler;
   }

   public void setExceptionHook(Consumer<Exception> exceptionHook) {
      this.exceptionHook = exceptionHook;
   }
}
