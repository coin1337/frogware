package fail.mercury.client.client.modules.movement;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.InventoryUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.Render3DEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@ModuleManifest(
   label = "Scaffold",
   category = Category.MOVEMENT,
   aliases = {"BlockFly"},
   description = "Automatically places blocks under your feet."
)
public class Scaffold extends Module {
   private List<Block> invalid;
   private TimerUtil timerMotion;
   private TimerUtil itemTimer;
   private Scaffold.BlockData blockData;
   @Property("Expand")
   @Clamp(
      maximum = "6"
   )
   private double expand;
   @Property("ESPMode")
   @Mode({"Block", "Face"})
   private String espMode;
   @Property("Switch")
   private boolean Switch;
   @Property("Tower")
   private boolean tower;
   @Property("Center")
   public static boolean center = true;
   @Property("KeepY")
   private boolean keepY;
   @Property("Sprint")
   public static boolean sprint = true;
   @Property("ESP")
   public static boolean esp = true;
   @Property("KeepRotations")
   private boolean keepRots;
   @Property("Replenish")
   private boolean replenishBlocks;
   @Property("Down")
   public static boolean down = true;
   @Property("Swing")
   public boolean swing;
   private int lastY;
   private float lastYaw;
   private float lastPitch;
   private BlockPos pos;
   private boolean teleported;

   public Scaffold() {
      this.invalid = Arrays.asList(Blocks.field_150467_bQ, Blocks.field_150350_a, Blocks.field_150355_j, Blocks.field_150480_ab, Blocks.field_150358_i, Blocks.field_150353_l, Blocks.field_150358_i, Blocks.field_150486_ae, Blocks.field_150381_bn, Blocks.field_150447_bR, Blocks.field_150477_bB, Blocks.field_150351_n, Blocks.field_150468_ap, Blocks.field_150395_bd, Blocks.field_150461_bJ, Blocks.field_150421_aI, Blocks.field_180410_as, Blocks.field_180412_aq, Blocks.field_180409_at, Blocks.field_150454_av, Blocks.field_180411_ar, Blocks.field_180413_ao, Blocks.field_180414_ap, Blocks.field_180400_cw, Blocks.field_150415_aT, Blocks.field_190975_dA, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190986_du, Blocks.field_190984_ds, Blocks.field_190990_dy, Blocks.field_190980_do, Blocks.field_190982_dq, Blocks.field_190979_dn, Blocks.field_190978_dm, Blocks.field_190983_dr, Blocks.field_190987_dv, Blocks.field_190991_dz, Blocks.field_190985_dt, Blocks.field_190977_dl, Blocks.field_190981_dp);
      this.timerMotion = new TimerUtil();
      this.itemTimer = new TimerUtil();
      this.expand = 1.0D;
      this.espMode = "Block";
      this.Switch = true;
      this.tower = true;
      this.keepY = true;
      this.keepRots = true;
      this.replenishBlocks = true;
      this.swing = false;
   }

   public void onEnable() {
      if (mc.field_71441_e != null) {
         this.timerMotion.reset();
         this.lastY = MathHelper.func_76128_c(mc.field_71439_g.field_70163_u);
      }

   }

   @EventHandler
   public void onRender3D(Render3DEvent event) {
      if (esp && this.blockData != null && this.blockData.position != null) {
         BlockPos place = this.blockData.position;
         double x1 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177958_n() : place.func_177958_n()) - mc.func_175598_ae().field_78725_b;
         double x2 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177958_n() : place.func_177958_n()) - mc.func_175598_ae().field_78725_b + 1.0D;
         double y1 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177956_o() : place.func_177956_o()) - mc.func_175598_ae().field_78726_c;
         double y2 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177956_o() : place.func_177956_o()) - mc.func_175598_ae().field_78726_c + 1.0D;
         double z1 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177952_p() : place.func_177952_p()) - mc.func_175598_ae().field_78723_d;
         double z2 = (double)(this.espMode.equalsIgnoreCase("block") ? this.pos.func_177952_p() : place.func_177952_p()) - mc.func_175598_ae().field_78723_d + 1.0D;
         if (this.espMode.equalsIgnoreCase("face")) {
            EnumFacing face = this.blockData.face;
            y1 += (double)face.func_96559_d();
            if (face.func_82601_c() < 0) {
               x2 += (double)face.func_82601_c();
            } else {
               x1 += (double)face.func_82601_c();
            }

            if (face.func_82599_e() < 0) {
               z2 += (double)face.func_82599_e();
            } else {
               z1 += (double)face.func_82599_e();
            }
         }

         Color clr = new Color(117, 255, 253, 29);
         RenderUtil.drawESP(new AxisAlignedBB(x1, y1, z1, x2, y2, z2), (float)clr.getRed(), (float)clr.getGreen(), (float)clr.getBlue(), (float)clr.getAlpha());
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (!Mercury.INSTANCE.getModuleManager().find(Sprint.class).isEnabled() && (down && mc.field_71474_y.field_74311_E.func_151470_d() || !sprint)) {
         mc.field_71439_g.func_70031_b(false);
      }

      int heldItem;
      if (this.replenishBlocks && !(mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() instanceof ItemBlock) && this.getBlockCountHotbar() <= 0 && this.itemTimer.hasReached(100L)) {
         for(heldItem = 9; heldItem < 45; ++heldItem) {
            if (mc.field_71439_g.field_71069_bz.func_75139_a(heldItem).func_75216_d()) {
               ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(heldItem).func_75211_c();
               if (is.func_77973_b() instanceof ItemBlock && !this.invalid.contains(Block.func_149634_a(is.func_77973_b())) && heldItem < 36) {
                  InventoryUtil.swap(InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, is.func_77973_b()), 44);
               }
            }
         }
      }

      if (this.keepY) {
         if (!MotionUtil.isMoving(mc.field_71439_g) && mc.field_71474_y.field_74314_A.func_151470_d() || mc.field_71439_g.field_70124_G || mc.field_71439_g.field_70122_E) {
            this.lastY = MathHelper.func_76128_c(mc.field_71439_g.field_70163_u);
         }
      } else {
         this.lastY = MathHelper.func_76128_c(mc.field_71439_g.field_70163_u);
      }

      if (event.getTiming().equals(EventTiming.PRE)) {
         this.blockData = null;
         double x = mc.field_71439_g.field_70165_t;
         double z = mc.field_71439_g.field_70161_v;
         double y = this.keepY ? (double)this.lastY : mc.field_71439_g.field_70163_u;
         double forward = (double)mc.field_71439_g.field_71158_b.field_192832_b;
         double strafe = (double)mc.field_71439_g.field_71158_b.field_78902_a;
         float yaw = mc.field_71439_g.field_70177_z;
         if (!mc.field_71439_g.field_70123_F) {
            double[] coords = this.getExpandCoords(x, z, forward, strafe, yaw);
            x = coords[0];
            z = coords[1];
         }

         if (this.canPlace(mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - (double)(mc.field_71474_y.field_74311_E.func_151470_d() && down ? 2 : 1), mc.field_71439_g.field_70161_v)).func_177230_c())) {
            x = mc.field_71439_g.field_70165_t;
            z = mc.field_71439_g.field_70161_v;
         }

         BlockPos blockBelow = new BlockPos(x, y - 1.0D, z);
         if (mc.field_71474_y.field_74311_E.func_151470_d() && down) {
            blockBelow = new BlockPos(x, y - 2.0D, z);
         }

         this.pos = blockBelow;
         if (mc.field_71441_e.func_180495_p(blockBelow).func_177230_c() == Blocks.field_150350_a) {
            this.blockData = this.getBlockData2(blockBelow);
            if (this.blockData != null) {
               float yaw1 = this.aimAtLocation((double)this.blockData.position.func_177958_n(), (double)this.blockData.position.func_177956_o(), (double)this.blockData.position.func_177952_p(), this.blockData.face)[0];
               float pitch = this.aimAtLocation((double)this.blockData.position.func_177958_n(), (double)this.blockData.position.func_177956_o(), (double)this.blockData.position.func_177952_p(), this.blockData.face)[1];
               if (this.keepRots) {
                  this.lastYaw = yaw1;
                  this.lastPitch = pitch;
               } else {
                  event.getRotation().setYaw(yaw1);
                  event.getRotation().setPitch(pitch);
               }
            }
         }

         if (this.keepRots) {
            event.getRotation().setYaw(this.lastYaw);
            event.getRotation().setPitch(this.lastPitch);
         }
      } else if (this.blockData != null) {
         if (this.getBlockCountHotbar() <= 0 || !this.Switch && mc.field_71439_g.func_184614_ca().func_77973_b() != null && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock)) {
            return;
         }

         heldItem = mc.field_71439_g.field_71071_by.field_70461_c;
         if (this.Switch) {
            for(int j = 0; j < 9; ++j) {
               if (mc.field_71439_g.field_71071_by.func_70301_a(j) != null && mc.field_71439_g.field_71071_by.func_70301_a(j).func_190916_E() != 0 && mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() instanceof ItemBlock && !this.invalid.contains(((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b()).func_179223_d())) {
                  mc.field_71439_g.field_71071_by.field_70461_c = j;
                  break;
               }
            }
         }

         if (this.tower) {
            if (mc.field_71474_y.field_74314_A.func_151470_d() && mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F && this.tower && !mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
               if (!this.teleported && center) {
                  this.teleported = true;
                  BlockPos pos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
                  mc.field_71439_g.func_70107_b((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5D);
               }

               if (center && !this.teleported) {
                  return;
               }

               mc.field_71439_g.field_70181_x = 0.41999998688697815D;
               mc.field_71439_g.field_70179_y = 0.0D;
               mc.field_71439_g.field_70159_w = 0.0D;
               if (this.timerMotion.sleep(1500L)) {
                  mc.field_71439_g.field_70181_x = -0.28D;
               }
            } else {
               this.timerMotion.reset();
               if (this.teleported && center) {
                  this.teleported = false;
               }
            }
         }

         if (mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, this.blockData.position, this.blockData.face, new Vec3d((double)this.blockData.position.func_177958_n() + Math.random(), (double)this.blockData.position.func_177956_o() + Math.random(), (double)this.blockData.position.func_177952_p() + Math.random()), EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
            if (this.swing) {
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            } else {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
            }
         }

         mc.field_71439_g.field_71071_by.field_70461_c = heldItem;
      }

   }

   private int getBlockCount() {
      int blockCount = 0;

      for(int i = 0; i < 45; ++i) {
         if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
            ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            Item item = is.func_77973_b();
            if (is.func_77973_b() instanceof ItemBlock && !this.invalid.contains(((ItemBlock)item).func_179223_d())) {
               blockCount += is.func_190916_E();
            }
         }
      }

      return blockCount;
   }

   public double[] getExpandCoords(double x, double z, double forward, double strafe, float YAW) {
      BlockPos underPos = new BlockPos(x, mc.field_71439_g.field_70163_u - (double)(mc.field_71474_y.field_74311_E.func_151470_d() && down ? 2 : 1), z);
      Block underBlock = mc.field_71441_e.func_180495_p(underPos).func_177230_c();
      double xCalc = -999.0D;
      double zCalc = -999.0D;
      double dist = 0.0D;

      for(double expandDist = this.expand * 2.0D; !this.canPlace(underBlock); underBlock = mc.field_71441_e.func_180495_p(underPos).func_177230_c()) {
         ++dist;
         if (dist > expandDist) {
            dist = expandDist;
         }

         xCalc = x + (forward * 0.45D * Math.cos(Math.toRadians((double)(YAW + 90.0F))) + strafe * 0.45D * Math.sin(Math.toRadians((double)(YAW + 90.0F)))) * dist;
         zCalc = z + (forward * 0.45D * Math.sin(Math.toRadians((double)(YAW + 90.0F))) - strafe * 0.45D * Math.cos(Math.toRadians((double)(YAW + 90.0F)))) * dist;
         if (dist == expandDist) {
            break;
         }

         underPos = new BlockPos(xCalc, mc.field_71439_g.field_70163_u - (double)(mc.field_71474_y.field_74311_E.func_151470_d() && down ? 2 : 1), zCalc);
      }

      return new double[]{xCalc, zCalc};
   }

   public boolean canPlace(Block block) {
      return (block instanceof BlockAir || block instanceof BlockLiquid) && mc.field_71441_e != null && mc.field_71439_g != null && this.pos != null && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(this.pos)).isEmpty();
   }

   private int getBlockCountHotbar() {
      int blockCount = 0;

      for(int i = 36; i < 45; ++i) {
         if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
            ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            Item item = is.func_77973_b();
            if (is.func_77973_b() instanceof ItemBlock && !this.invalid.contains(((ItemBlock)item).func_179223_d())) {
               blockCount += is.func_190916_E();
            }
         }
      }

      return blockCount;
   }

   private Scaffold.BlockData getBlockData2(BlockPos pos) {
      if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(0, -1, 0), EnumFacing.UP);
      } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(-1, 0, 0), EnumFacing.EAST);
      } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(1, 0, 0), EnumFacing.WEST);
      } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(0, 0, 1), EnumFacing.NORTH);
      } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
      } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c())) {
         return new Scaffold.BlockData(pos.func_177982_a(0, 1, 0), EnumFacing.DOWN);
      } else {
         BlockPos pos2 = pos.func_177982_a(-1, 0, 0);
         if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, -1, 0)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(0, -1, 0), EnumFacing.UP);
         } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 1, 0)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(0, 1, 0), EnumFacing.DOWN);
         } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(-1, 0, 0)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(-1, 0, 0), EnumFacing.EAST);
         } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(1, 0, 0)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(1, 0, 0), EnumFacing.WEST);
         } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 0, 1)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(0, 0, 1), EnumFacing.NORTH);
         } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 0, -1)).func_177230_c())) {
            return new Scaffold.BlockData(pos2.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
         } else {
            BlockPos pos3 = pos.func_177982_a(1, 0, 0);
            if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, -1, 0)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(0, -1, 0), EnumFacing.UP);
            } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 1, 0)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(0, 1, 0), EnumFacing.DOWN);
            } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(-1, 0, 0)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(-1, 0, 0), EnumFacing.EAST);
            } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(1, 0, 0)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(1, 0, 0), EnumFacing.WEST);
            } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 0, 1)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(0, 0, 1), EnumFacing.NORTH);
            } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 0, -1)).func_177230_c())) {
               return new Scaffold.BlockData(pos3.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
            } else {
               BlockPos pos4 = pos.func_177982_a(0, 0, 1);
               if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, -1, 0)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(0, -1, 0), EnumFacing.UP);
               } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 1, 0)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(0, 1, 0), EnumFacing.DOWN);
               } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(-1, 0, 0)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(-1, 0, 0), EnumFacing.EAST);
               } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(1, 0, 0)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(1, 0, 0), EnumFacing.WEST);
               } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 0, 1)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(0, 0, 1), EnumFacing.NORTH);
               } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 0, -1)).func_177230_c())) {
                  return new Scaffold.BlockData(pos4.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
               } else {
                  BlockPos pos5 = pos.func_177982_a(0, 0, -1);
                  if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, -1, 0)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(0, -1, 0), EnumFacing.UP);
                  } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 1, 0)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                  } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(-1, 0, 0)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                  } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(1, 0, 0)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(1, 0, 0), EnumFacing.WEST);
                  } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 0, 1)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                  } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 0, -1)).func_177230_c())) {
                     return new Scaffold.BlockData(pos5.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                  } else {
                     BlockPos pos6 = pos.func_177982_a(-2, 0, 0);
                     if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, -1, 0)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(0, -1, 0), EnumFacing.UP);
                     } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 1, 0)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                     } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(-1, 0, 0)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                     } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(1, 0, 0)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(1, 0, 0), EnumFacing.WEST);
                     } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 0, 1)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                     } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 0, -1)).func_177230_c())) {
                        return new Scaffold.BlockData(pos2.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                     } else {
                        BlockPos pos7 = pos.func_177982_a(2, 0, 0);
                        if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, -1, 0)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(0, -1, 0), EnumFacing.UP);
                        } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 1, 0)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                        } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(-1, 0, 0)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                        } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(1, 0, 0)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(1, 0, 0), EnumFacing.WEST);
                        } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 0, 1)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                        } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos3.func_177982_a(0, 0, -1)).func_177230_c())) {
                           return new Scaffold.BlockData(pos3.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                        } else {
                           BlockPos pos8 = pos.func_177982_a(0, 0, 2);
                           if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, -1, 0)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(0, -1, 0), EnumFacing.UP);
                           } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 1, 0)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                           } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(-1, 0, 0)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                           } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(1, 0, 0)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(1, 0, 0), EnumFacing.WEST);
                           } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 0, 1)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                           } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos4.func_177982_a(0, 0, -1)).func_177230_c())) {
                              return new Scaffold.BlockData(pos4.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                           } else {
                              BlockPos pos9 = pos.func_177982_a(0, 0, -2);
                              if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, -1, 0)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(0, -1, 0), EnumFacing.UP);
                              } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 1, 0)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                              } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                              } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(1, 0, 0)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(1, 0, 0), EnumFacing.WEST);
                              } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 0, 1)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                              } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos5.func_177982_a(0, 0, -1)).func_177230_c())) {
                                 return new Scaffold.BlockData(pos5.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                              } else {
                                 BlockPos pos10 = pos.func_177982_a(0, -1, 0);
                                 if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(0, -1, 0)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(0, -1, 0), EnumFacing.UP);
                                 } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(0, 1, 0)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                                 } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                                 } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(1, 0, 0)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(1, 0, 0), EnumFacing.WEST);
                                 } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(0, 0, 1)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                                 } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos10.func_177982_a(0, 0, -1)).func_177230_c())) {
                                    return new Scaffold.BlockData(pos10.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                                 } else {
                                    BlockPos pos11 = pos10.func_177982_a(1, 0, 0);
                                    if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(0, -1, 0)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(0, -1, 0), EnumFacing.UP);
                                    } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(0, 1, 0)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                                    } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                                    } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(1, 0, 0)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(1, 0, 0), EnumFacing.WEST);
                                    } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(0, 0, 1)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                                    } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos11.func_177982_a(0, 0, -1)).func_177230_c())) {
                                       return new Scaffold.BlockData(pos11.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                                    } else {
                                       BlockPos pos12 = pos10.func_177982_a(-1, 0, 0);
                                       if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(0, -1, 0)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(0, -1, 0), EnumFacing.UP);
                                       } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(0, 1, 0)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                                       } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                                       } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(1, 0, 0)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(1, 0, 0), EnumFacing.WEST);
                                       } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(0, 0, 1)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                                       } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos12.func_177982_a(0, 0, -1)).func_177230_c())) {
                                          return new Scaffold.BlockData(pos12.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                                       } else {
                                          BlockPos pos13 = pos10.func_177982_a(0, 0, 1);
                                          if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(0, -1, 0)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(0, -1, 0), EnumFacing.UP);
                                          } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                                          } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(0, 1, 0)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                                          } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(1, 0, 0)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(1, 0, 0), EnumFacing.WEST);
                                          } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(0, 0, 1)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                                          } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos13.func_177982_a(0, 0, -1)).func_177230_c())) {
                                             return new Scaffold.BlockData(pos13.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
                                          } else {
                                             BlockPos pos14 = pos10.func_177982_a(0, 0, -1);
                                             if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(0, -1, 0)).func_177230_c())) {
                                                return new Scaffold.BlockData(pos14.func_177982_a(0, -1, 0), EnumFacing.UP);
                                             } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(0, 1, 0)).func_177230_c())) {
                                                return new Scaffold.BlockData(pos14.func_177982_a(0, 1, 0), EnumFacing.DOWN);
                                             } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(-1, 0, 0)).func_177230_c())) {
                                                return new Scaffold.BlockData(pos14.func_177982_a(-1, 0, 0), EnumFacing.EAST);
                                             } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(1, 0, 0)).func_177230_c())) {
                                                return new Scaffold.BlockData(pos14.func_177982_a(1, 0, 0), EnumFacing.WEST);
                                             } else if (!this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(0, 0, 1)).func_177230_c())) {
                                                return new Scaffold.BlockData(pos14.func_177982_a(0, 0, 1), EnumFacing.NORTH);
                                             } else {
                                                return !this.invalid.contains(mc.field_71441_e.func_180495_p(pos14.func_177982_a(0, 0, -1)).func_177230_c()) ? new Scaffold.BlockData(pos14.func_177982_a(0, 0, -1), EnumFacing.SOUTH) : null;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private float[] aimAtLocation(double x, double y, double z, EnumFacing facing) {
      EntitySnowball temp = new EntitySnowball(mc.field_71441_e);
      temp.field_70165_t = x + 0.5D;
      temp.field_70163_u = y - 2.7035252353D;
      temp.field_70161_v = z + 0.5D;
      return this.aimAtLocation(temp.field_70165_t, temp.field_70163_u, temp.field_70161_v);
   }

   private float[] aimAtLocation(double positionX, double positionY, double positionZ) {
      double x = positionX - mc.field_71439_g.field_70165_t;
      double y = positionY - mc.field_71439_g.field_70163_u;
      double z = positionZ - mc.field_71439_g.field_70161_v;
      double distance = (double)MathHelper.func_76133_a(x * x + z * z);
      return new float[]{(float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-(Math.atan2(y, distance) * 180.0D / 3.141592653589793D))};
   }

   private class BlockData {
      public BlockPos position;
      public EnumFacing face;

      public BlockData(BlockPos position, EnumFacing face) {
         this.position = position;
         this.face = face;
      }
   }
}
