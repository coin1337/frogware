package fail.mercury.client.client.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.Objects;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.EntityLivingBase;

@ModuleManifest(
   label = "DiscordRPC",
   fakelabel = "Discord RPC",
   aliases = {"RPC"},
   category = Category.MISC,
   hidden = true
)
public class DiscordRPC extends Module {
   private static club.minnced.discord.rpc.DiscordRPC LIB;
   private DiscordRichPresence presence;
   private ServerData serverData;
   private long lastTime;
   private boolean afk;
   private TimerUtil timer = new TimerUtil();
   @Property("Server")
   public boolean server = true;
   @Property("Name")
   public boolean name = true;

   public void onEnable() {
      LIB = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
      this.lastTime = System.currentTimeMillis() / 1000L;
      String applicationId = "670676274338988042";
      DiscordEventHandlers handlers = new DiscordEventHandlers();
      LIB.Discord_Initialize("670676274338988042", handlers, true, "");
      (new Thread(() -> {
         while(!Thread.currentThread().isInterrupted()) {
            this.presence = new DiscordRichPresence();
            this.presence.startTimestamp = this.lastTime;
            this.presence.largeImageText = String.format("%s %s | 1.12.2", Mercury.INSTANCE.getName(), Mercury.INSTANCE.getVersion());
            this.presence.largeImageKey = String.format("name_%s_", MathUtil.getRandom(1, 50));
            this.presence.smallImageKey = "discord";
            this.presence.smallImageText = "https://discord.io/mercurymod";
            this.presence.details = !this.afk && !(mc.field_71462_r instanceof GuiMainMenu) && !(mc.field_71462_r instanceof GuiMultiplayer) ? "Currently Exploring" : "Currently AFK";
            this.serverData = mc.func_147104_D();
            if (this.serverData != null) {
               StringBuilder sb = new StringBuilder("Multiplayer");
               if (this.server) {
                  sb.append(": " + this.serverData.field_78845_b);
               }

               if (this.name) {
                  sb.append(String.format(" (%s)", mc.func_110432_I().func_111285_a()));
               }

               this.presence.state = sb.toString();
            } else if (mc.func_71356_B()) {
               this.presence.state = "Singleplayer";
            } else if (mc.field_71462_r != null) {
               if (mc.field_71462_r instanceof GuiMainMenu) {
                  this.presence.state = "Main Menu";
               }

               if (mc.field_71462_r instanceof GuiMultiplayer) {
                  this.presence.state = "Multiplayer";
               }
            }

            LIB.Discord_UpdatePresence(this.presence);
            LIB.Discord_RunCallbacks();

            try {
               Thread.sleep(5000L);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }
         }

      }, "RPC-Callback-Handler")).start();
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (MotionUtil.getSpeed((EntityLivingBase)Objects.requireNonNull(mc.field_71439_g)) == 0.0D) {
         if (this.timer.hasReached(10000L)) {
            this.afk = true;
         }
      } else {
         this.timer.reset();
         this.afk = false;
      }

   }
}
