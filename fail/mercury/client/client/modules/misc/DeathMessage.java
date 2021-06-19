package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;

@ModuleManifest(
   label = "DeathMessage",
   category = Category.MISC,
   fakelabel = "DeathMessage",
   description = "Send message when you die."
)
public class DeathMessage extends Module {
   @Property("AutoRespawn")
   public boolean autorespawn = false;
   @Property("Death Message")
   private String suffix = "lag killed me not you skid";

   @EventHandler
   public void onUpdate(GuiScreenEvent event) {
      if (event.getGui() instanceof GuiGameOver && this.autorespawn) {
         mc.field_71439_g.func_71004_bE();
         mc.func_147108_a((GuiScreen)null);
         mc.field_71439_g.func_71165_d(this.suffix);
      }

   }
}
