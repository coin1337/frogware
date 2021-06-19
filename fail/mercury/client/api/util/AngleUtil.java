package fail.mercury.client.api.util;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class AngleUtil implements Util {
   public static float[] getRotations(Entity ent) {
      double x = ent.field_70165_t;
      double z = ent.field_70161_v;
      double y = ent.func_174813_aQ().field_72337_e - 4.0D;
      return getRotationFromPosition(x, z, y);
   }

   public static float[] getRotationFromPosition(double x, double z, double y) {
      double xDiff = x - Minecraft.func_71410_x().field_71439_g.field_70165_t;
      double zDiff = z - Minecraft.func_71410_x().field_71439_g.field_70161_v;
      double yDiff = y - Minecraft.func_71410_x().field_71439_g.field_70163_u;
      double hypotenuse = Math.hypot(xDiff, zDiff);
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-Math.atan2(yDiff, hypotenuse) * 180.0D / 3.141592653589793D);
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationFromPosition(BlockPos pos) {
      double xDiff = (double)pos.func_177958_n() - Minecraft.func_71410_x().field_71439_g.field_70165_t;
      double zDiff = (double)pos.func_177952_p() - Minecraft.func_71410_x().field_71439_g.field_70161_v;
      double yDiff = (double)pos.func_177956_o() - Minecraft.func_71410_x().field_71439_g.field_70163_u;
      double hypotenuse = Math.hypot(xDiff, zDiff);
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-Math.atan2(yDiff, hypotenuse) * 180.0D / 3.141592653589793D);
      return new float[]{yaw, pitch};
   }

   public static Vec3d resolveBestHitVec(Entity entity, int precision, boolean evadeBlocks) {
      try {
         Vec3d headVec = mc.field_71439_g.func_174824_e(1.0F);
         Vec3d bestHitVec = new Vec3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
         Vec3d interpolated = MathUtil.interpolateEntity(entity, mc.func_184121_ak());
         int offset = precision / 3;
         float height = entity.func_70047_e() / (float)precision;
         float width = entity.field_70130_N * 0.5F / (float)offset;

         for(int offsetY = 0; offsetY <= precision; ++offsetY) {
            for(int offsetX = -offset; offsetX <= offset; ++offsetX) {
               for(int offsetZ = -offset; offsetZ <= offset; ++offsetZ) {
                  Vec3d possibleVec = new Vec3d(interpolated.field_72450_a + (double)(width * (float)offsetX), interpolated.field_72448_b + (double)(height * (float)offsetY), interpolated.field_72449_c + (double)(width * (float)offsetZ));
                  if (evadeBlocks) {
                     RayTraceResult result = mc.field_71439_g.func_130014_f_().func_72933_a(headVec, possibleVec);
                     if (result != null) {
                        continue;
                     }
                  }

                  if (headVec.func_72438_d(possibleVec) < headVec.func_72438_d(bestHitVec)) {
                     bestHitVec = possibleVec;
                  }
               }
            }
         }

         if (bestHitVec.field_72450_a == Double.MAX_VALUE) {
            bestHitVec = null;
         }

         return bestHitVec;
      } catch (Throwable var14) {
         var14.printStackTrace();
         return entity.func_174791_d();
      }
   }

   public static Entity rayTrace(float yaw, float pitch, double range) {
      Entity entity = mc.func_175606_aa();
      if (entity != null && mc.field_71441_e != null) {
         mc.field_147125_j = null;
         float partialTicks = 1.0F;
         mc.field_71476_x = entity.func_174822_a(range, partialTicks);
         Vec3d vec3d = entity.func_174824_e(partialTicks);
         Vec3d vec31 = mc.field_71439_g.func_174806_f(pitch, yaw);
         Vec3d vec32 = vec3d.func_72441_c(vec31.field_72450_a * range, vec31.field_72448_b * range, vec31.field_72449_c * range);
         Entity pointedEntity = null;
         Vec3d vec33 = null;
         float f = 1.0F;
         List list = mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec31.field_72450_a * range, vec31.field_72448_b * range, vec31.field_72449_c * range).func_72321_a((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.field_180132_d, (p_apply_1_) -> {
            return p_apply_1_ != null && p_apply_1_.func_70067_L();
         }));
         double d2 = range;
         int i = 0;

         while(true) {
            if (i >= list.size()) {
               if (pointedEntity == null || !(d2 < range) && mc.field_71476_x != null) {
                  break;
               }

               mc.field_71476_x = new RayTraceResult(pointedEntity, vec33);
               if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                  return pointedEntity;
               }
               break;
            }

            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.func_70111_Y();
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72321_a((double)f1, (double)f1, (double)f1);
            RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec32);
            if (axisalignedbb.func_189973_a(vec3d, vec32)) {
               if (d2 >= 0.0D) {
                  pointedEntity = entity1;
                  vec33 = raytraceresult == null ? vec3d : raytraceresult.field_72307_f;
                  d2 = 0.0D;
               }
            } else if (raytraceresult != null) {
               double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
               if (d3 < d2 || d2 == 0.0D) {
                  if (entity1.func_184208_bv() == entity.func_184208_bv() && !entity1.canRiderInteract()) {
                     if (d2 == 0.0D) {
                        pointedEntity = entity1;
                        vec3d = raytraceresult.field_72307_f;
                     }
                  } else {
                     pointedEntity = entity1;
                     vec3d = raytraceresult.field_72307_f;
                     d2 = d3;
                  }
               }
            }

            ++i;
         }
      }

      return null;
   }

   public static float getAngleDifference(float direction, float rotationYaw) {
      float phi = Math.abs(rotationYaw - direction) % 360.0F;
      return phi > 180.0F ? 360.0F - phi : phi;
   }

   public static boolean isEntityInFov(EntityLivingBase entity, double angle) {
      return (double)getAngleDifference(mc.field_71439_g.field_70177_z, getRotations(entity)[0]) < angle;
   }
}
