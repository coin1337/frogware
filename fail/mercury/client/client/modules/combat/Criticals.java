package fail.mercury.client.client.modules.combat;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.JumpEvent;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.modules.movement.Flight;
import fail.mercury.client.client.modules.movement.Speed;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketUseEntity.Action;

@ModuleManifest(
   label = "Criticals",
   aliases = {"crits"},
   category = Category.COMBAT
)
public class Criticals extends Module {
   @Property("Mode")
   @Mode({"Packet", "Edit"})
   public static String mode = "Packet";
   public static int waitDelay;
   public static int groundTicks;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketUseEntity) {
         CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
         if (packet.func_149565_c() == Action.ATTACK && canCrit(packet.func_149564_a(mc.field_71441_e)) && mode.equalsIgnoreCase("Packet")) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0625101D, mc.field_71439_g.field_70161_v, false));
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
         }
      }

   }

   @EventHandler
   public void onJump(JumpEvent event) {
      if (KillAura.target != null && mode.equalsIgnoreCase("edit") && groundTicks != 0) {
         event.setCancelled(true);
         mc.field_71439_g.field_71174_a.func_147297_a(new PositionRotation(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, KillAura.yaw, KillAura.pitch, true));
         mc.field_71439_g.field_70181_x = 0.41999998688697815D;
         groundTicks = 0;
      } else {
         event.setCancelled(false);
      }

   }

   public static boolean canCrit(Entity entity) {
      return !Mercury.INSTANCE.getModuleManager().find(Speed.class).isEnabled() && !Mercury.INSTANCE.getModuleManager().find(Flight.class).isEnabled() && mc.field_71439_g.field_70122_E && !mc.field_71474_y.field_74314_A.func_151470_d() && !(entity instanceof EntityEnderCrystal);
   }
}
