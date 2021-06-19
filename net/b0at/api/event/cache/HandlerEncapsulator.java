package net.b0at.api.event.cache;

import com.esotericsoftware.reflectasm.MethodAccess;
import java.lang.reflect.Method;
import java.util.NavigableSet;
import java.util.Objects;
import net.b0at.api.event.types.EventPriority;
import net.b0at.api.event.types.EventTiming;

public class HandlerEncapsulator<T> {
   protected Object listener;
   protected Method method;
   private EventPriority priority;
   protected NavigableSet<HandlerEncapsulator<T>> parentSet;
   protected MethodAccess methodAccess;
   protected int methodIndex;

   public HandlerEncapsulator(Object listener, Method method, int methodIndex, EventPriority priority, NavigableSet<HandlerEncapsulator<T>> parentSet) {
      this.listener = listener;
      this.method = method;
      this.priority = priority;
      this.parentSet = parentSet;
      this.methodIndex = methodIndex;
      method.setAccessible(true);
      this.methodAccess = MethodAccess.get(this.listener.getClass());
   }

   public void invoke(T event, EventTiming timing) {
      this.methodAccess.invoke(this.listener, this.methodIndex, event);
   }

   public final EventPriority getPriority() {
      return this.priority;
   }

   public void setEnabled(boolean enabled) {
      if (enabled) {
         this.parentSet.add(this);
      } else {
         this.parentSet.remove(this);
      }

   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj.getClass() != HandlerEncapsulator.class) {
         return false;
      } else {
         HandlerEncapsulator other = (HandlerEncapsulator)obj;
         return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
      }
   }

   public String toString() {
      return String.format("%s@%s#%s@%s(%s)", this.listener.getClass().getName(), Integer.toHexString(this.listener.hashCode()), this.method.getName(), Integer.toHexString(this.method.hashCode()), this.method.getParameters()[0].getType().getName());
   }
}
