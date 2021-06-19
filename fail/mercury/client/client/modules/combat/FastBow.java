package fail.mercury.client.client.modules.combat;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "FastBow",
   category = Category.COMBAT,
   fakelabel = "Fast Bow",
   description = "Shoot arrows faster"
)
public class FastBow extends Module {
   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && mc.field_71439_g.func_184587_cr() && mc.field_71439_g.func_184612_cw() >= 3) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(mc.field_71439_g.func_184600_cs()));
         mc.field_71439_g.func_184597_cx();
      }

   }
}
