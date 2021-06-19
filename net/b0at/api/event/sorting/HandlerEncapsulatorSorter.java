package net.b0at.api.event.sorting;

import java.util.Comparator;
import java.util.Objects;
import net.b0at.api.event.cache.HandlerEncapsulator;

public class HandlerEncapsulatorSorter<T> implements Comparator<HandlerEncapsulator<T>> {
   public int compare(HandlerEncapsulator<T> a, HandlerEncapsulator<T> b) {
      if (Objects.equals(a, b)) {
         return 0;
      } else if (a.getPriority().ordinal() == b.getPriority().ordinal()) {
         return Integer.compare(a.hashCode(), b.hashCode());
      } else {
         return a.getPriority().ordinal() > b.getPriority().ordinal() ? 1 : -1;
      }
   }
}
