package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.client.events.PacketEvent;
import java.util.ArrayList;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "KeepBreak",
   fakelabel = "Keep Break",
   aliases = {"NoAbortBreaking"},
   category = Category.MISC
)
public class KeepBreak extends Module {
   private final ArrayList<BlockPos> blocksAborted = new ArrayList();

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketPlayerDigging) {
         ChatUtil.print("f");
         if (((CPacketPlayerDigging)event.getPacket()).func_180762_c().equals(Action.ABORT_DESTROY_BLOCK)) {
            this.blocksAborted.add(((CPacketPlayerDigging)event.getPacket()).func_179715_a());
            event.setCancelled(true);
         }

         if (((CPacketPlayerDigging)event.getPacket()).func_180762_c().equals(Action.START_DESTROY_BLOCK) && this.blocksAborted.contains(((CPacketPlayerDigging)event.getPacket()).func_179715_a())) {
            event.setCancelled(true);
         }
      }

   }
}
