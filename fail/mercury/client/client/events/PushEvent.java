package fail.mercury.client.client.events;

import net.b0at.api.event.Event;

public class PushEvent extends Event {
   public PushEvent.Type type;

   public PushEvent(PushEvent.Type type) {
      this.type = type;
   }

   public PushEvent.Type getType() {
      return this.type;
   }

   public static enum Type {
      BLOCK,
      LIQUID;
   }
}
