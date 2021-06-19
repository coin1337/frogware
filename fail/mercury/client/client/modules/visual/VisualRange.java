package fail.mercury.client.client.modules.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.audio.AudioPlayer;
import fail.mercury.client.api.friend.Friend;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.client.events.PlayerEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@ModuleManifest(
   label = "VisualRange",
   category = Category.MISC,
   fakelabel = "Visual Range"
)
public class VisualRange extends Module {
   @Property("SendEnterMessage")
   public boolean sendEnterMessage = false;
   @Property("EnterMessage")
   public String enterMessage = "/msg %s hi";
   @Property("SendExitMessage")
   public boolean sendExitMessage = false;
   @Property("ExitMessage")
   public String exitMessage = "/msg %s bye";
   @Property("Sound")
   public boolean sound = false;
   private int prevPlayer = -1;

   @EventHandler
   public void onPlayer(PlayerEvent event) {
      Friend friend;
      String msg;
      AudioPlayer player;
      switch(event.getType()) {
      case ENTERING:
         if (mc.field_71441_e != null && mc.field_71439_g != null && !mc.field_71439_g.field_70128_L && event.getEntity() instanceof EntityPlayer && !event.getEntity().func_70005_c_().equalsIgnoreCase(mc.field_71439_g.func_70005_c_())) {
            friend = Mercury.INSTANCE.getFriendManager().getFriend(event.getEntity().func_70005_c_());
            msg = (friend != null ? ChatFormatting.DARK_PURPLE : ChatFormatting.RED) + (friend != null ? friend.getAlias() : event.getEntity().func_70005_c_()) + ChatFormatting.WHITE + " has entered your visual range.";
            ChatUtil.print(msg);
            if (this.sendEnterMessage) {
               if (this.enterMessage.contains("%s")) {
                  mc.field_71439_g.func_71165_d(String.format(this.enterMessage, event.getEntity().func_70005_c_()));
               } else {
                  mc.field_71439_g.func_71165_d(this.enterMessage);
               }
            }

            if (this.sound) {
               player = new AudioPlayer(new ResourceLocation("assets\\mercury", "enter.wav"));
               player.play();
               player.stop();
               player.close();
            }

            if (event.getEntity().func_145782_y() == this.prevPlayer) {
               this.prevPlayer = -1;
            }
         }
         break;
      case EXITING:
         if (mc.field_71441_e != null && mc.field_71439_g != null && !mc.field_71439_g.field_70128_L && event.getEntity() instanceof EntityPlayer && !event.getEntity().func_70005_c_().equalsIgnoreCase(mc.field_71439_g.func_70005_c_()) && this.prevPlayer != event.getEntity().func_145782_y()) {
            this.prevPlayer = event.getEntity().func_145782_y();
            friend = Mercury.INSTANCE.getFriendManager().getFriend(event.getEntity().func_70005_c_());
            msg = (friend != null ? ChatFormatting.DARK_PURPLE : ChatFormatting.RED) + (friend != null ? friend.getAlias() : event.getEntity().func_70005_c_()) + ChatFormatting.WHITE + " has left your visual range.";
            ChatUtil.print(msg);
            if (this.sendExitMessage) {
               if (this.exitMessage.contains("%s")) {
                  mc.field_71439_g.func_71165_d(String.format(this.exitMessage, event.getEntity().func_70005_c_()));
               } else {
                  mc.field_71439_g.func_71165_d(this.exitMessage);
               }
            }

            if (this.sound) {
               player = new AudioPlayer(new ResourceLocation("assets\\trident", "exit.wav"));
               player.play();
               player.stop();
               player.close();
            }
         }
      }

   }
}
