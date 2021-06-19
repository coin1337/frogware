package fail.mercury.client.client.modules.visual;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;

@ModuleManifest(
   label = "FullBright",
   aliases = {"Brightness"},
   category = Category.VISUAL
)
public class FullBright extends Module {
   private float originalgamma;

   public void onEnable() {
      this.originalgamma = mc.field_71474_y.field_74333_Y;
      mc.field_71474_y.field_74333_Y = 1.5999999E7F;
   }

   public void onDisable() {
      mc.field_71474_y.field_74333_Y = this.originalgamma;
   }
}
