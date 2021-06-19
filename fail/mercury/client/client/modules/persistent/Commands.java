package fail.mercury.client.client.modules.persistent;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketChatMessage;

@ModuleManifest(
   label = "Commands",
   aliases = {"command", "cmd"},
   category = Category.MISC,
   description = "Commands for the client.",
   hidden = true,
   persistent = true
)
public class Commands extends Module {
   @Property("Prefix")
   public static String prefix = ".";

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketChatMessage) {
         CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
         if (packet.func_149439_c().startsWith(prefix + prefix) && !event.isCancelled()) {
            packet.field_149440_a = packet.func_149439_c().substring(1).toLowerCase();
         } else if (packet.func_149439_c().startsWith(prefix)) {
            event.setCancelled(true);
            Mercury.INSTANCE.getCommandManager().dispatch(packet.func_149439_c().substring(1));
         }
      }

   }
}
