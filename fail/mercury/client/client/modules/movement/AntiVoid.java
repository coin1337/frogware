package fail.mercury.client.client.modules.movement;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;

@ModuleManifest(
   label = "AntiVoid",
   fakelabel = "Anti Void",
   category = Category.MOVEMENT,
   description = "Prevents you from falling in da void"
)
public class AntiVoid extends Module {
   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (event.getTiming().equals(EventTiming.PRE) && mc.field_71439_g.field_70143_R > 5.0F && mc.field_71439_g.field_70163_u < 0.0D) {
         event.getLocation().setY(event.getLocation().getY() + 8.0D);
      }

   }
}
