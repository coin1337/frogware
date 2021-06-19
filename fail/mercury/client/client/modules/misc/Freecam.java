package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.client.events.CollisionBoxEvent;
import fail.mercury.client.client.events.InsideBlockRenderEvent;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.PushEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.math.Vec3d;

@ModuleManifest(
   label = "FreeCam",
   fakelabel = "Free Cam",
   category = Category.MISC
)
public class Freecam extends Module {
   public static EntityOtherPlayerMP entity;
   private Entity riding;
   private Vec3d position;
   private float yaw;
   private float pitch;
   @Property("Speed")
   @Clamp(
      minimum = "0.1",
      maximum = "10"
   )
   public float speed = 2.0F;

   public void onEnable() {
      if (mc.field_71441_e != null) {
         if (mc.field_71439_g.func_184187_bx() != null) {
            this.riding = mc.field_71439_g.func_184187_bx();
            mc.field_71439_g.func_184210_p();
         }

         entity = new EntityOtherPlayerMP(mc.field_71441_e, mc.field_71449_j.func_148256_e());
         entity.func_82149_j(mc.field_71439_g);
         entity.field_70177_z = mc.field_71439_g.field_70177_z;
         entity.field_70759_as = mc.field_71439_g.field_70759_as;
         entity.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
         mc.field_71441_e.func_73027_a(1337, entity);
         this.position = mc.field_71439_g.func_174791_d();
         this.yaw = mc.field_71439_g.field_70177_z;
         this.pitch = mc.field_71439_g.field_70125_A;
      }

   }

   public void onDisable() {
      if (mc.field_71441_e != null) {
         if (this.riding != null) {
            mc.field_71439_g.func_184205_a(this.riding, true);
         }

         if (entity != null) {
            mc.field_71441_e.func_72900_e(entity);
         }

         if (this.position != null) {
            mc.field_71439_g.func_70107_b(this.position.field_72450_a, this.position.field_72448_b, this.position.field_72449_c);
         }

         mc.field_71439_g.field_70177_z = this.yaw;
         mc.field_71439_g.field_70125_A = this.pitch;
         mc.field_71439_g.field_70145_X = false;
         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         MotionUtil.setSpeed(mc.field_71439_g, 0.0D);
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
      mc.field_71439_g.field_70145_X = true;
      mc.field_71439_g.field_71155_g = 5000.0F;
      mc.field_71439_g.field_70747_aH = this.speed;
      if (MotionUtil.isMoving(mc.field_71439_g)) {
         MotionUtil.setSpeed(mc.field_71439_g, (double)this.speed);
      } else {
         MotionUtil.setSpeed(mc.field_71439_g, 0.0D);
      }

      EntityPlayerSP var10000;
      if (mc.field_71474_y.field_74314_A.func_151470_d()) {
         var10000 = mc.field_71439_g;
         var10000.field_70181_x += (double)this.speed;
      }

      if (mc.field_71474_y.field_74311_E.func_151470_d()) {
         var10000 = mc.field_71439_g;
         var10000.field_70181_x -= (double)this.speed;
      }

   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (mc.field_71441_e != null) {
         if (event.getType() == PacketEvent.Type.OUTGOING && mc.field_71441_e != null && event.getPacket() instanceof CPacketPlayer) {
            event.setCancelled(true);
         }

         if (event.getType() == PacketEvent.Type.INCOMING && event.getPacket() instanceof SPacketSetPassengers) {
            SPacketSetPassengers packet = (SPacketSetPassengers)event.getPacket();
            Entity riding = mc.field_71441_e.func_73045_a(packet.func_186972_b());
            if (riding != null && riding == this.riding) {
               this.riding = null;
            }
         }

      }
   }

   @EventHandler
   public void onPush(PushEvent event) {
      event.setCancelled(true);
   }

   @EventHandler
   public void onRender(InsideBlockRenderEvent event) {
      event.setCancelled(true);
   }

   @EventHandler
   public void onCollision(CollisionBoxEvent event) {
      if (event.getEntity() == mc.field_71439_g) {
         event.setAABB(Block.field_185506_k);
      }

   }
}
