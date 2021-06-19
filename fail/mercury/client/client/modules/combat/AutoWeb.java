package fail.mercury.client.client.modules.combat;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.AngleUtil;
import fail.mercury.client.api.util.InventoryUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.UpdateEvent;
import fail.mercury.client.client.modules.misc.Freecam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "AutoWeb",
   category = Category.COMBAT,
   aliases = {"webaura"},
   fakelabel = "Auto Web",
   description = "Automatically places cobwebs on nearby entities."
)
public class AutoWeb extends Module {
   @Property("Range")
   @Clamp(
      minimum = "3.0",
      maximum = "6.0"
   )
   public static double reach = 4.2D;
   @Property("Speed")
   @Clamp(
      minimum = "1"
   )
   public int speed = 8;
   @Property("FOV")
   @Clamp(
      minimum = "1",
      maximum = "180"
   )
   public static int fov = 180;
   @Property("Invisibles")
   public static boolean invisibles = true;
   @Property("Walls")
   public static boolean walls = false;
   @Property("Players")
   public static boolean players = true;
   @Property("Mobs")
   public static boolean mobs = true;
   @Property("Hold")
   public boolean hold = true;
   @Property("Swing")
   public boolean swing = false;
   @Property("Silent")
   public boolean silent = false;
   @Property("Replenish")
   public boolean replenish = false;
   public static List<EntityLivingBase> targets = new ArrayList();
   public static EntityLivingBase target;
   public TimerUtil placeTimer = new TimerUtil();

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (mc.field_71439_g == null) {
         target = null;
      }

      if (event.getTiming().equals(EventTiming.PRE)) {
         targets = this.getTargets();
         if (!targets.isEmpty()) {
            target = (EntityLivingBase)targets.get(0);
            BlockPos pos = new BlockPos(target.field_70165_t, target.field_70163_u, target.field_70161_v);
            if (this.canPlace()) {
               float[] rotation = AngleUtil.getRotationFromPosition((double)pos.func_177958_n() + 0.5D, (double)pos.func_177952_p() + 0.5D, (double)((float)pos.func_177982_a(0, 0, 0).func_177956_o() - mc.field_71439_g.func_70047_e()));
               event.getRotation().setYaw(!this.silent ? (mc.field_71439_g.field_70177_z = rotation[0]) : rotation[0]);
               event.getRotation().setPitch(!this.silent ? (mc.field_71439_g.field_70125_A = rotation[1]) : rotation[1]);
               if (InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, Item.func_150899_d(30)) < 36 && this.replenish) {
                  InventoryUtil.swap(InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, Item.func_150899_d(30)), 44);
               }

               int slot = InventoryUtil.getItemSlotInHotbar(Item.func_150899_d(30));
               int lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
               mc.field_71439_g.field_71071_by.field_70461_c = slot;
               mc.field_71442_b.func_78765_e();
               if (this.placeTimer.hasReached((long)(1000 / this.speed))) {
                  EnumFacing[] var6 = EnumFacing.values();
                  int var7 = var6.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     EnumFacing side = var6[var8];
                     BlockPos neighbor = pos.func_177972_a(side);
                     if (this.canBeClicked(neighbor)) {
                        this.place(neighbor, side.func_176734_d());
                     }
                  }

                  this.placeTimer.reset();
               }

               mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
               mc.field_71442_b.func_78765_e();
            }
         } else if (target != null) {
            target = null;
         }
      }

   }

   public boolean canBeClicked(BlockPos pos) {
      return mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(pos), false);
   }

   public void place(BlockPos pos, EnumFacing direction) {
      if (this.swing) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
      }

      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(pos, direction, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
   }

   public boolean canPlace() {
      return InventoryUtil.getItemCount(mc.field_71439_g.field_71069_bz, Item.func_150899_d(30)) != 0;
   }

   public List<EntityLivingBase> getTargets() {
      List<EntityLivingBase> targets = new ArrayList();
      Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)o;
            if (doesQualify(entity)) {
               targets.add(entity);
            }
         }
      }

      targets.sort((o1, o2) -> {
         float[] rot1 = AngleUtil.getRotations(o1);
         float[] rot2 = AngleUtil.getRotations(o2);
         return Float.compare(rot2[0], rot1[0]);
      });
      return targets;
   }

   private static boolean doesQualify(EntityLivingBase entity) {
      double d = reach;
      BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
      boolean var10000;
      if (Mercury.INSTANCE.getModuleManager().find(Freecam.class).isEnabled() && entity != Freecam.entity) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return entity != null && !(mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockWeb) && entity.field_70122_E && entity.func_70089_S() && AngleUtil.isEntityInFov(entity, (double)fov) && entity.func_70089_S() && entity.func_110143_aJ() > 0.0F && entity != mc.field_71439_g && (entity instanceof EntityPlayer && players || (entity instanceof EntityAnimal || entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityVillager) && mobs) && entity.func_70068_e(mc.field_71439_g) <= d * d && (!entity.func_82150_aj() || invisibles) && !Mercury.INSTANCE.getFriendManager().isFriend(entity.func_70005_c_()) && (!entity.func_82150_aj() || invisibles);
   }
}
