package fail.mercury.client.client.modules.movement;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.EntityUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.client.events.CollisionBoxEvent;
import fail.mercury.client.client.events.JumpEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;

@ModuleManifest(
   label = "Jesus",
   category = Category.MOVEMENT,
   aliases = {"WaterWalk", "Basilisk"}
)
public class Jesus extends Module {
   @Property("Mode")
   @Mode({"Solid"})
   public String mode = "Solid";
   @Property("Criticals")
   public boolean criticals = false;

   @EventHandler
   public void onBoundingBox(CollisionBoxEvent event) {
      if (mc.field_71439_g != null) {
         if (event.getBlock() instanceof BlockLiquid && event.getEntity() == mc.field_71439_g && !EntityUtil.isInLiquid() && mc.field_71439_g.field_70143_R < 3.0F && !mc.field_71439_g.func_70093_af()) {
            event.setAABB(Block.field_185505_j);
         }

      }
   }

   @EventHandler
   public void onJump(JumpEvent event) {
      if (EntityUtil.isColliding(0.0D, -0.8D, 0.0D) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
         MotionUtil.setSpeed(mc.field_71439_g, MotionUtil.getBaseMoveSpeed() - 0.24D);
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (!mc.field_71474_y.field_74311_E.func_151470_d()) {
         if (EntityUtil.isInLiquid() && event.getTiming().equals(EventTiming.PRE)) {
            mc.field_71439_g.field_70181_x = 0.1D;
         }

         if (this.mode.equalsIgnoreCase("solid") && event.getTiming().equals(EventTiming.PRE) && EntityUtil.isColliding(0.0D, -0.1D, 0.0D) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
            event.getLocation().setY(mc.field_71439_g.field_70163_u + (mc.field_71439_g.field_70173_aa % 2 == 0 ? 0.01D : -0.01D));
            event.getLocation().setOnGround(mc.field_71439_g.field_70173_aa % 2 != 0);
         }

      }
   }
}
