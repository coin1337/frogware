package fail.mercury.client.api.profile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fail.mercury.client.api.manager.type.HashMapManager;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.UUID;
import java.util.Map.Entry;

public class ProfileManager extends HashMapManager<String, UUID> {
   private static final String NAME = "https://api.mojang.com/users/profiles/minecraft/%s";
   private static final String PROFILE = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

   public void load() {
      super.load();
   }

   public void unload() {
      this.getRegistry().clear();
   }

   public UUID getUUID(String name) {
      if (this.getRegistry().containsKey(name)) {
         return (UUID)this.getRegistry().get(name);
      } else {
         try {
            Reader uuidReader = new InputStreamReader((new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name))).openStream());
            JsonObject jsonObject = (new JsonParser()).parse(uuidReader).getAsJsonObject();
            String unfomatted = jsonObject.get("id").getAsString();
            String formatted = "";
            int[] var6 = new int[]{8, 4, 4, 4, 12};
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               int length = var6[var8];
               formatted = formatted + "-";

               for(int i = 0; i < length; ++i) {
                  formatted = formatted + unfomatted.charAt(0);
                  unfomatted = unfomatted.substring(1);
               }
            }

            formatted = formatted.substring(1);
            UUID uuid = UUID.fromString(formatted);
            this.getRegistry().put(name, uuid);
            return uuid;
         } catch (Exception var11) {
            var11.printStackTrace();
            return null;
         }
      }
   }

   public String getName(UUID uuid) {
      try {
         if (this.getRegistry().containsValue(uuid)) {
            return (String)((Entry)this.getRegistry().entrySet().stream().filter((entry) -> {
               return uuid == entry.getValue();
            }).findFirst().get()).getKey();
         }
      } catch (Exception var6) {
      }

      try {
         Reader uuidReader = new InputStreamReader((new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", uuid.toString().replaceAll("-", "")))).openStream());
         JsonObject jsonObject = (new JsonParser()).parse(uuidReader).getAsJsonObject();
         String name = jsonObject.get("name").getAsString();
         this.getRegistry().put(name, uuid);
         return name;
      } catch (Exception var5) {
         var5.printStackTrace();
         return "";
      }
   }
}
