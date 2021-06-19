package fail.mercury.client.client.modules.persistent;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.KeypressEvent;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.hudeditor.GuiHud;
import net.b0at.api.event.EventHandler;

@ModuleManifest(
   label = "KeyBinds",
   category = Category.MISC,
   description = "KeyBinds for the client.",
   hidden = true,
   persistent = true
)
public class KeyBinds extends Module {
   private Menu guiCllck;
   private GuiHud guiHud;

   @EventHandler
   public void onKeyPress(KeypressEvent event) {
      Mercury.INSTANCE.getModuleManager().getRegistry().values().forEach((m) -> {
         if (m.getBind() == event.getKey()) {
            m.toggle();
         }

      });
      if (event.getKey() == 54) {
         if (this.guiCllck == null) {
            this.guiCllck = new Menu();
            this.guiCllck.init();
         }

         mc.func_147108_a(this.guiCllck);
      }

      if (event.getKey() == 41) {
         if (this.guiHud == null) {
            this.guiHud = new GuiHud();
         }

         mc.func_147108_a(this.guiHud);
      }

   }
}
