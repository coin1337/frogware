package fail.mercury.client.client.events;

import net.b0at.api.event.Event;

public class Render3DEvent extends Event {
   private float partialTicks;

   public Render3DEvent(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
