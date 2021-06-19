package fail.mercury.client.client.modules.misc;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.modules.persistent.Commands;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketChatMessage;

@ModuleManifest(
   label = "FurryChat",
   fakelabel = "Furry Chat",
   aliases = {"FChat", "OwOify", "WeebTalk"},
   category = Category.MISC
)
public class FurryChat extends Module {
   @Property("Suffix")
   public boolean suffix = true;
   @Property("Prefix")
   public boolean prefix = false;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketChatMessage) {
         CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
         if (packet.func_149439_c().startsWith("/") || packet.func_149439_c().startsWith(Commands.prefix)) {
            return;
         }

         if (Mercury.INSTANCE.getModuleManager().find(Translator.class).isEnabled()) {
            return;
         }

         if (this.prefix && !packet.func_149439_c().startsWith("owo:")) {
            return;
         }

         String[] cancer = new String[]{"owo", "OwO", "uwu", "UwU", ">w<", "^w^", "7w7", "^o^", ":3", "@w@"};
         packet.field_149440_a = this.yep(this.prefix && packet.func_149439_c().startsWith("owo:") ? packet.func_149439_c().replace("owo:", "") : packet.func_149439_c()) + " " + (this.suffix ? cancer[MathUtil.getRandom(0, cancer.length - 1)] : "");
      }

   }

   public String yep(String chat) {
      return chat.replace("r", "w").replace("R", "W").replace("l", "w").replace("L", "W").replace(" n", " ny").replace(" N", " Ny").replace("ove", "uv").replace("OVE", "UV").replace("this", "dis");
   }
}
