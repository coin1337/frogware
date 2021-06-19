package fail.mercury.client.client.modules.player;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.network.play.client.CPacketPlayer.Position;

@ModuleManifest(
   label = "Revive",
   category = Category.PLAYER
)
public class Revive extends Module {
   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (event.getTiming().equals(EventTiming.PRE) && mc.field_71439_g.func_110143_aJ() <= 0.0F) {
         mc.field_71439_g.func_70606_j(20.0F);
         mc.field_71462_r = null;
         mc.field_71439_g.field_70128_L = false;
         double x = mc.field_71439_g.field_70165_t;
         double z = mc.field_71439_g.field_70161_v;

         for(int i = 0; i <= 100; ++i) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(x, 0.0D, z, mc.field_71439_g.field_70122_E));
         }

         mc.field_71439_g.func_71004_bE();
      }

   }
}
