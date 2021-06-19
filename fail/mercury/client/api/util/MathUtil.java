package fail.mercury.client.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class MathUtil {
   private static final Random random = new Random();

   public static <T extends Number> T clamp(T value, T minimum, T maximum) {
      return value.floatValue() <= minimum.floatValue() ? minimum : (value.floatValue() >= maximum.floatValue() ? maximum : value);
   }

   public static int getRandom(int min, int max) {
      return min + random.nextInt(max - min + 1);
   }

   public static double getRandom(double min, double max) {
      return MathHelper.func_151237_a(min + random.nextDouble() * max, min, max);
   }

   public static float getRandom(float min, float max) {
      return MathHelper.func_76131_a(min + random.nextFloat() * max, min, max);
   }

   public static Vec3d interpolateEntity(Entity entity, float time) {
      return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)time);
   }

   public static Vec3i interpolateEntityInt(Entity entity, float time) {
      return new Vec3i(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)time);
   }

   public static double roundToPlace(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal(value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   public static double round(double in, int places) {
      places = MathHelper.func_76125_a(places, 0, Integer.MAX_VALUE);
      return Double.parseDouble(String.format("%." + places + "f", in));
   }

   public static double roundDouble(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal(value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   public static float roundFloat(float value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal((double)value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.floatValue();
      }
   }
}
