package net.b0at.api.event;

public class Event {
   private boolean cancelled;

   public final boolean isCancelled() {
      return this.cancelled;
   }

   public final void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public final void cancel() {
      this.setCancelled(true);
   }

   public final void restore() {
      this.setCancelled(false);
   }
}
