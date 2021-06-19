package fail.mercury.client.api.smalltext;

import fail.mercury.client.api.manager.type.HashMapManager;
import org.apache.commons.lang3.StringUtils;

public class SmallTextManager extends HashMapManager<String, String> {
   public void load() {
      super.load();
      this.include("a", "ᴀ");
      this.include("b", "ʙ");
      this.include("c", "ᴄ");
      this.include("d", "ᴅ");
      this.include("e", "ᴇ");
      this.include("f", "ғ");
      this.include("g", "ɢ");
      this.include("h", "ʜ");
      this.include("i", "ɪ");
      this.include("j", "ᴊ");
      this.include("k", "ᴋ");
      this.include("l", "ʟ");
      this.include("m", "ᴍ");
      this.include("n", "ɴ");
      this.include("o", "ᴏ");
      this.include("p", "ᴘ");
      this.include("q", "ǫ");
      this.include("r", "ʀ");
      this.include("s", "ꜱ");
      this.include("t", "ᴛ");
      this.include("u", "ᴜ");
      this.include("v", "ᴠ");
      this.include("w", "ᴡ");
      this.include("x", "x");
      this.include("y", "ʏ");
      this.include("z", "ᴢ");
      this.include("|", "⏐");
      this.include("-", "–");
      this.include("!", "！");
      this.include("?", "？");
   }

   public String getUnicodeFromString(String text) {
      return (String)this.getRegistry().get(text.toLowerCase());
   }

   public String convert(String text) {
      String converted = text;
      char[] var3 = text.toLowerCase().toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char c = var3[var5];
         String character = String.valueOf(c);
         converted = StringUtils.replaceIgnoreCase(converted, character, this.getUnicodeFromString(character));
      }

      return converted;
   }
}
