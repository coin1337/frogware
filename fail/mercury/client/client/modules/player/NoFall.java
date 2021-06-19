package fail.mercury.client.client.modules.player;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.ArrayList;
import java.util.List;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "NoFall",
   category = Category.PLAYER,
   fakelabel = "No Fall"
)
public class NoFall extends Module {
   private int teleportId;
   private List<CPacketPlayer> packets = new ArrayList();

   public void onEnable() {
      super.onEnable();
      if (mc.field_71441_e != null) {
         this.teleportId = 0;
         this.packets.clear();
         CPacketPlayer bounds = new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E);
         this.packets.add(bounds);
         mc.field_71439_g.field_71174_a.func_147297_a(bounds);
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.teleportId <= 0) {
         CPacketPlayer bounds = new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E);
         this.packets.add(bounds);
         mc.field_71439_g.field_71174_a.func_147297_a(bounds);
      } else {
         double posY = -1.0E-8D;
         if ((double)mc.field_71439_g.field_70143_R > 1.5D) {
            mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);

            for(int i = 0; i <= 3; ++i) {
               mc.field_71439_g.func_70016_h(0.0D, posY - 0.0625D * (double)i, 0.0D);
               this.move(0.0D, posY - 0.0625D * (double)i, 0.0D);
            }
         }

      }
   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (mc.field_71439_g != null && (double)mc.field_71439_g.field_70143_R > 1.5D) {
         switch(event.getType()) {
         case INCOMING:
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
               SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
               if (Minecraft.func_71410_x().field_71439_g.func_70089_S() && Minecraft.func_71410_x().field_71441_e.func_175667_e(new BlockPos(Minecraft.func_71410_x().field_71439_g.field_70165_t, Minecraft.func_71410_x().field_71439_g.field_70163_u, Minecraft.func_71410_x().field_71439_g.field_70161_v)) && !(Minecraft.func_71410_x().field_71462_r instanceof GuiDownloadTerrain)) {
                  if (this.teleportId <= 0) {
                     this.teleportId = packet.func_186965_f();
                  } else {
                     event.setCancelled(true);
                  }
               }
            }
            break;
         case OUTGOING:
            if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof Position)) {
               event.setCancelled(true);
            }

            if (event.getPacket() instanceof CPacketPlayer) {
               CPacketPlayer packet = (CPacketPlayer)event.getPacket();
               if (this.packets.contains(packet)) {
                  this.packets.remove(packet);
                  return;
               }

               event.setCancelled(true);
            }
         }
      }

   }

   private void move(double x, double y, double z) {
      Minecraft mc = Minecraft.func_71410_x();
      CPacketPlayer pos = new Position(mc.field_71439_g.field_70165_t + x, mc.field_71439_g.field_70163_u + y, mc.field_71439_g.field_70161_v + z, mc.field_71439_g.field_70122_E);
      this.packets.add(pos);
      mc.field_71439_g.field_71174_a.func_147297_a(pos);
      CPacketPlayer bounds = new Position(mc.field_71439_g.field_70165_t + x, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v + z, mc.field_71439_g.field_70122_E);
      this.packets.add(bounds);
      mc.field_71439_g.field_71174_a.func_147297_a(bounds);
      ++this.teleportId;
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketConfirmTeleport(this.teleportId - 1));
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketConfirmTeleport(this.teleportId));
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketConfirmTeleport(this.teleportId + 1));
   }
}
