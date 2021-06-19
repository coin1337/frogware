package fail.mercury.client.client.modules.combat;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.api.util.InventoryUtil;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketUpdateHealth;

@ModuleManifest(
   label = "AutoTotem",
   category = Category.COMBAT,
   fakelabel = "Auto Totem",
   description = "Automatically replaces offhand with totems."
)
public class AutoTotem extends Module {
   @Property("Health")
   @Clamp(
      minimum = "0.5",
      maximum = "10"
   )
   public double health = 10.0D;
   @Property("Smart")
   public boolean smart = false;
   private boolean predicted = false;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (this.smart) {
         if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof SPacketUpdateHealth) {
            SPacketUpdateHealth packet = (SPacketUpdateHealth)event.getPacket();
            if (mc.field_71439_g.func_110143_aJ() - packet.func_149332_c() <= 0.0F) {
               this.predicted = true;
            }
         }

      }
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (event.getTiming().equals(EventTiming.PRE) && (mc.field_71462_r == null || mc.field_71462_r instanceof GuiInventory) && ((double)mc.field_71439_g.func_110143_aJ() <= this.health * 2.0D || this.smart && this.predicted)) {
         if (this.predicted) {
            ChatUtil.print("predicted");
         }

         Item offhand = mc.field_71439_g.func_184592_cb().func_77973_b();
         if (InventoryUtil.getItemCount(mc.field_71439_g.field_71069_bz, Items.field_190929_cY) > 0 && !offhand.equals(Items.field_190929_cY)) {
            InventoryUtil.swap(InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, Items.field_190929_cY), 45);
         }

         if (this.predicted) {
            this.predicted = false;
         }
      }

   }
}
