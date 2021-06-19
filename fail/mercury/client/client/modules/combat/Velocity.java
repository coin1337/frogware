package fail.mercury.client.client.modules.combat;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

@ModuleManifest(
   label = "Velocity",
   aliases = {"antikb", "antivelocity", "kb"},
   category = Category.COMBAT
)
public class Velocity extends Module {
   @Property("Percent")
   @Clamp(
      maximum = "100"
   )
   public int percent = 0;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         if (event.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity packet = (SPacketEntityVelocity)event.getPacket();
            if (mc.field_71441_e.func_73045_a(packet.func_149412_c()) == mc.field_71439_g) {
               if (this.percent > 0) {
                  packet.field_149415_b = (int)((double)packet.field_149415_b * ((double)this.percent / 100.0D));
                  packet.field_149416_c = (int)((double)packet.field_149416_c * ((double)this.percent / 100.0D));
                  packet.field_149414_d = (int)((double)packet.field_149414_d * ((double)this.percent / 100.0D));
               } else {
                  event.setCancelled(true);
               }
            }
         }

         if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion s27PacketExplosion;
            SPacketExplosion packet2 = s27PacketExplosion = (SPacketExplosion)event.getPacket();
            if (this.percent > 0) {
               s27PacketExplosion.field_149152_f = (float)((double)s27PacketExplosion.field_149152_f * ((double)this.percent / 100.0D));
               packet2.field_149153_g = (float)((double)packet2.field_149153_g * ((double)this.percent / 100.0D));
               packet2.field_149159_h = (float)((double)packet2.field_149159_h * ((double)this.percent / 100.0D));
            } else {
               event.setCancelled(true);
            }
         }

      }
   }
}
