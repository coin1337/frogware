package fail.mercury.client.client.modules.movement;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.EntityUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.MotionEvent;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "Speed",
   category = Category.MOVEMENT
)
public class Speed extends Module {
   @Property("Mode")
   @Mode({"BHop", "Ground", "YPort", "Packet", "Packet2"})
   public static String mode = "BHop";
   @Property("Speed")
   @Clamp(
      maximum = "10"
   )
   private double speed = 8.0D;
   @Property("Ice")
   public boolean ice = false;
   private double moveSpeed;
   public static boolean doSlow;
   public TimerUtil waitTimer = new TimerUtil();

   public void onDisable() {
      if (!mode.equalsIgnoreCase("bhop")) {
         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
      }

      EntityUtil.resetTimer();
   }

   public void onEnable() {
      this.moveSpeed = MotionUtil.getBaseMoveSpeed();
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      boolean icee = this.ice && (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockIce || mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockPackedIce);
      if (icee) {
         MotionUtil.setSpeed(mc.field_71439_g, MotionUtil.getBaseMoveSpeed() + (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) ? (mc.field_71439_g.field_70173_aa % 2 == 0 ? 0.7D : 0.1D) : 0.4D));
      }

      if (!icee) {
         boolean notUnder;
         if ((mode.equalsIgnoreCase("packet") || mode.equalsIgnoreCase("packet2")) && MotionUtil.isMoving(mc.field_71439_g) && mc.field_71439_g.field_70122_E) {
            notUnder = Mercury.INSTANCE.getModuleManager().find(Step.class).isEnabled();
            double posX = mc.field_71439_g.field_70165_t;
            double posY = mc.field_71439_g.field_70163_u;
            double posZ = mc.field_71439_g.field_70161_v;
            boolean ground = mc.field_71439_g.field_70122_E;
            double[] dir1 = MotionUtil.forward(0.5D);
            BlockPos pos = new BlockPos(posX + dir1[0], posY, posZ + dir1[1]);
            Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (notUnder && !(block instanceof BlockAir)) {
               MotionUtil.setSpeed(mc.field_71439_g, 0.0D);
               return;
            }

            if (mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o() - 1, pos.func_177952_p())).func_177230_c() instanceof BlockAir) {
               return;
            }

            for(double x = 0.0625D; x < this.speed; x += 0.262D) {
               double[] dir = MotionUtil.forward(x);
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(posX + dir[0], posY, posZ + dir[1], ground));
            }

            if (mode.equalsIgnoreCase("packet2")) {
               MotionUtil.setSpeed(mc.field_71439_g, 2.0D);
            }

            mc.field_71439_g.field_71174_a.func_147297_a(new Position(posX + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u <= 10.0D ? 255.0D : 1.0D, posZ + mc.field_71439_g.field_70179_y, ground));
         }

         if (mode.equalsIgnoreCase("yport")) {
            if (!MotionUtil.isMoving(mc.field_71439_g) || mc.field_71439_g.func_70090_H() && mc.field_71439_g.func_180799_ab() || mc.field_71439_g.field_70123_F) {
               return;
            }

            if (mc.field_71439_g.field_70122_E) {
               EntityUtil.setTimer(1.15F);
               mc.field_71439_g.func_70664_aZ();
               notUnder = mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockIce || mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockPackedIce;
               MotionUtil.setSpeed(mc.field_71439_g, MotionUtil.getBaseMoveSpeed() + (notUnder ? 0.3D : 0.06D));
            } else {
               mc.field_71439_g.field_70181_x = -1.0D;
               EntityUtil.resetTimer();
            }
         }

         if (mode.equalsIgnoreCase("ground") && event.getTiming().equals(EventTiming.PRE)) {
            if (!MotionUtil.isMoving(mc.field_71439_g) || mc.field_71439_g.func_70090_H() && mc.field_71439_g.func_180799_ab() || mc.field_71439_g.field_70123_F) {
               return;
            }

            if (mc.field_71439_g.field_70122_E) {
               if (mc.field_71439_g.field_70173_aa % 2 == 0) {
                  notUnder = mc.field_71441_e.func_180495_p(event.getLocation().toBlockPos().func_177982_a(0, 2, 0)).func_177230_c() instanceof BlockAir;
                  event.getLocation().setY(mc.field_71439_g.field_70163_u + (notUnder ? 0.4D : 0.2D));
                  MotionUtil.setSpeed(mc.field_71439_g, MotionUtil.getBaseMoveSpeed() - 0.15D);
               } else {
                  MotionUtil.setSpeed(mc.field_71439_g, MotionUtil.getBaseMoveSpeed() + 0.065D);
               }
            } else {
               mc.field_71439_g.field_70181_x = -1.0D;
            }
         }
      }

   }

   @EventHandler
   public void onMotion(MotionEvent event) {
      boolean icee = this.ice && (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockIce || mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockPackedIce);
      if (!icee) {
         if (mode.equalsIgnoreCase("bhop")) {
            boolean jesus = Mercury.INSTANCE.getModuleManager().find(Jesus.class).isEnabled();
            double motionY = 0.41999998688697815D;
            if (mc.field_71439_g.field_70122_E && MotionUtil.isMoving(mc.field_71439_g) && this.waitTimer.hasReached(300L)) {
               if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                  motionY += (double)((float)(mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
               }

               event.setY(mc.field_71439_g.field_70181_x = motionY);
               this.moveSpeed = MotionUtil.getBaseMoveSpeed() * (jesus && EntityUtil.isColliding(0.0D, -0.5D, 0.0D) instanceof BlockLiquid && !EntityUtil.isInLiquid() ? 0.9D : 1.901D);
               doSlow = true;
               this.waitTimer.reset();
            } else if (!doSlow && !mc.field_71439_g.field_70123_F) {
               this.moveSpeed -= this.moveSpeed / 159.0D;
            } else {
               this.moveSpeed -= jesus && EntityUtil.isColliding(0.0D, -0.8D, 0.0D) instanceof BlockLiquid && !EntityUtil.isInLiquid() ? 0.4D : 0.7D * (this.moveSpeed = MotionUtil.getBaseMoveSpeed());
               doSlow = false;
            }

            this.moveSpeed = Math.max(this.moveSpeed, MotionUtil.getBaseMoveSpeed());
            double[] dir = MotionUtil.forward(this.moveSpeed);
            event.setX(dir[0]);
            event.setZ(dir[1]);
         }

      }
   }
}
