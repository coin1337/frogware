package fail.mercury.client.client.commands;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;

@CommandManifest(
   label = "Friend",
   description = "Add/Remove/List friends.",
   aliases = {"f"}
)
public class FriendCommand extends Command {
   public void onRun(String[] args) {
      if (args.length <= 1) {
         ChatUtil.print("Not enough args.");
      } else {
         String var2 = args[1];
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -934610812:
            if (var2.equals("remove")) {
               var3 = 2;
            }
            break;
         case 96417:
            if (var2.equals("add")) {
               var3 = 0;
            }
            break;
         case 99339:
            if (var2.equals("del")) {
               var3 = 1;
            }
            break;
         case 3322014:
            if (var2.equals("list")) {
               var3 = 4;
            }
            break;
         case 94746189:
            if (var2.equals("clear")) {
               var3 = 3;
            }
         }

         switch(var3) {
         case 0:
            if (args.length > 1) {
               if (Mercury.INSTANCE.getFriendManager().isFriend(args[2])) {
                  ChatUtil.print(args[2] + " is already your friend.");
                  return;
               }

               if (args.length < 4) {
                  ChatUtil.print("Added " + args[2] + " to your friends list without an alias.");
                  Mercury.INSTANCE.getFriendManager().addFriend(args[2]);
               } else {
                  ChatUtil.print("Added " + args[2] + " to your friends list with the alias " + args[3] + ".");
                  Mercury.INSTANCE.getFriendManager().addFriend(args[2], args[3]);
               }
            }
            break;
         case 1:
         case 2:
            if (args.length > 1) {
               if (!Mercury.INSTANCE.getFriendManager().isFriend(args[2])) {
                  ChatUtil.print(args[2] + " is not your friend.");
                  return;
               }

               if (Mercury.INSTANCE.getFriendManager().isFriend(args[2])) {
                  ChatUtil.print("Removed " + args[2] + " from your friends list.");
                  Mercury.INSTANCE.getFriendManager().removeFriend(args[2]);
               }
            }
            break;
         case 3:
            if (Mercury.INSTANCE.getFriendManager().getRegistry().isEmpty()) {
               ChatUtil.print("Your friends list is already empty.");
               return;
            }

            ChatUtil.print("Your have cleared your friends list. Friends removed: " + Mercury.INSTANCE.getFriendManager().getRegistry().size());
            Mercury.INSTANCE.getFriendManager().clearFriends();
            break;
         case 4:
            if (Mercury.INSTANCE.getFriendManager().getRegistry().isEmpty()) {
               ChatUtil.print("Your friends list is empty.");
               return;
            }

            ChatUtil.print("Your current friends are: ");
            Mercury.INSTANCE.getFriendManager().getRegistry().forEach((friend) -> {
               ChatUtil.print("Username: " + friend.getName() + (friend.getAlias() != null ? " - Alias: " + friend.getAlias() : ""));
            });
         }

      }
   }
}
