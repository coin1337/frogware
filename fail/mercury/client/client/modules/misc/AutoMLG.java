package fail.mercury.client.client.modules.misc;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;

@ModuleManifest(
   label = "AutoMLG",
   category = Category.MISC,
   fakelabel = "Auto MLG",
   aliases = {"waternofall"},
   description = "Automatically places water below the player when falling"
)
public class AutoMLG extends Module {
}
