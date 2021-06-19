package fail.mercury.client.client.modules.movement;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.EntityUtil;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.CollisionBoxEvent;
import fail.mercury.client.client.events.MotionEvent;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.ArrayList;
import java.util.List;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "Flight",
   aliases = {"Fly"},
   category = Category.MOVEMENT
)
public class Flight extends Module {
   @Property("Mode")
   @Mode({"Normal", "Packet", "SlowPacket", "DelayPacket", "Damage"})
   public static String mode = "Packet";
   @Property("Speed")
   @Clamp(
      maximum = "10"
   )
   private double speed = 8.0D;
   @Property("NoClip")
   private boolean noclip = false;
   @Property("AntiKick")
   private boolean antikick = false;
   private int teleportId;
   private List<CPacketPlayer> packets = new ArrayList();
   private double moveSpeed;
   private double lastDist;
   private int level;
   private TimerUtil delayTimer = new TimerUtil();

   public void onDisable() {
      EntityUtil.resetTimer();
      mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
      this.moveSpeed = MotionUtil.getBaseMoveSpeed();
      this.lastDist = 0.0D;
      if (this.noclip) {
         mc.field_71439_g.field_70145_X = false;
      }

   }

   public void onEnable() {
      this.level = 0;
      if (mode.equalsIgnoreCase("packet") && mc.field_71441_e != null) {
         this.teleportId = 0;
         this.packets.clear();
         CPacketPlayer bounds = new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E);
         this.packets.add(bounds);
         mc.field_71439_g.field_71174_a.func_147297_a(bounds);
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      double posY;
      if (mode.equalsIgnoreCase("damage")) {
         if (event.getTiming().equals(EventTiming.PRE)) {
            mc.field_71439_g.field_70181_x = 0.0D;
            posY = 0.41999998688697815D;
            if (mc.field_71439_g.field_70122_E) {
               if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                  posY += (double)((float)(mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
               }

               event.getLocation().setY(mc.field_71439_g.field_70181_x = posY);
               this.moveSpeed *= 2.149D;
            }
         }

         if (mc.field_71439_g.field_70173_aa % 2 == 0) {
            mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + MathUtil.getRandom(1.2354235325235235E-14D, 1.2354235325235233E-13D), mc.field_71439_g.field_70161_v);
         }

         EntityPlayerSP var10000;
         if (mc.field_71474_y.field_74314_A.func_151470_d()) {
            var10000 = mc.field_71439_g;
            var10000.field_70181_x += this.speed / 2.0D;
         }

         if (mc.field_71474_y.field_74311_E.func_151470_d()) {
            var10000 = mc.field_71439_g;
            var10000.field_70181_x -= this.speed / 2.0D;
         }
      }

      if (mode.equalsIgnoreCase("Normal")) {
         if (mc.field_71474_y.field_74314_A.func_151470_d()) {
            mc.field_71439_g.field_70181_x = this.speed;
         } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
            mc.field_71439_g.field_70181_x = -this.speed;
         } else {
            mc.field_71439_g.field_70181_x = 0.0D;
         }

         if (this.antikick && mc.field_71439_g.field_70173_aa % 5 == 0) {
            event.getLocation().setY(event.getLocation().getY() - 0.03125D);
            event.getLocation().setOnGround(true);
         }

         double[] dir = MotionUtil.forward(this.speed);
         mc.field_71439_g.field_70159_w = dir[0];
         mc.field_71439_g.field_70179_y = dir[1];
      }

      Position bounds;
      double posY;
      if (mode.equalsIgnoreCase("packet")) {
         if (this.teleportId <= 0) {
            bounds = new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E);
            this.packets.add(bounds);
            mc.field_71439_g.field_71174_a.func_147297_a(bounds);
            return;
         }

         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         posY = -1.0E-8D;
         if (!mc.field_71474_y.field_74314_A.func_151470_d() && !mc.field_71474_y.field_74311_E.func_151470_d()) {
            if (MotionUtil.isMoving(mc.field_71439_g)) {
               for(posY = 0.0625D; posY < this.speed; posY += 0.262D) {
                  double[] dir = MotionUtil.forward(posY);
                  mc.field_71439_g.func_70016_h(dir[0], posY, dir[1]);
                  this.move(dir[0], posY, dir[1]);
               }
            }
         } else {
            int i;
            if (mc.field_71474_y.field_74314_A.func_151470_d()) {
               for(i = 0; i <= 3; ++i) {
                  mc.field_71439_g.func_70016_h(0.0D, mc.field_71439_g.field_70173_aa % 20 == 0 ? -0.03999999910593033D : (double)(0.062F * (float)i), 0.0D);
                  this.move(0.0D, mc.field_71439_g.field_70173_aa % 20 == 0 ? -0.03999999910593033D : (double)(0.062F * (float)i), 0.0D);
               }
            } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
               for(i = 0; i <= 3; ++i) {
                  mc.field_71439_g.func_70016_h(0.0D, posY - 0.0625D * (double)i, 0.0D);
                  this.move(0.0D, posY - 0.0625D * (double)i, 0.0D);
               }
            }
         }
      }

      if (mode.equalsIgnoreCase("slowpacket")) {
         posY = mc.field_71439_g.field_70165_t;
         posY = mc.field_71439_g.field_70163_u;
         double posZ = mc.field_71439_g.field_70161_v;
         boolean ground = mc.field_71439_g.field_70122_E;
         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         if (!mc.field_71474_y.field_74314_A.func_151470_d() && !mc.field_71474_y.field_74311_E.func_151470_d()) {
            double[] dir = MotionUtil.forward(0.0625D);
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(posY + dir[0], posY, posZ + dir[1], ground));
            mc.field_71439_g.func_70634_a(posY + dir[0], posY, posZ + dir[1]);
         } else if (mc.field_71474_y.field_74314_A.func_151470_d()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(posY, posY + 0.0625D, posZ, ground));
            mc.field_71439_g.func_70634_a(posY, posY + 0.0625D, posZ);
         } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(posY, posY - 0.0625D, posZ, ground));
            mc.field_71439_g.func_70634_a(posY, posY - 0.0625D, posZ);
         }

         mc.field_71439_g.field_71174_a.func_147297_a(new Position(posY + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, posZ + mc.field_71439_g.field_70179_y, ground));
      }

      if (mode.equalsIgnoreCase("delaypacket")) {
         if (this.delayTimer.hasReached(1000L)) {
            this.delayTimer.reset();
         }

         if (this.delayTimer.hasReached(600L)) {
            mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
            return;
         }

         if (this.teleportId <= 0) {
            bounds = new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E);
            this.packets.add(bounds);
            mc.field_71439_g.field_71174_a.func_147297_a(bounds);
            return;
         }

         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         posY = -1.0E-8D;
         if (!mc.field_71474_y.field_74314_A.func_151470_d() && !mc.field_71474_y.field_74311_E.func_151470_d()) {
            if (MotionUtil.isMoving(mc.field_71439_g)) {
               double[] dir = MotionUtil.forward(0.2D);
               mc.field_71439_g.func_70016_h(dir[0], posY, dir[1]);
               this.move(dir[0], posY, dir[1]);
            }
         } else if (mc.field_71474_y.field_74314_A.func_151470_d()) {
            mc.field_71439_g.func_70016_h(0.0D, 0.06199999898672104D, 0.0D);
            this.move(0.0D, 0.06199999898672104D, 0.0D);
         } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
            mc.field_71439_g.func_70016_h(0.0D, 0.0625D, 0.0D);
            this.move(0.0D, 0.0625D, 0.0D);
         }
      }

      if (this.noclip) {
         mc.field_71439_g.field_70145_X = true;
      }

   }

   @EventHandler
   public void onMove(MotionEvent event) {
      if (mode.equalsIgnoreCase("damage")) {
         double forward = (double)mc.field_71439_g.field_71158_b.field_192832_b;
         double strafe = (double)mc.field_71439_g.field_71158_b.field_78902_a;
         float yaw = mc.field_71439_g.field_70177_z;
         if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0.0D);
            event.setZ(0.0D);
         }

         if (forward != 0.0D && strafe != 0.0D) {
            forward *= Math.sin(0.7853981633974483D);
            strafe *= Math.cos(0.7853981633974483D);
         }

         double difference;
         if (this.level != 1 || mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F) {
            if (this.level == 2) {
               ++this.level;
            } else if (this.level == 3) {
               ++this.level;
               difference = (mc.field_71439_g.field_70173_aa % 2 == 0 ? -0.05D : 0.1D) * (this.lastDist - MotionUtil.getBaseMoveSpeed());
               this.moveSpeed = this.lastDist - difference;
            } else {
               if (mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D)).size() > 0 || mc.field_71439_g.field_70124_G) {
                  this.level = 1;
               }

               this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
            }
         } else {
            this.level = 2;
            difference = mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) ? 1.86D : 2.05D;
            this.moveSpeed = difference * MotionUtil.getBaseMoveSpeed() - 0.01D;
         }

         this.moveSpeed = Math.max(this.moveSpeed, MotionUtil.getBaseMoveSpeed());
         difference = -Math.sin(Math.toRadians((double)yaw));
         double mz = Math.cos(Math.toRadians((double)yaw));
         event.setX(forward * this.moveSpeed * difference + strafe * this.moveSpeed * mz);
         event.setZ(forward * this.moveSpeed * mz - strafe * this.moveSpeed * difference);
      }

   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      switch(event.getType()) {
      case INCOMING:
         if ((mode.equalsIgnoreCase("packet") || mode.equalsIgnoreCase("delaypacket")) && event.getPacket() instanceof SPacketPlayerPosLook) {
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
         if (mode.equalsIgnoreCase("packet") || mode.equalsIgnoreCase("delaypacket")) {
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

   @EventHandler
   public void onCollision(CollisionBoxEvent event) {
      if (this.noclip && event.getEntity() == mc.field_71439_g) {
         event.setAABB(Block.field_185506_k);
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
