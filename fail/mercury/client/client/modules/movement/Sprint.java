package fail.mercury.client.client.modules.movement;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.client.events.JumpEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;

@ModuleManifest(
   label = "Sprint",
   aliases = {"AutoSprint"},
   category = Category.MOVEMENT
)
public class Sprint extends Module {
   @Property("Omni")
   public boolean omni = false;

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (Mercury.INSTANCE.getModuleManager().find(Scaffold.class).isEnabled() && !Scaffold.sprint) {
         mc.field_71439_g.func_70031_b(false);
      } else if (Mercury.INSTANCE.getModuleManager().find(Scaffold.class).isEnabled() && mc.field_71474_y.field_74311_E.func_151470_d() && Scaffold.down) {
         mc.field_71439_g.func_70031_b(false);
      } else {
         if (mc.field_71439_g.func_71024_bL().func_75116_a() > 6 && this.omni) {
            if (mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F) {
               return;
            }
         } else if (!(mc.field_71439_g.field_191988_bg > 0.0F)) {
            return;
         }

         mc.field_71439_g.func_70031_b(true);
      }
   }

   @EventHandler
   public void onJump(JumpEvent event) {
      if (this.omni) {
         double[] dir = MotionUtil.forward(0.01745329238474369D);
         event.getLocation().setX(dir[0] * 0.20000000298023224D);
         event.getLocation().setZ(dir[1] * 0.20000000298023224D);
      }

   }
}
