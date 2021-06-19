package fail.mercury.client.client.modules.combat;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.AngleUtil;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

@ModuleManifest(
   label = "KillAura",
   aliases = {"Aura"},
   fakelabel = "Kill Aura",
   category = Category.COMBAT,
   description = "Automatically attacks players."
)
public class KillAura extends Module {
   @Property("Range")
   @Clamp(
      minimum = "3.0",
      maximum = "6.0"
   )
   public static double reach = 4.2D;
   @Property("FOV")
   @Clamp(
      minimum = "1",
      maximum = "180"
   )
   public static int fov = 180;
   @Property("MaxTargets")
   @Clamp(
      minimum = "1"
   )
   public int maxTargets = 6;
   @Property("SwitchSwings")
   @Clamp(
      minimum = "1"
   )
   public int switchSwings = 2;
   @Property("Silent")
   public boolean silent = true;
   @Property("Invisibles")
   public static boolean invisibles = true;
   @Property("Walls")
   public static boolean walls = false;
   @Property("Players")
   public static boolean players = true;
   @Property("Mobs")
   public static boolean mobs = true;
   @Property("Sync")
   public boolean sync = true;
   @Property("Criticals")
   public boolean crits = false;
   public static List<EntityLivingBase> targets = new ArrayList();
   public static EntityLivingBase target;
   private int index;
   private int hits;
   public static float yaw;
   public static float pitch;

   public void onDisable() {
      targets.clear();
      target = null;
   }

   public void onEnable() {
   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof Rotation) {
         Rotation packet = (Rotation)event.getPacket();
         yaw = packet.field_149476_e;
         pitch = packet.field_149473_f;
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (mc.field_71439_g == null) {
         target = null;
      }

      if (event.getTiming().equals(EventTiming.PRE)) {
         targets = this.getTargets();
         if (targets.size() > this.maxTargets) {
            if (this.index >= this.maxTargets) {
               this.index = 0;
            }
         } else if (this.index >= targets.size()) {
            this.index = 0;
         }

         if (!targets.isEmpty()) {
            target = (EntityLivingBase)targets.get(this.index);
            Vec3d vec = AngleUtil.resolveBestHitVec(target, 3, walls);
            if (vec != null) {
               float[] rotation = AngleUtil.getRotationFromPosition(vec.field_72450_a, vec.field_72449_c, vec.field_72448_b - (double)mc.field_71439_g.func_70047_e());
               event.getRotation().setYaw(!this.silent ? (mc.field_71439_g.field_70177_z = rotation[0]) : rotation[0]);
               event.getRotation().setPitch(!this.silent ? (mc.field_71439_g.field_70125_A = (double)mc.field_71439_g.func_70032_d(target) <= 0.5D ? 90.0F : rotation[1]) : ((double)mc.field_71439_g.func_70032_d(target) <= 0.5D ? 90.0F : rotation[1]));
               float ticks = 20.0F - Mercury.INSTANCE.getTickRateManager().getTickRate();
               boolean canAttack = mc.field_71439_g.func_184825_o(this.sync ? -ticks : 0.0F) >= 1.0F && AngleUtil.rayTrace(event.getRotation().getYaw(), event.getRotation().getPitch(), reach) != null;
               if (canAttack) {
                  if (Mercury.INSTANCE.getModuleManager().find(Criticals.class).isEnabled() && target != null && Criticals.mode.equalsIgnoreCase("edit") && Criticals.canCrit(target)) {
                     if (mc.field_71439_g.field_70143_R != 0.0F) {
                        Criticals.waitDelay = 2;
                     }

                     if (Criticals.waitDelay <= 0) {
                        Criticals.waitDelay = 0;
                        if (Criticals.canCrit(target)) {
                           event.getLocation().setOnGround(false);
                           ++Criticals.groundTicks;
                           if (Criticals.groundTicks == 1) {
                              event.getLocation().setOnGround(false);
                              event.getLocation().setY(event.getLocation().getY() + 0.0625101D);
                           } else if (Criticals.groundTicks == 2) {
                              event.getLocation().setOnGround(false);
                              event.getLocation().setY(event.getLocation().getY() + 0.062666D);
                           } else if (Criticals.groundTicks == 3) {
                              event.getLocation().setOnGround(false);
                              event.getLocation().setY(event.getLocation().getY() + 1.0E-4D);
                           } else if (Criticals.groundTicks >= 4) {
                              event.getLocation().setOnGround(false);
                              event.getLocation().setY(event.getLocation().getY() + 1.0E-4D);
                              Criticals.groundTicks = 0;
                           }
                        } else {
                           Criticals.waitDelay = 2;
                        }
                     } else {
                        --Criticals.waitDelay;
                     }
                  }

                  if (this.crits && !mc.field_71439_g.field_70122_E && (double)mc.field_71439_g.field_70143_R < 0.1D) {
                     return;
                  }

                  if (AngleUtil.rayTrace(event.getRotation().getYaw(), event.getRotation().getPitch(), reach) != null) {
                     this.attack(target);
                  }

                  ++this.hits;
               }
            }
         } else if (target != null) {
            target = null;
         }
      }

      if (target != null && targets.size() > 0 && this.hits > this.switchSwings) {
         ++this.index;
         this.hits = 0;
      }

   }

   public void attack(Entity entity) {
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(entity));
      mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      if (Mercury.INSTANCE.getModuleManager().find(Criticals.class).isEnabled() && Criticals.canCrit(entity) && Criticals.mode.equalsIgnoreCase("edit") && Criticals.groundTicks != 0) {
         mc.field_71441_e.func_184148_a((EntityPlayer)null, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, SoundEvents.field_187718_dS, mc.field_71439_g.func_184176_by(), 1.0F, 1.0F);
         mc.field_71439_g.func_71009_b(entity);
      }

      mc.field_71439_g.func_184821_cY();
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
      return entity != null && entity.func_70089_S() && AngleUtil.isEntityInFov(entity, (double)fov) && entity.func_70089_S() && entity.func_110143_aJ() > 0.0F && entity != mc.field_71439_g && (entity instanceof EntityPlayer && players || (entity instanceof EntityAnimal || entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityVillager) && mobs) && entity.func_70068_e(mc.field_71439_g) <= d * d && (!entity.func_82150_aj() || invisibles) && !Mercury.INSTANCE.getFriendManager().isFriend(entity.func_70005_c_());
   }
}
