package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import java.util.Objects;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

@ModuleManifest(
   label = "AutoFish",
   fakelabel = "Auto Fish",
   category = Category.MISC
)
public class AutoFish extends Module {
   @Property("Mode")
   @Mode({"Normal", "Lava"})
   public String mode = "Normal";
   @Property("Cast")
   public boolean cast = false;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.INCOMING)) {
         if (this.mode.equalsIgnoreCase("Normal") && event.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.func_186977_b() == SoundCategory.NEUTRAL && packet.func_186978_a() == SoundEvents.field_187609_F && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFishingRod) {
               this.click();
               if (this.cast) {
                  this.click();
               }
            }
         }

         if (this.mode.equalsIgnoreCase("Lava") && event.getPacket() instanceof SPacketSpawnMob) {
            SPacketSpawnMob packet = (SPacketSpawnMob)event.getPacket();
            Entity entity = mc.field_71441_e.func_73045_a(packet.func_149024_d());
            if (mc.field_71439_g.field_71104_cf != null && entity instanceof EntityItem && mc.field_71439_g.field_71104_cf.func_70032_d((Entity)Objects.requireNonNull(entity)) < 3.0F && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFishingRod) {
               this.click();
               if (this.cast) {
                  this.click();
               }
            }
         }
      }

   }

   public void click() {
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
      mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
   }
}
