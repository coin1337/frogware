package fail.mercury.client.api.gui.hudeditor;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fail.mercury.client.api.manager.type.HashMapManager;
import fail.mercury.client.client.hudcomponents.ArrayList;
import fail.mercury.client.client.hudcomponents.Speed;
import fail.mercury.client.client.hudcomponents.TargetHUD;
import fail.mercury.client.client.hudcomponents.Watermark;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ComponentManager extends HashMapManager<String, HudComponent> {
   private File directory;

   public ComponentManager(File directory) {
      this.directory = directory;
      if (!directory.exists()) {
         directory.mkdir();
      }

   }

   public void load() {
      super.load();
      this.register(new Watermark(), new ArrayList(), new TargetHUD(), new Speed());
      this.getRegistry().values().forEach(HudComponent::init);
      this.loadComponents();
   }

   public void unload() {
      this.saveComponents();
   }

   public void register(HudComponent... components) {
      HudComponent[] var2 = components;
      int var3 = components.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         HudComponent component = var2[var4];
         if (component.getLabel() != null) {
            this.include(component.getLabel().toLowerCase(), component);
         }
      }

   }

   public void saveComponents() {
      if (this.getValues().isEmpty()) {
         this.directory.delete();
      }

      File[] files = this.directory.listFiles();
      if (!this.directory.exists()) {
         this.directory.mkdir();
      } else if (files != null) {
         File[] var2 = files;
         int var3 = files.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            file.delete();
         }
      }

      this.getValues().forEach((comp) -> {
         File file = new File(this.directory, comp.getLabel() + ".json");
         JsonObject node = new JsonObject();
         comp.save(node);
         if (!node.entrySet().isEmpty()) {
            try {
               file.createNewFile();
            } catch (IOException var17) {
               return;
            }

            try {
               Writer writer = new FileWriter(file);
               Throwable var5 = null;

               try {
                  writer.write((new GsonBuilder()).setPrettyPrinting().create().toJson(node));
               } catch (Throwable var16) {
                  var5 = var16;
                  throw var16;
               } finally {
                  if (writer != null) {
                     if (var5 != null) {
                        try {
                           writer.close();
                        } catch (Throwable var15) {
                           var5.addSuppressed(var15);
                        }
                     } else {
                        writer.close();
                     }
                  }

               }
            } catch (IOException var19) {
               file.delete();
            }

         }
      });
      files = this.directory.listFiles();
      if (files == null || files.length == 0) {
         this.directory.delete();
      }

   }

   public void loadComponents() {
      this.getValues().forEach((comp) -> {
         File file = new File(this.directory, comp.getLabel() + ".json");
         if (file.exists()) {
            try {
               Reader reader = new FileReader(file);
               Throwable var4 = null;

               try {
                  JsonElement node = (new JsonParser()).parse(reader);
                  if (node.isJsonObject()) {
                     comp.load(node.getAsJsonObject());
                     return;
                  }
               } catch (Throwable var16) {
                  var4 = var16;
                  throw var16;
               } finally {
                  if (reader != null) {
                     if (var4 != null) {
                        try {
                           reader.close();
                        } catch (Throwable var15) {
                           var4.addSuppressed(var15);
                        }
                     } else {
                        reader.close();
                     }
                  }

               }

            } catch (IOException var18) {
            }
         }
      });
   }

   public HudComponent find(Class<? extends HudComponent> clazz) {
      return (HudComponent)this.getValues().stream().filter((m) -> {
         return m.getClass() == clazz;
      }).findFirst().orElse((Object)null);
   }

   public HudComponent find(String find) {
      HudComponent m = (HudComponent)this.pull(find.replaceAll(" ", ""));
      if (this.pull(find.replaceAll(" ", "")) != null) {
         m = (HudComponent)this.pull(find.replaceAll(" ", ""));
      }

      return m;
   }
}
