package fail.mercury.client.client.commands;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;
import java.util.Arrays;

@CommandManifest(
   label = "Help",
   description = "Lists Commands."
)
public class HelpCommand extends Command {
   public void onRun(String[] args) {
      Mercury.INSTANCE.getCommandManager().getRegistry().values().forEach((cmd) -> {
         if (Arrays.stream(cmd.getAlias()).count() > 0L) {
            StringBuilder aliases = new StringBuilder();
            Arrays.stream(cmd.getAlias()).forEach((alias) -> {
               aliases.append(alias).append("§r, ");
            });
            ChatUtil.print(String.format("%s (%s) - %s", cmd.getLabel(), aliases.toString().substring(0, aliases.length() - 2), cmd.getDescription().equals("") ? "No Description found." : cmd.getDescription()));
         } else {
            ChatUtil.print(String.format("%s - %s", cmd.getLabel(), cmd.getDescription().equals("") ? "No Description found." : cmd.getDescription()));
         }

      });
   }
}
