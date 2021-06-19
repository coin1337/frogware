package fail.mercury.client.client.commands;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;

@CommandManifest(
   label = "Modules",
   description = "Lists modules.",
   aliases = {"mods"}
)
public class ModulesCommand extends Command {
   public void onRun(String[] args) {
      StringBuilder mods = new StringBuilder("Modules (" + Mercury.INSTANCE.getModuleManager().getRegistry().values().size() + "): ");
      Mercury.INSTANCE.getModuleManager().getRegistry().values().forEach((mod) -> {
         mods.append(mod.isEnabled() ? "§a" : "§c").append(mod.getLabel()).append("§r, ");
      });
      ChatUtil.print(mods.toString().substring(0, mods.length() - 2));
   }
}
