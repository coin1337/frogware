package fail.mercury.client.api.mixin;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.util.Rotation;
import fail.mercury.client.client.events.RotationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderLivingBase.class})
public class MixinRenderLivingBase {
   private float yaw;
   private float pitch;
   private float yawOffset;
   private float prevYaw;
   private float prevPitch;
   private float prevYawOffset;

   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void preHeadRotation(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
      if (entity == Minecraft.func_71410_x().field_71439_g) {
         this.yaw = entity.field_70759_as;
         this.pitch = entity.field_70125_A;
         this.prevYaw = entity.field_70758_at;
         this.prevPitch = entity.field_70127_C;
         this.yawOffset = entity.field_70761_aq;
         this.prevYawOffset = entity.field_70760_ar;
         RotationEvent event = new RotationEvent(new Rotation(this.yaw, this.pitch), new Rotation(this.prevYaw, this.prevPitch), this.yawOffset, this.prevYawOffset);
         Mercury.INSTANCE.getEventManager().fireEvent(event);
         entity.field_70759_as = event.getRotation().getYaw();
         entity.field_70125_A = event.getRotation().getPitch();
         entity.field_70758_at = event.getPrevRotation().getYaw();
         entity.field_70127_C = event.getPrevRotation().getPitch();
         entity.field_70761_aq = event.getRenderYawOffset();
         entity.field_70760_ar = event.getPrevRenderYawOffset();
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("TAIL")}
   )
   private void postHeadRotation(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
      if (entity == Minecraft.func_71410_x().field_71439_g) {
         entity.field_70759_as = this.yaw;
         entity.field_70125_A = this.pitch;
         entity.field_70758_at = this.prevYaw;
         entity.field_70127_C = this.prevPitch;
         entity.field_70761_aq = this.yawOffset;
         entity.field_70760_ar = this.prevYawOffset;
      }

   }
}
