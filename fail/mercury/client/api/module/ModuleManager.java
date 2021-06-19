package fail.mercury.client.api.module;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fail.mercury.client.api.manager.type.HashMapManager;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.Util;
import fail.mercury.client.client.modules.combat.AntiChainPop;
import fail.mercury.client.client.modules.combat.ArmorBreaker;
import fail.mercury.client.client.modules.combat.AutoTotem;
import fail.mercury.client.client.modules.combat.AutoWeb;
import fail.mercury.client.client.modules.combat.Criticals;
import fail.mercury.client.client.modules.combat.FastBow;
import fail.mercury.client.client.modules.combat.KillAura;
import fail.mercury.client.client.modules.combat.Surround;
import fail.mercury.client.client.modules.combat.Velocity;
import fail.mercury.client.client.modules.misc.AntiCrash;
import fail.mercury.client.client.modules.misc.AutoFish;
import fail.mercury.client.client.modules.misc.AutoReply;
import fail.mercury.client.client.modules.misc.BanWave;
import fail.mercury.client.client.modules.misc.CaptchaSolver;
import fail.mercury.client.client.modules.misc.ChatSuffix;
import fail.mercury.client.client.modules.misc.Crasher;
import fail.mercury.client.client.modules.misc.DiscordRPC;
import fail.mercury.client.client.modules.misc.FastPlace;
import fail.mercury.client.client.modules.misc.Freecam;
import fail.mercury.client.client.modules.misc.FurryChat;
import fail.mercury.client.client.modules.misc.Handshake;
import fail.mercury.client.client.modules.misc.KeepBreak;
import fail.mercury.client.client.modules.misc.PacketCancel;
import fail.mercury.client.client.modules.misc.Phase;
import fail.mercury.client.client.modules.misc.SpeedMine;
import fail.mercury.client.client.modules.misc.TotemDetector;
import fail.mercury.client.client.modules.misc.Translator;
import fail.mercury.client.client.modules.movement.AntiVoid;
import fail.mercury.client.client.modules.movement.Flight;
import fail.mercury.client.client.modules.movement.Jesus;
import fail.mercury.client.client.modules.movement.Scaffold;
import fail.mercury.client.client.modules.movement.ScreenMove;
import fail.mercury.client.client.modules.movement.Speed;
import fail.mercury.client.client.modules.movement.Sprint;
import fail.mercury.client.client.modules.movement.Step;
import fail.mercury.client.client.modules.persistent.Commands;
import fail.mercury.client.client.modules.persistent.HUD;
import fail.mercury.client.client.modules.persistent.KeyBinds;
import fail.mercury.client.client.modules.player.AntiHunger;
import fail.mercury.client.client.modules.player.GodMode;
import fail.mercury.client.client.modules.player.NoFall;
import fail.mercury.client.client.modules.player.NoPush;
import fail.mercury.client.client.modules.player.NoRotate;
import fail.mercury.client.client.modules.player.Revive;
import fail.mercury.client.client.modules.player.Timer;
import fail.mercury.client.client.modules.visual.EntityESP;
import fail.mercury.client.client.modules.visual.FullBright;
import fail.mercury.client.client.modules.visual.HitEffects;
import fail.mercury.client.client.modules.visual.HoleESP;
import fail.mercury.client.client.modules.visual.Location;
import fail.mercury.client.client.modules.visual.VisualRange;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleManager extends HashMapManager<String, Module> implements Util {
   private File directory;

   public ModuleManager(File directory) {
      this.directory = directory;
      if (!directory.exists()) {
         directory.mkdir();
      }

   }

   public void load() {
      super.load();
      this.register(new Commands(), new KeyBinds(), new Sprint(), new Speed(), new Velocity(), new KillAura(), new Flight(), new Timer(), new NoRotate(), new NoFall(), new VisualRange(), new Criticals(), new DiscordRPC(), new AutoFish(), new ChatSuffix(), new Phase(), new Revive(), new Freecam(), new Crasher(), new AntiCrash(), new AutoReply(), new FurryChat(), new FastBow(), new FastPlace(), new AutoTotem(), new AutoWeb(), new Surround(), new AntiHunger(), new GodMode(), new AntiChainPop(), new NoPush(), new Step(), new TotemDetector(), new CaptchaSolver(), new Jesus(), new EntityESP(), new Handshake(), new AntiVoid(), new FullBright(), new PacketCancel(), new SpeedMine(), new KeepBreak(), new BanWave(), new ArmorBreaker(), new HoleESP(), new HitEffects(), new Scaffold(), new HUD(), new Translator(), new ScreenMove(), new Location());
      this.getRegistry().values().forEach(Module::init);
      this.loadCheats();
   }

   public List<Module> getToggles() {
      List<Module> toggleableModules = new ArrayList();
      Iterator var2 = this.getValues().iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         if (module instanceof Module && !module.isPersistent()) {
            toggleableModules.add(module);
         }
      }

      return toggleableModules;
   }

   public void unload() {
      this.saveCheats();
   }

   public Module getAlias(String name) {
      Iterator var2 = this.getRegistry().values().iterator();

      while(var2.hasNext()) {
         Module f = (Module)var2.next();
         if (f.getLabel().equalsIgnoreCase(name)) {
            return f;
         }

         String[] alias;
         int length = (alias = f.getAlias()).length;

         for(int i = 0; i < length; ++i) {
            String s = alias[i];
            if (s.equalsIgnoreCase(name)) {
               return f;
            }
         }
      }

      return null;
   }

   public ArrayList<Module> getModsInCategory(Category category) {
      ArrayList<Module> mods = new ArrayList();
      Iterator var3 = this.getRegistry().values().iterator();

      while(var3.hasNext()) {
         Module m = (Module)var3.next();
         if (m.getCategory().equals(category)) {
            mods.add(m);
         }
      }

      return mods;
   }

   public void register(Module... modules) {
      Module[] var2 = modules;
      int var3 = modules.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module cheat = var2[var4];
         if (cheat.getLabel() != null) {
            this.include(cheat.getLabel().toLowerCase(), cheat);
         }
      }

   }

   public Module find(Class<? extends Module> clazz) {
      return (Module)this.getValues().stream().filter((m) -> {
         return m.getClass() == clazz;
      }).findFirst().orElse((Object)null);
   }

   public Module find(String find) {
      Module m = (Module)this.pull(find.replaceAll(" ", ""));
      if (this.pull(find.replaceAll(" ", "")) != null) {
         m = (Module)this.pull(find.replaceAll(" ", ""));
      }

      if (this.getAlias(find) != null) {
         m = this.getAlias(find);
      }

      return m;
   }

   public void saveCheats() {
      if (this.getRegistry().values().isEmpty()) {
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

      this.getRegistry().values().forEach((module) -> {
         File file = new File(this.directory, module.getLabel() + ".json");
         JsonObject node = new JsonObject();
         module.save(node);
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

   public void loadCheats() {
      this.getRegistry().values().forEach((module) -> {
         if (module.isPersistent()) {
            module.setEnabled(true);
         }

         File file = new File(this.directory, module.getLabel() + ".json");
         if (file.exists()) {
            try {
               Reader reader = new FileReader(file);
               Throwable var4 = null;

               try {
                  JsonElement node = (new JsonParser()).parse(reader);
                  if (node.isJsonObject()) {
                     module.load(node.getAsJsonObject());
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
}
