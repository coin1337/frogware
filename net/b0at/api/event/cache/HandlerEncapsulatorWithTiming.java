package net.b0at.api.event.cache;

import java.lang.reflect.Method;
import java.util.NavigableSet;
import java.util.Objects;
import net.b0at.api.event.types.EventPriority;
import net.b0at.api.event.types.EventTiming;

public class HandlerEncapsulatorWithTiming<T> extends HandlerEncapsulator<T> {
   private NavigableSet<HandlerEncapsulator<T>> postParentSet;

   public HandlerEncapsulatorWithTiming(Object listener, Method method, int methodIndex, EventPriority priority, NavigableSet<HandlerEncapsulator<T>> preParentSet, NavigableSet<HandlerEncapsulator<T>> postParentSet) {
      super(listener, method, methodIndex, priority, preParentSet);
      this.postParentSet = postParentSet;
   }

   public void invoke(T event, EventTiming timing) {
      this.methodAccess.invoke(this.listener, this.methodIndex, event, timing);
   }

   public void setEnabled(boolean enabled) {
      if (enabled) {
         this.parentSet.add(this);
         this.postParentSet.add(this);
      } else {
         this.parentSet.remove(this);
         this.postParentSet.remove(this);
      }

   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj.getClass() != HandlerEncapsulatorWithTiming.class) {
         return false;
      } else {
         HandlerEncapsulatorWithTiming other = (HandlerEncapsulatorWithTiming)obj;
         return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
      }
   }

   public String toString() {
      return String.format("%s@%s#%s@%s(%s, EventPriority priority)", this.listener.getClass().getName(), Integer.toHexString(this.listener.hashCode()), this.method.getName(), Integer.toHexString(this.method.hashCode()), this.method.getParameters()[0].getType().getName());
   }
}
