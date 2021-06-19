package fail.mercury.client.api.mixin;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.util.Location;
import fail.mercury.client.api.util.Util;
import fail.mercury.client.client.events.JumpEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityLivingBase.class})
public class MixinEntityLivingBase implements Util {
   private Location location;

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onJump(CallbackInfo callbackInfo) {
      JumpEvent event = new JumpEvent(this.getLocation());
      Mercury.INSTANCE.getEventManager().fireEvent(event);
      if (event.isCancelled()) {
         callbackInfo.cancel();
      }

   }

   @Redirect(
      method = {"jump"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/Entity;motionX:D"
)
   )
   private double jumpMotionX(Entity player) {
      return this.location.getX();
   }

   @Redirect(
      method = {"jump"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/Entity;motionY:D"
)
   )
   private double jumpMotionY(Entity player) {
      return this.location.getY();
   }

   @Redirect(
      method = {"jump"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/Entity;motionZ:D"
)
   )
   private double jumpMotionZ(Entity player) {
      return this.location.getZ();
   }

   public Location getLocation() {
      if (this.location == null) {
         this.location = new Location(mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70179_y);
      }

      this.location.setX(mc.field_71439_g.field_70159_w);
      this.location.setY(mc.field_71439_g.field_70181_x);
      this.location.setZ(mc.field_71439_g.field_70179_y);
      return this.location;
   }
}
