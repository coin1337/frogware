package fail.mercury.client.client.events;

import fail.mercury.client.api.util.Rotation;
import net.b0at.api.event.Event;

public class RotationEvent extends Event {
   private Rotation rotation;
   private Rotation prevRotation;
   private float renderYawOffset;
   private float prevRenderYawOffset;

   public RotationEvent(Rotation rotation, Rotation prevRotation, float renderYawOffset, float prevRenderYawOffset) {
      this.rotation = rotation;
      this.prevRotation = prevRotation;
      this.renderYawOffset = renderYawOffset;
      this.prevRenderYawOffset = prevRenderYawOffset;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public Rotation getPrevRotation() {
      return this.prevRotation;
   }

   public float getRenderYawOffset() {
      return this.renderYawOffset;
   }

   public float getPrevRenderYawOffset() {
      return this.prevRenderYawOffset;
   }

   public void setRenderYawOffset(float renderYawOffset) {
      this.renderYawOffset = renderYawOffset;
   }

   public void setPrevRenderYawOffset(float prevRenderYawOffset) {
      this.prevRenderYawOffset = prevRenderYawOffset;
   }
}
