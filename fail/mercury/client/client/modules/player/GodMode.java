package fail.mercury.client.client.modules.player;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumHand;

@ModuleManifest(
   label = "GodMode",
   category = Category.PLAYER,
   fakelabel = "God Mode",
   aliases = {"God"}
)
public class GodMode extends Module {
   private Entity riding;
   @Property("Mode")
   @Mode({"Portal", "Entity"})
   public String mode = "Portal";

   public void onEnable() {
      if (this.mode.equalsIgnoreCase("entity") && mc.field_71439_g.func_184187_bx() != null) {
         this.riding = mc.field_71439_g.func_184187_bx();
         mc.field_71439_g.func_184210_p();
         mc.field_71441_e.func_72900_e(this.riding);
         mc.field_71439_g.func_70107_b((double)mc.field_71439_g.func_180425_c().func_177958_n(), (double)(mc.field_71439_g.func_180425_c().func_177956_o() - 1), (double)mc.field_71439_g.func_180425_c().func_177952_p());
      }

   }

   public void onDisable() {
      if (this.mode.equalsIgnoreCase("entity") && this.riding != null) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(this.riding, EnumHand.MAIN_HAND));
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.mode.equalsIgnoreCase("entity") && this.riding != null) {
         this.riding.field_70165_t = mc.field_71439_g.field_70165_t;
         this.riding.field_70163_u = mc.field_71439_g.field_70163_u;
         this.riding.field_70161_v = mc.field_71439_g.field_70161_v;
         this.riding.field_70177_z = mc.field_71439_g.field_70177_z;
         mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, true));
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketInput(mc.field_71439_g.field_71158_b.field_192832_b, mc.field_71439_g.field_71158_b.field_78902_a, false, false));
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketVehicleMove(this.riding));
      }

   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING)) {
         if (this.mode.equalsIgnoreCase("portal") && event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCancelled(true);
         }

         if (this.mode.equalsIgnoreCase("entity")) {
            if (event.getPacket() instanceof CPacketUseEntity) {
               CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
               if (this.riding != null) {
                  Entity entity = packet.func_149564_a(mc.field_71441_e);
                  if (entity != null) {
                     this.riding.field_70165_t = entity.field_70165_t;
                     this.riding.field_70163_u = entity.field_70163_u;
                     this.riding.field_70161_v = entity.field_70161_v;
                     this.riding.field_70177_z = mc.field_71439_g.field_70177_z;
                     mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, true));
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketInput(mc.field_71439_g.field_71158_b.field_192832_b, mc.field_71439_g.field_71158_b.field_78902_a, false, false));
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketVehicleMove(this.riding));
                  }
               }
            }

            if (event.getPacket() instanceof Position || event.getPacket() instanceof PositionRotation) {
               event.setCancelled(true);
            }
         }
      }

   }
}
