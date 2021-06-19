package fail.mercury.client.client.events;

import net.b0at.api.event.Event;
import net.minecraft.entity.Entity;

public class PlayerEvent extends Event {
   public Entity entity;
   public PlayerEvent.Type type;

   public PlayerEvent(PlayerEvent.Type type, Entity entity) {
      this.type = type;
      this.entity = entity;
   }

   public PlayerEvent.Type getType() {
      return this.type;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public static enum Type {
      ENTERING,
      EXITING;
   }
}
