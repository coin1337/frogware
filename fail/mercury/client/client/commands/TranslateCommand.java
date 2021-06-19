package fail.mercury.client.client.commands;

import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;

@CommandManifest(
   label = "Translate",
   description = "Translates outgoing messages."
)
public class TranslateCommand extends Command {
   public void onRun(String[] s) {
      if (s.length <= 3) {
         ChatUtil.print("Not enough args.");
      } else {
         String messageToTranslate = s[3];
      }
   }
}
