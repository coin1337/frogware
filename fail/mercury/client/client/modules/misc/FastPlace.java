package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

@ModuleManifest(
   label = "FastPlace",
   category = Category.MISC,
   fakelabel = "Fast Place",
   description = "Handles right clicks faster."
)
public class FastPlace extends Module {
   @Property("EXP")
   public boolean exp = false;
   @Property("CrystalOnly")
   public boolean crystal = false;

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (!this.exp || mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemExpBottle) {
         if (!this.crystal || mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemEndCrystal) {
            mc.field_71467_ac = 0;
         }
      }
   }
}
