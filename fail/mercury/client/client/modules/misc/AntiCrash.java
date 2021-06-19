package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.Objects;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;

@ModuleManifest(
   label = "AntiCrash",
   aliases = {"ACrash"},
   fakelabel = "Anti Crash",
   category = Category.MISC
)
public class AntiCrash extends Module {
   @Property("Slime")
   public boolean slime = true;
   @Property("Offhand")
   public boolean offhand = true;

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (Objects.nonNull(mc.field_71441_e) && this.slime) {
         mc.field_71441_e.field_72996_f.forEach((e) -> {
            if (e instanceof EntitySlime) {
               EntitySlime slime = (EntitySlime)e;
               if (slime.func_70809_q() > 4) {
                  mc.field_71441_e.func_72900_e(e);
               }
            }

         });
      }

   }

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (this.offhand && event.getType().equals(PacketEvent.Type.INCOMING) && event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).func_186978_a() == SoundEvents.field_187719_p) {
         event.setCancelled(true);
      }

   }
}
