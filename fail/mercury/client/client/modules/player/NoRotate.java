package fail.mercury.client.client.modules.player;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import java.util.Objects;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@ModuleManifest(
   label = "NoRotate",
   aliases = {"NoForceRotate"},
   fakelabel = "No Rotate",
   category = Category.PLAYER
)
public class NoRotate extends Module {
   @EventHandler
   public void onPacket(PacketEvent event) {
      if (!Objects.isNull(mc.field_71439_g) && !Objects.isNull(mc.field_71441_e)) {
         if (event.getType().equals(PacketEvent.Type.INCOMING) && event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook poslook = (SPacketPlayerPosLook)event.getPacket();
            if (mc.field_71439_g.field_70177_z != -180.0F && mc.field_71439_g.field_70125_A != 0.0F) {
               poslook.field_148936_d = mc.field_71439_g.field_70177_z;
               poslook.field_148937_e = mc.field_71439_g.field_70125_A;
            }
         }

      }
   }
}
