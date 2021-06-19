package fail.mercury.client.api.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.manager.type.HashMapManager;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.client.commands.BindCommand;
import fail.mercury.client.client.commands.FriendCommand;
import fail.mercury.client.client.commands.HClipCommand;
import fail.mercury.client.client.commands.HelpCommand;
import fail.mercury.client.client.commands.MobOwnerCommand;
import fail.mercury.client.client.commands.ModulesCommand;
import fail.mercury.client.client.commands.SexCommand;
import fail.mercury.client.client.commands.ToggleCommand;
import java.util.Arrays;
import java.util.HashMap;
import me.kix.lotus.property.impl.BooleanProperty;
import me.kix.lotus.property.impl.NumberProperty;
import me.kix.lotus.property.impl.string.StringProperty;
import me.kix.lotus.property.impl.string.impl.ModeStringProperty;
import org.lwjgl.input.Keyboard;

public class CommandManager extends HashMapManager<String, Command> {
   private HashMap<String, Command> aliasMap = new HashMap();

   public void load() {
      super.load();
      this.initialize();
   }

   public void initialize() {
      this.register(new ToggleCommand(), new BindCommand(), new ModulesCommand(), new FriendCommand(), new HClipCommand(), new SexCommand(), new HelpCommand(), new MobOwnerCommand());
   }

   public HashMap<String, Command> getAliasMap() {
      return this.aliasMap;
   }

   public void register(Command... commands) {
      Command[] var2 = commands;
      int var3 = commands.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Command command = var2[var4];
         this.include(command.getLabel().toLowerCase(), command);
         if (command.getAlias().length > 0) {
            String[] var6 = command.getAlias();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String com = var6[var8];
               this.aliasMap.put(com.toLowerCase(), command);
            }
         }
      }

   }

   public void dispatch(String s) {
      String[] command = s.split(" ");
      if (command.length > 1) {
         Module m = Mercury.INSTANCE.getModuleManager().find(command[0]);
         if (m != null) {
            if (command[1].equals("help")) {
               try {
                  ChatUtil.print(m.getLabel() + "'s available properties (" + Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(m).size() + ") are:");
                  Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(m).forEach((prop) -> {
                     ChatUtil.print(prop.getLabel() + ": " + prop.getValue());
                  });
               } catch (NullPointerException var5) {
                  ChatUtil.print("This module has no properties.");
               }

               ChatUtil.print(m.getLabel() + " is bound to " + Keyboard.getKeyName(m.getBind()) + ".");
               if (!m.getDescription().equals("")) {
                  ChatUtil.print("Desc: " + m.getDescription());
               }

               return;
            }

            if (command.length >= 2) {
               Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(m).stream().filter((property) -> {
                  return command[1].equalsIgnoreCase(property.getLabel());
               }).forEach((property) -> {
                  if (property instanceof BooleanProperty) {
                     BooleanProperty booleanProperty = (BooleanProperty)property;
                     booleanProperty.setValue(!(Boolean)booleanProperty.getValue());
                     ChatUtil.print(booleanProperty.getLabel() + " has been " + ((Boolean)booleanProperty.getValue() ? "§aenabled§7" : "§cdisabled§7") + " for " + m.getLabel() + ".");
                  } else if (property instanceof ModeStringProperty) {
                     ModeStringProperty modeProperty = (ModeStringProperty)property;
                     if (command.length >= 3) {
                        modeProperty.setValue(command[2]);
                        ChatUtil.print(modeProperty.getLabel() + " has been set to " + (String)modeProperty.getValue() + " for " + ChatFormatting.AQUA + m.getLabel() + ChatFormatting.WHITE + ".");
                     } else if (command.length >= 2) {
                        ChatUtil.print(m.getLabel() + "'s available " + modeProperty.getLabel() + "s are:");
                        Arrays.stream(modeProperty.getModes()).forEach((prop) -> {
                           ChatUtil.print(((String)modeProperty.getValue()).toLowerCase().equals(prop.toLowerCase()) ? ChatFormatting.GRAY + prop : prop);
                        });
                     }
                  } else if (property instanceof NumberProperty) {
                     NumberProperty numberProperty = (NumberProperty)property;
                     if (command.length >= 3) {
                        try {
                           numberProperty.setValue(command[2]);
                           ChatUtil.print(numberProperty.getLabel() + " has been set to " + numberProperty.getValue() + " for " + ChatFormatting.AQUA + m.getLabel() + ChatFormatting.WHITE + ".");
                        } catch (NumberFormatException var6) {
                           ChatUtil.print(command[2] + " is not a number.");
                        }
                     } else {
                        ChatUtil.print("Not enough arguments to change property.");
                     }
                  } else if (property instanceof StringProperty) {
                     StringProperty stringProperty = (StringProperty)property;
                     if (command.length >= 3) {
                        stringProperty.setValue(s.substring(m.getLabel().length() + stringProperty.getLabel().length() + 2));
                        ChatUtil.print(stringProperty.getLabel() + " has been set to " + ChatFormatting.GRAY + (String)stringProperty.getValue() + ChatFormatting.WHITE + " for " + ChatFormatting.AQUA + m.getLabel() + ChatFormatting.WHITE + ".");
                     } else {
                        ChatUtil.print("Not enough arguments to change property.");
                     }
                  }

               });
            } else {
               ChatUtil.print("Property not found! Do ." + command[0] + " help for list of properties.");
            }
         }
      }

      Command c = (Command)this.getRegistry().get(command[0]);
      if (c != null) {
         c.onRun(command);
      }

      Command cc = (Command)this.getAliasMap().get(command[0]);
      if (cc != null) {
         cc.onRun(command);
      }

   }
}
