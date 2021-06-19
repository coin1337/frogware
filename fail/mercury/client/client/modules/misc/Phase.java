package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.CollisionBoxEvent;
import fail.mercury.client.client.events.InsideBlockRenderEvent;
import fail.mercury.client.client.events.PushEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "Phase",
   category = Category.MISC
)
public class Phase extends Module {
   @Property("Speed")
   @Clamp(
      minimum = "0.1"
   )
   public double speed = 10.0D;
   @Property("Mode")
   @Mode({"Down", "Destroy", "NCP"})
   public String mode = "Vertical";
   public TimerUtil timer = new TimerUtil();

   @EventHandler
   public void onRender(InsideBlockRenderEvent event) {
      event.setCancelled(true);
   }

   @EventHandler
   public void onPush(PushEvent event) {
      event.setCancelled(true);
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.canPhase()) {
         if (this.mode.equalsIgnoreCase("down")) {
            double posX = mc.field_71439_g.field_70165_t;
            double posY = mc.field_71439_g.field_70163_u;
            double posZ = mc.field_71439_g.field_70161_v;
            boolean ground = mc.field_71439_g.field_70122_E;
            if (mc.field_71439_g.func_70093_af() && mc.field_71462_r == null) {
               MotionUtil.setSpeed(mc.field_71439_g, 0.0D);
               mc.field_71439_g.func_70634_a(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.1D, mc.field_71439_g.field_70161_v);
               mc.field_71439_g.field_70181_x = -this.speed * 2.0D;
            }
         }

         if (this.mode.equalsIgnoreCase("destroy")) {
            double[] dir = MotionUtil.forward(1.0D);
            if (mc.field_71439_g.field_70123_F) {
               mc.field_71441_e.func_175655_b(new BlockPos(mc.field_71439_g.field_70165_t + dir[0], mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + dir[1]), false);
               mc.field_71441_e.func_175655_b(new BlockPos(mc.field_71439_g.field_70165_t + dir[0], mc.field_71439_g.field_70163_u + 1.0D, mc.field_71439_g.field_70161_v + dir[1]), false);
            }

            if (MotionUtil.isMoving(mc.field_71439_g) && mc.field_71439_g.field_70122_E) {
               MotionUtil.setSpeed(mc.field_71439_g, 0.23D);
            }
         }

      }
   }

   @EventHandler
   public void onCollision(CollisionBoxEvent event) {
   }

   public boolean canPhase() {
      return !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab();
   }
}
