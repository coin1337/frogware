package fail.mercury.client.client.modules.player;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.EntityUtil;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;

@ModuleManifest(
   label = "Timer",
   aliases = {"GameSpeed"},
   category = Category.PLAYER
)
public class Timer extends Module {
   @Property("Speed")
   @Clamp(
      minimum = "0.1",
      maximum = "10"
   )
   private float speed = 4.0F;
   @Property("Mode")
   @Mode({"Normal", "Test"})
   private String mode = "Normal";

   public void onDisable() {
      EntityUtil.resetTimer();
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      String var2 = this.mode.toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1039745817:
         if (var2.equals("normal")) {
            var3 = 0;
         }
         break;
      case 3556498:
         if (var2.equals("test")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         EntityUtil.setTimer(this.speed);
         break;
      case 1:
         EntityUtil.setTimer(10.0F);
         if (mc.field_71439_g.field_70173_aa % 10 == 0) {
            EntityUtil.setTimer(1.0F);
         }
      }

   }
}
