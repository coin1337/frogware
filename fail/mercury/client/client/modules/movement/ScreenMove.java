package fail.mercury.client.client.modules.movement;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.UpdateEvent;
import fail.mercury.client.client.gui.click.Menu;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleManifest(
   label = "ScreenMove",
   fakelabel = "Screen Move",
   aliases = {"GuiMove", "InvWalk", "ScreenWalk", "GuiWalk"},
   category = Category.MOVEMENT
)
public class ScreenMove extends Module {
   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (event.getTiming().equals(EventTiming.PRE)) {
         if (mc.field_71462_r instanceof GuiChat || mc.field_71462_r instanceof Menu || mc.field_71462_r instanceof GuiEditSign || mc.field_71462_r == null) {
            return;
         }

         KeyBinding[] moveKeys = new KeyBinding[]{mc.field_71474_y.field_74351_w, mc.field_71474_y.field_74368_y, mc.field_71474_y.field_74370_x, mc.field_71474_y.field_74366_z, mc.field_71474_y.field_74314_A};
         KeyBinding[] var3 = moveKeys;
         int var4 = moveKeys.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            KeyBinding bind = var3[var5];
            KeyBinding.func_74510_a(bind.func_151463_i(), Keyboard.isKeyDown(bind.func_151463_i()));
         }

         if (Mouse.isButtonDown(2)) {
            Mouse.setGrabbed(true);
            mc.field_71415_G = true;
         } else {
            Mouse.setGrabbed(false);
            mc.field_71415_G = false;
         }
      }

   }
}
