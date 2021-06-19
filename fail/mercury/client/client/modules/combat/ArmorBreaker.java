package fail.mercury.client.client.modules.combat;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.PacketEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraft.util.EnumHand;

@ModuleManifest(
   label = "ArmorBreaker",
   fakelabel = "Armor Breaker",
   category = Category.COMBAT,
   aliases = {"Dura", "Durability"},
   description = "Lowers armor durability faster."
)
public class ArmorBreaker extends Module {
   public TimerUtil timer = new TimerUtil();

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketUseEntity) {
         CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
         if (packet.func_149565_c() == Action.ATTACK && this.timer.hasReached(200L)) {
            event.setCancelled(true);
            this.swap(9, mc.field_71439_g.field_71071_by.field_70461_c);
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(packet.func_149564_a(mc.field_71441_e)));
            this.swap(9, mc.field_71439_g.field_71071_by.field_70461_c);
            this.timer.reset();
         }
      }

   }

   private void swap(int slot, int hotbarNum) {
      mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, hotbarNum, ClickType.SWAP, mc.field_71439_g);
   }
}
