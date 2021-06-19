package fail.mercury.client.client.modules.visual;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(
   label = "HitEffects",
   aliases = {"KillEffects"},
   fakelabel = "Hit Effects",
   category = Category.VISUAL
)
public class HitEffects extends Module {
   @Property("Mode")
   @Mode({"Lightning"})
   public String mode = "Lightning";
   @Property("Sounds")
   public boolean sounds = false;
   @Property("Death")
   public boolean death = false;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketUseEntity) {
         CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
         if (packet.func_149565_c() == Action.ATTACK) {
            Entity entity = packet.func_149564_a(mc.field_71441_e);
            String var4 = this.mode.toLowerCase();
            byte var5 = -1;
            switch(var4.hashCode()) {
            case 686445258:
               if (var4.equals("lightning")) {
                  var5 = 0;
               }
            default:
               switch(var5) {
               case 0:
                  if (this.death && !entity.field_70128_L) {
                     return;
                  }

                  EntityLightningBolt lightning = new EntityLightningBolt(mc.field_71441_e, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, true);
                  mc.field_71441_e.func_72838_d(lightning);
                  if (this.sounds) {
                     ResourceLocation thunderLocal = new ResourceLocation("minecraft", "entity.lightning.thunder");
                     SoundEvent thunderSound = new SoundEvent(thunderLocal);
                     ResourceLocation lightningImpactLocal = new ResourceLocation("minecraft", "entity.lightning.impact");
                     SoundEvent lightningImpactSound = new SoundEvent(lightningImpactLocal);
                     mc.field_71441_e.func_184133_a(mc.field_71439_g, new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), thunderSound, SoundCategory.WEATHER, 1.0F, 1.0F);
                     mc.field_71441_e.func_184133_a(mc.field_71439_g, new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), lightningImpactSound, SoundCategory.WEATHER, 1.0F, 1.0F);
                  }
               }
            }
         }
      }

   }
}
