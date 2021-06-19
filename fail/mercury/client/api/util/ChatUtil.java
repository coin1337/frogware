package fail.mercury.client.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.Mercury;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements Util {
   public static final void print(String text) {
      mc.field_71456_v.func_146158_b().func_146227_a(new TextComponentString(String.format(ChatFormatting.WHITE + "[" + ChatFormatting.AQUA + "%s" + ChatFormatting.WHITE + "]:" + ChatFormatting.WHITE + " %s", Mercury.INSTANCE.getName().substring(0, 1), text)));
   }
}
