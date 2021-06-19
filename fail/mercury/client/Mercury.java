package fail.mercury.client;

import fail.mercury.client.api.command.CommandManager;
import fail.mercury.client.api.friend.FriendManager;
import fail.mercury.client.api.gui.hudeditor.ComponentManager;
import fail.mercury.client.api.module.ModuleManager;
import fail.mercury.client.api.profile.ProfileManager;
import fail.mercury.client.api.smalltext.SmallTextManager;
import fail.mercury.client.api.tickrate.TickRateManager;
import fail.mercury.client.api.translate.TranslationManager;
import fail.mercury.client.api.util.Util;
import java.io.File;
import me.kix.lotus.property.manage.PropertyManager;
import net.b0at.api.event.Event;
import net.b0at.api.event.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Mercury {
   public static Mercury INSTANCE = new Mercury();
   public static final String NAME = "FrogWare";
   public static final String VERSION = "1.0";
   private final PropertyManager propertyManager = new PropertyManager();
   private EventManager<Event> eventManager = new EventManager(Event.class);
   private ModuleManager moduleManager;
   private ComponentManager componentManager;
   private CommandManager commandManager = new CommandManager();
   private FriendManager friendManager = new FriendManager();
   private TickRateManager tickRateManager = new TickRateManager();
   private ProfileManager profileManager = new ProfileManager();
   private SmallTextManager smallTextManager = new SmallTextManager();
   private TranslationManager translationManager = new TranslationManager();
   private File directory;
   public static final Logger log = LogManager.getLogger("FrogWare");

   public Mercury() {
      this.directory = new File(Util.mc.field_71412_D, "FrogWare");
   }

   public void init() {
      try {
         if (!this.directory.exists()) {
            this.directory.mkdir();
         }

         String title = String.format("%s %s | 1.12.2", this.getName(), this.getVersion());
         Display.setTitle(title);
         this.propertyManager.load();
         this.moduleManager = new ModuleManager(new File(this.directory, "modules"));
         this.moduleManager.load();
         this.componentManager = new ComponentManager(new File(this.directory, "hud"));
         this.componentManager.load();
         this.friendManager.setDirectory(new File(this.directory, "friends.json"));
         this.friendManager.load();
         this.commandManager.load();
         this.smallTextManager.load();
         this.profileManager.load();
         this.tickRateManager.load();
         Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void shutdown() {
      this.moduleManager.unload();
      this.componentManager.unload();
      this.friendManager.unload();
      this.tickRateManager.unload();
   }

   public String getName() {
      return "FrogWare";
   }

   public String getVersion() {
      return "1.0";
   }

   public File getDirectory() {
      return this.directory;
   }

   public PropertyManager getPropertyManager() {
      return this.propertyManager;
   }

   public EventManager<Event> getEventManager() {
      return this.eventManager;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public ComponentManager getHudManager() {
      return this.componentManager;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public FriendManager getFriendManager() {
      return this.friendManager;
   }

   public TickRateManager getTickRateManager() {
      return this.tickRateManager;
   }

   public ProfileManager getProfileManager() {
      return this.profileManager;
   }

   public SmallTextManager getSmallTextManager() {
      return this.smallTextManager;
   }

   public TranslationManager getTranslationManager() {
      return this.translationManager;
   }
}
