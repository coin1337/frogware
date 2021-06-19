package fail.mercury.client.api.mixin;

import com.mojang.authlib.GameProfile;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.util.Location;
import fail.mercury.client.api.util.Rotation;
import fail.mercury.client.client.events.MotionEvent;
import fail.mercury.client.client.events.PushEvent;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {EntityPlayerSP.class},
   priority = 1001
)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
   @Shadow
   @Final
   public NetHandlerPlayClient field_71174_a;
   @Shadow
   @Final
   private double field_175172_bI;
   @Shadow
   private double field_175166_bJ;
   @Shadow
   private double field_175167_bK;
   @Shadow
   private float field_175164_bL;
   @Shadow
   private float field_175165_bM;
   @Shadow
   private boolean field_175171_bO;
   @Shadow
   private boolean field_175170_bN;
   @Shadow
   private boolean field_184841_cd;
   @Shadow
   private int field_175168_bP;
   @Shadow
   protected Minecraft field_71159_c;
   @Shadow
   private boolean field_189811_cr;
   @Shadow
   public MovementInput field_71158_b;
   private Location location;
   private Rotation rotation;

   public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
      super(worldIn, playerProfile);
   }

   @Shadow
   public abstract boolean func_70093_af();

   @Shadow
   protected abstract boolean func_175160_A();

   public void func_70091_d(MoverType type, double x, double y, double z) {
      MotionEvent event = new MotionEvent(x, y, z);
      Mercury.INSTANCE.getEventManager().fireEvent(event);
      super.func_70091_d(type, event.getX(), event.getY(), event.getZ());
   }

   @Inject(
      method = {"pushOutOfBlocks"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
      PushEvent event = new PushEvent(PushEvent.Type.BLOCK);
      Mercury.INSTANCE.getEventManager().fireEvent(event);
      if (event.isCancelled()) {
         cir.setReturnValue(false);
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onStartUpdateWalkingPlayer(CallbackInfo ci) {
      UpdateEvent event = new UpdateEvent(EventTiming.PRE, this.getLocation(), this.getRotation());
      Mercury.INSTANCE.getEventManager().fireEvent(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("RETURN")}
   )
   public void onEndUpdateWalkingPlayer(CallbackInfo ci) {
      Mercury.INSTANCE.getEventManager().fireEvent(new UpdateEvent(EventTiming.POST, this.getLocation(), this.getRotation()));
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;posX:D"
)
   )
   private double onUpdateWalkingPlayerPosX(EntityPlayerSP player) {
      return this.location.getX();
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/util/math/AxisAlignedBB;minY:D"
)
   )
   private double onUpdateWalkingPlayerMinY(AxisAlignedBB boundingBox) {
      return this.location.getY();
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;posZ:D"
)
   )
   private double onUpdateWalkingPlayerPosZ(EntityPlayerSP player) {
      return this.location.getZ();
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;onGround:Z"
)
   )
   private boolean onUpdateWalkingPlayerOnGround(EntityPlayerSP player) {
      return this.location.isOnGround();
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationYaw:F"
)
   )
   private float onUpdateWalkingPlayerRotationYaw(EntityPlayerSP player) {
      return this.rotation.getYaw();
   }

   @Redirect(
      method = {"onUpdateWalkingPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationPitch:F"
)
   )
   private float onUpdateWalkingPlayerRotationPitch(EntityPlayerSP player) {
      return this.rotation.getPitch();
   }

   public Location getLocation() {
      if (this.location == null) {
         this.location = new Location(this.field_71159_c.field_71439_g.field_70165_t, this.field_71159_c.field_71439_g.field_70163_u, this.field_71159_c.field_71439_g.field_70161_v, this.field_71159_c.field_71439_g.field_70122_E);
      }

      this.location.setX(this.field_71159_c.field_71439_g.field_70165_t);
      this.location.setY(this.field_71159_c.field_71439_g.field_70163_u);
      this.location.setZ(this.field_71159_c.field_71439_g.field_70161_v);
      this.location.setOnGround(this.field_71159_c.field_71439_g.field_70122_E);
      return this.location;
   }

   public Rotation getRotation() {
      if (this.rotation == null) {
         this.rotation = new Rotation(this.field_71159_c.field_71439_g.field_70177_z, this.field_71159_c.field_71439_g.field_70125_A);
      }

      this.rotation.setYaw(this.field_71159_c.field_71439_g.field_70177_z);
      this.rotation.setPitch(this.field_71159_c.field_71439_g.field_70125_A);
      return this.rotation;
   }
}
