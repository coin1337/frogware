package fail.mercury.client.client.modules.visual;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.RotationEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;

@ModuleManifest(
   label = "Location",
   aliases = {"Rotations"},
   description = "Shows rotations and y position in F5.",
   category = Category.VISUAL,
   hidden = true
)
public class Location extends Module {
   @Property("Rotations")
   public boolean rotations = true;
   @Property("Active")
   public boolean active = true;
   public float yaw;
   public float pitch;
   public float prevYaw;
   public float prevPitch;
   public boolean isActive;

   @EventHandler
   public void onRotate(RotationEvent event) {
      if (this.rotations) {
         if (this.active && !this.isActive) {
            return;
         }

         event.getRotation().setYaw(this.yaw);
         event.getPrevRotation().setYaw(this.prevYaw);
         event.getRotation().setPitch(this.pitch);
         event.getPrevRotation().setPitch(this.prevPitch);
         event.setRenderYawOffset(this.yaw);
         event.setPrevRenderYawOffset(this.prevYaw);
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
      this.yaw = event.getRotation().getYaw();
      this.pitch = event.getRotation().getPitch();
      if (event.getRotation().getYaw() == mc.field_71439_g.field_70177_z && event.getRotation().getPitch() == mc.field_71439_g.field_70125_A) {
         this.isActive = false;
      } else {
         this.isActive = true;
      }

   }
}
