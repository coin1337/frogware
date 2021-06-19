package fail.mercury.client.client.modules.misc;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.UpdateEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Mode;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

@ModuleManifest(
   label = "BanWave",
   category = Category.MISC,
   fakelabel = "Ban Wave",
   description = "Automatically bans players."
)
public class BanWave extends Module {
   private TimerUtil timer = new TimerUtil();
   public ArrayList<Entity> banned = new ArrayList();
   public static Path path;
   @Property("Mode")
   @Mode({"Normal", "Read"})
   private String mode = "Normal";
   @Property("SendMessage")
   public boolean message = true;
   @Property("Message")
   private String banMessage = "bannd bi mercury benwav ez ee";
   @Property("Delay")
   @Clamp(
      minimum = "1"
   )
   private int banDelay = 10;

   public void onEnable() {
      if (!Files.exists(path, new LinkOption[0])) {
         try {
            Files.createFile(path);
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

      this.banned.clear();
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.mode.equalsIgnoreCase("normal")) {
         Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

         while(var2.hasNext()) {
            Object o = var2.next();
            if (o instanceof EntityOtherPlayerMP) {
               EntityOtherPlayerMP e = (EntityOtherPlayerMP)o;
               if (this.timer.hasReached((long)(this.banDelay * 100)) && !Mercury.INSTANCE.getFriendManager().isFriend(e.func_70005_c_()) && e.func_70005_c_() != mc.field_71439_g.func_70005_c_() && !this.banned.contains(e)) {
                  if (this.message) {
                     mc.field_71439_g.func_71165_d("/ban " + e.func_70005_c_() + " " + this.banMessage);
                     System.out.println("banned " + e.func_70005_c_() + " " + this.banMessage);
                  } else {
                     mc.field_71439_g.func_71165_d("/ban " + e.func_70005_c_());
                     System.out.println("banned " + e.func_70005_c_());
                  }

                  this.banned.add(e);
                  this.timer.reset();
               }
            }
         }
      }

      if (this.mode.equalsIgnoreCase("read")) {
         try {
            if (Files.lines(path).count() <= 0L) {
               ChatUtil.print("No names found in file " + path.getFileName());
               return;
            }

            Files.lines(path).forEach((n) -> {
               Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

               while(var2.hasNext()) {
                  Object o = var2.next();
                  if (o instanceof EntityOtherPlayerMP) {
                     EntityOtherPlayerMP e = (EntityOtherPlayerMP)o;
                     if (e.func_70005_c_().equalsIgnoreCase(n) && this.timer.hasReached((long)(this.banDelay * 100)) && !Mercury.INSTANCE.getFriendManager().isFriend(e.func_70005_c_()) && e.func_70005_c_() != mc.field_71439_g.func_70005_c_() && !this.banned.contains(e)) {
                        if (this.message) {
                           mc.field_71439_g.func_71165_d("/ban " + e.func_70005_c_() + " " + this.banMessage);
                           System.out.println("banned " + e.func_70005_c_() + " " + this.banMessage);
                        } else {
                           mc.field_71439_g.func_71165_d("/ban " + e.func_70005_c_());
                           System.out.println("banned " + e.func_70005_c_());
                        }

                        this.banned.add(e);
                        this.timer.reset();
                     }
                  }
               }

            });
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

   }

   static {
      path = (new File(Mercury.INSTANCE.getDirectory(), "banwave-names.txt")).toPath();
   }
}
