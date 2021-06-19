package net.b0at.api.event.profiler;

import java.util.NavigableSet;
import net.b0at.api.event.cache.HandlerEncapsulator;
import net.b0at.api.event.types.EventTiming;

public interface IEventProfiler<T> {
   default void onRegisterListener(Object listener, boolean onlyAddPersistent) {
   }

   default void onDeregisterListener(Object listener, boolean onlyRemovePersistent) {
   }

   default void preListenerDiscovery(Object listener) {
   }

   default void postListenerDiscovery(Object listener) {
   }

   default void onDeregisterAll() {
   }

   default void onCleanup() {
   }

   default void preFireEvent(T event, EventTiming timing, NavigableSet<HandlerEncapsulator<T>> handlers) {
   }

   default void postFireEvent(T event, EventTiming timing, NavigableSet<HandlerEncapsulator<T>> handlers) {
   }

   default void onSkippedEvent(T event, EventTiming timing) {
   }
}
