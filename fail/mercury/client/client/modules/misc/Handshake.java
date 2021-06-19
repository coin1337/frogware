package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import io.netty.buffer.Unpooled;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

@ModuleManifest(
   label = "Handshake",
   aliases = {"CustomHandshake", "NoHandshake"},
   category = Category.MISC,
   hidden = true
)
public class Handshake extends Module {
   @Property("Brand")
   public String brand = "vanilla";

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING)) {
         if (event.getPacket() instanceof FMLProxyPacket && !mc.func_71356_B()) {
            event.setCancelled(true);
         }

         if (event.getPacket() instanceof CPacketCustomPayload) {
            CPacketCustomPayload packet = (CPacketCustomPayload)event.getPacket();
            if (packet.func_149559_c().equals("MC|Brand")) {
               packet.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a(this.brand);
            }
         }
      }

   }
}
