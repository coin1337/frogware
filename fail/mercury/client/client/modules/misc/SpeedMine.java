package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.DamageBlockEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "SpeedMine",
   aliases = {"FastBreak"},
   fakelabel = "Speed Mine",
   category = Category.MISC,
   description = "Mines blocks faster."
)
public class SpeedMine extends Module {
   @Property("Packet")
   public boolean packet = true;
   @Property("Damage")
   public boolean damage = false;
   @Property("Effect")
   public boolean effect = false;

   public void onDisable() {
      mc.field_71439_g.func_184589_d(MobEffects.field_76422_e);
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      mc.field_71442_b.field_78781_i = 0;
      if (this.effect) {
         PotionEffect effect = new PotionEffect(MobEffects.field_76422_e, 80950, 1, false, false);
         mc.field_71439_g.func_70690_d(new PotionEffect(effect));
      }

   }

   @EventHandler
   public void onBreak(DamageBlockEvent event) {
      if (this.canBreak(event.getPos())) {
         if (this.packet) {
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));
            event.setCancelled(true);
         }

         if (this.damage && mc.field_71442_b.field_78770_f >= 0.7F) {
            mc.field_71442_b.field_78770_f = 1.0F;
         }

         if (this.effect) {
         }
      }

   }

   private boolean canBreak(BlockPos pos) {
      IBlockState blockState = mc.field_71441_e.func_180495_p(pos);
      Block block = blockState.func_177230_c();
      return block.func_176195_g(blockState, mc.field_71441_e, pos) != -1.0F;
   }
}
