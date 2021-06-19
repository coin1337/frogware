package fail.mercury.client.client.modules.visual;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.events.OutlineEvent;
import fail.mercury.client.client.events.Render3DEvent;
import fail.mercury.client.client.modules.combat.KillAura;
import java.awt.Color;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

@ModuleManifest(
   label = "EntityESP",
   fakelabel = "Entity ESP",
   aliases = {"PlayerESP"},
   category = Category.VISUAL
)
public class EntityESP extends Module {
   @Property("Mode")
   @Mode({"Box", "Outline"})
   public static String mode = "Box";
   @Property("Radius")
   @Clamp(
      maximum = "5"
   )
   public float radiusValue = 0.1F;
   @Property("Box")
   public static boolean box = true;
   @Property("Outline")
   public static boolean outline = true;
   @Property("Players")
   public static boolean players = true;
   @Property("Mobs")
   public static boolean mobs = true;
   @Property("Items")
   public static boolean items = true;
   @Property("Invisibles")
   public static boolean invisibles = true;
   @Property("TargetESP")
   public static boolean targetESP = false;
   @Property("CustomColor")
   public static boolean color = false;
   @Property("Red")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int red = 255;
   @Property("Green")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int green = 255;
   @Property("Blue")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int blue = 255;

   @EventHandler
   public void onRender3D(Render3DEvent event) {
      mc.field_71441_e.field_72996_f.forEach((entity) -> {
         if (doesQualify(entity)) {
            Color clr = this.getColor(entity);
            if (mode.equalsIgnoreCase("box")) {
               Vec3d vec = MathUtil.interpolateEntity(entity, event.getPartialTicks());
               double posX = vec.field_72450_a - mc.func_175598_ae().field_78725_b;
               double posY = vec.field_72448_b - mc.func_175598_ae().field_78726_c;
               double posZ = vec.field_72449_c - mc.func_175598_ae().field_78723_d;
               float alpha = 40.0F;
               if (box) {
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase ent = (EntityLivingBase)entity;
                     if (ent.field_70737_aN > 0) {
                        alpha += 10.0F;
                     }
                  }

                  RenderUtil.drawESP((new AxisAlignedBB(0.0D, 0.0D, 0.0D, (double)entity.field_70130_N, (double)entity.field_70131_O, (double)entity.field_70130_N)).func_72317_d(posX - (double)(entity.field_70130_N / 2.0F), posY, posZ - (double)(entity.field_70130_N / 2.0F)), (float)clr.getRed(), (float)clr.getGreen(), (float)clr.getBlue(), alpha);
               }

               if (outline) {
                  RenderUtil.drawESPOutline((new AxisAlignedBB(0.0D, 0.0D, 0.0D, (double)entity.field_70130_N, (double)entity.field_70131_O, (double)entity.field_70130_N)).func_72317_d(posX - (double)(entity.field_70130_N / 2.0F), posY, posZ - (double)(entity.field_70130_N / 2.0F)), (float)clr.getRed(), (float)clr.getGreen(), (float)clr.getBlue(), 255.0F, 1.0F);
               }
            }
         }

      });
   }

   @EventHandler
   public void onOutline(OutlineEvent event) {
      if (mode.equalsIgnoreCase("outline")) {
         mc.field_71438_f.field_174991_A.field_148031_d.forEach((shader) -> {
            ShaderUniform radius = shader.func_148043_c().func_147991_a("Radius");
            if (radius != null) {
               radius.func_148090_a(this.radiusValue);
            }

         });
         mc.field_71441_e.field_72996_f.forEach((entity) -> {
            if (doesQualify(entity) && event.getEntity() == entity) {
               event.setCancelled(true);
            }

         });
      }

   }

   public Color getColor(Entity entity) {
      if (color) {
         return new Color(this.red, this.green, this.blue);
      } else if (Mercury.INSTANCE.getFriendManager().isFriend(entity.func_70005_c_())) {
         return new Color(155, 100, 180);
      } else if (entity.func_70093_af()) {
         return new Color(192, 58, 100);
      } else if (entity instanceof EntityItem) {
         return new Color(116, 255, 253);
      } else if (Mercury.INSTANCE.getModuleManager().find(KillAura.class).isEnabled() && KillAura.target != null && KillAura.target.equals(entity) && targetESP) {
         return new Color(233, 11, 0);
      } else {
         return entity instanceof EntityPlayer ? new Color(255, 63, 43) : new Color(255, 125, 40);
      }
   }

   private static boolean doesQualify(Entity entity) {
      return entity != null && entity.func_70089_S() && RenderUtil.isInViewFrustrum(entity) && entity != mc.field_71439_g && (entity instanceof EntityPlayer && players || entity instanceof EntityItem && items || (entity instanceof EntityAnimal || entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityVillager) && mobs) && (!entity.func_82150_aj() || invisibles);
   }
}
