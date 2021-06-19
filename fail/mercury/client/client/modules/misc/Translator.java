package fail.mercury.client.client.modules.misc;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.translate.Language;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.modules.persistent.Commands;
import java.io.IOException;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.apache.commons.lang3.StringUtils;

@ModuleManifest(
   label = "Translator",
   category = Category.MISC,
   description = "Automatically translates messages."
)
public class Translator extends Module {
   @Property("Incoming")
   public boolean incoming = false;
   @Property("MainLang")
   @Mode({"Azerbaijan", "Albanian", "Amharic", "English", "Arabic", "Armenian", "Afrikaans", "Basque", "Bashkir", "Belarusian", "Bengali", "Burmese", "Bulgarian", "Bosnian", "Welsh", "Hungarian", "Vietnamese", "Haitian", "Galician", "Dutch", "HillMari", "Greek", "Georgian", "Gujarati", "Danish", "Hebrew", "Yiddish", "Indonesian", "Irish", "Italian", "Icelandic", "Spanish", "Kazakh", "Kannada", "Catalan", "Kyrgyz", "Chinese", "Korean", "Xhosa", "Khmer", "Laotian", "Latin", "Latvian", "Lithuanian", "Luxembourgish", "Malagasy", "Malay", "Malayalam", "Maltese", "Macedonian", "Maori", "Marathi", "Mari", "Mongolian", "German", "Nepali", "Norwegian", "Russian", "Punjabi", "Papiamento", "Persian", "Polish", "Portuguese", "Romanian", "Cebuano", "Serbian", "Sinhala", "Slovakian", "Slovenian", "Swahili", "Sundanese", "Tajik", "Thai", "Tagalog", "Tamil", "Tatar", "Telugu", "Turkish", "Udmurt", "Uzbek", "Ukrainian", "Urdu", "Finnish", "French", "Hindi", "Croatian", "Czech", "Swedish", "Scottish", "Estonian", "Esperanto", "Javanese", "Japanese"})
   public String mainLang = "English";
   @Property("Outgoing")
   public boolean outgoing = false;
   @Property("OutgoingLang")
   @Mode({"Azerbaijan", "Albanian", "Amharic", "English", "Arabic", "Armenian", "Afrikaans", "Basque", "Bashkir", "Belarusian", "Bengali", "Burmese", "Bulgarian", "Bosnian", "Welsh", "Hungarian", "Vietnamese", "Haitian", "Galician", "Dutch", "HillMari", "Greek", "Georgian", "Gujarati", "Danish", "Hebrew", "Yiddish", "Indonesian", "Irish", "Italian", "Icelandic", "Spanish", "Kazakh", "Kannada", "Catalan", "Kyrgyz", "Chinese", "Korean", "Xhosa", "Khmer", "Laotian", "Latin", "Latvian", "Lithuanian", "Luxembourgish", "Malagasy", "Malay", "Malayalam", "Maltese", "Macedonian", "Maori", "Marathi", "Mari", "Mongolian", "German", "Nepali", "Norwegian", "Russian", "Punjabi", "Papiamento", "Persian", "Polish", "Portuguese", "Romanian", "Cebuano", "Serbian", "Sinhala", "Slovakian", "Slovenian", "Swahili", "Sundanese", "Tajik", "Thai", "Tagalog", "Tamil", "Tatar", "Telugu", "Turkish", "Udmurt", "Uzbek", "Ukrainian", "Urdu", "Finnish", "French", "Hindi", "Croatian", "Czech", "Swedish", "Scottish", "Estonian", "Esperanto", "Javanese", "Japanese"})
   public String outgoingMode = "English";

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (this.outgoing && event.getType().equals(PacketEvent.Type.OUTGOING) && event.getPacket() instanceof CPacketChatMessage) {
         CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
         if (packet.func_149439_c().startsWith("/") || packet.func_149439_c().startsWith(Commands.prefix)) {
            return;
         }

         try {
            Language sourceLang = Language.valueOf(this.mainLang.equalsIgnoreCase("HillMari") ? "HillMari" : StringUtils.capitalize(this.mainLang));
            Language lang = Language.valueOf(this.mainLang.equalsIgnoreCase("HillMari") ? "HillMari" : StringUtils.capitalize(this.outgoingMode));
            packet.field_149440_a = Mercury.INSTANCE.getTranslationManager().translate(packet.func_149439_c(), sourceLang, lang);
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

   }
}
