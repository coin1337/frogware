package fail.mercury.client.client.events;

import net.b0at.api.event.Event;

public class FullScreenEvent extends Event {
   private float width;
   private float height;

   public FullScreenEvent(float width, float height) {
      this.width = width;
      this.height = height;
   }

   public float getWidth() {
      return this.width;
   }

   public void setWidth(float width) {
      this.width = width;
   }

   public float getHeight() {
      return this.height;
   }

   public void setHeight(float height) {
      this.height = height;
   }
}
