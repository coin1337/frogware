package fail.mercury.client.client.modules.player;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import fail.mercury.client.client.modules.movement.Flight;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;

@ModuleManifest(
   label = "AntiHunger",
   aliases = {"NoHunger"},
   fakelabel = "Anti Hunger",
   category = Category.PLAYER
)
public class AntiHunger extends Module {
   @Property("CancelSprint")
   public boolean sprint = false;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && this.sprint && event.getPacket() instanceof CPacketEntityAction) {
         CPacketEntityAction packet = (CPacketEntityAction)event.getPacket();
         if (packet.func_180764_b() == Action.START_SPRINTING || packet.func_180764_b() == Action.STOP_SPRINTING) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (!Mercury.INSTANCE.getModuleManager().find(Flight.class).isEnabled() || !Flight.mode.equalsIgnoreCase("packet") && !Flight.mode.equalsIgnoreCase("packet2")) {
         event.getLocation().setOnGround(false);
      }
   }
}
