package fail.mercury.client.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityUtil implements Util {
   public static void setTimer(float speed) {
      mc.field_71428_T.field_194149_e = 50.0F / speed;
   }

   public static void resetTimer() {
      mc.field_71428_T.field_194149_e = 50.0F;
   }

   public static Block isColliding(double posX, double posY, double posZ) {
      Block block = null;
      if (mc.field_71439_g != null) {
         AxisAlignedBB bb = mc.field_71439_g.func_184187_bx() != null ? mc.field_71439_g.func_184187_bx().func_174813_aQ().func_191195_a(0.0D, 0.0D, 0.0D).func_72317_d(posX, posY, posZ) : mc.field_71439_g.func_174813_aQ().func_191195_a(0.0D, 0.0D, 0.0D).func_72317_d(posX, posY, posZ);
         int y = (int)bb.field_72338_b;

         for(int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d) + 1; ++x) {
            for(int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f) + 1; ++z) {
               block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
            }
         }
      }

      return block;
   }

   public static boolean isInLiquid() {
      if (mc.field_71439_g == null) {
         return false;
      } else if (mc.field_71439_g.field_70143_R >= 3.0F) {
         return false;
      } else {
         boolean inLiquid = false;
         AxisAlignedBB bb = mc.field_71439_g.func_184187_bx() != null ? mc.field_71439_g.func_184187_bx().func_174813_aQ() : mc.field_71439_g.func_174813_aQ();
         int y = (int)bb.field_72338_b;

         for(int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d) + 1; ++x) {
            for(int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f) + 1; ++z) {
               Block block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
               if (!(block instanceof BlockAir)) {
                  if (!(block instanceof BlockLiquid)) {
                     return false;
                  }

                  inLiquid = true;
               }
            }
         }

         return inLiquid;
      }
   }

   public static boolean isInBlock() {
      for(int x = MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72340_a); x < MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72336_d) + 1; ++x) {
         for(int y = MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72338_b); y < MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72337_e) + 1; ++y) {
            for(int z = MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72339_c); z < MathHelper.func_76128_c(mc.field_71439_g.func_174813_aQ().field_72334_f) + 1; ++z) {
               Block block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
               if (block != null && !(block instanceof BlockAir)) {
                  AxisAlignedBB boundingBox = block.func_180646_a(mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)), mc.field_71441_e, new BlockPos(x, y, z));
                  if (block instanceof BlockHopper) {
                     boundingBox = new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
                  }

                  if (boundingBox != null && mc.field_71439_g.func_174813_aQ().func_72326_a(boundingBox)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }
}
