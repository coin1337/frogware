package fail.mercury.client.client.gui.click.panel.panels;

import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.gui.click.panel.Panel;
import fail.mercury.client.client.gui.click.panel.panels.modules.frame.Frame;
import fail.mercury.client.client.gui.click.panel.panels.modules.frame.impl.CategoryFrame;
import fail.mercury.client.client.gui.click.panel.panels.modules.frame.impl.HudFrame;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ModulesPanel extends Panel {
   public static ArrayList<Frame> frames;

   public ModulesPanel() {
      super("Modules");
      frames = new ArrayList();
      int x = 2;
      int y = 20;
      Category[] var3 = Category.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Category moduleCategory = var3[var5];
         frames.add(new CategoryFrame(moduleCategory, (float)x, (float)y, 120.0F, 18.0F));
         if (x + 225 >= (new ScaledResolution(Minecraft.func_71410_x())).func_78326_a()) {
            x = 2;
            y += 20;
         } else {
            x += 125;
         }
      }

      frames.add(new HudFrame((float)x, (float)y, 120.0F, 18.0F));
      frames.forEach(Frame::init);
   }

   public void draw(int mouseX, int mouseY, float partialTicks) {
      frames.forEach((frame) -> {
         frame.drawScreen(mouseX, mouseY, partialTicks);
      });
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      frames.forEach((frame) -> {
         frame.mouseClicked(mouseX, mouseY, mouseButton);
      });
   }

   public void keyTyped(char typedChar, int keyCode) {
      frames.forEach((frame) -> {
         frame.keyTyped(typedChar, keyCode);
      });
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      frames.forEach((frame) -> {
         frame.mouseReleased(mouseX, mouseY, state);
      });
   }

   public void onGuiClosed() {
      frames.forEach((frame) -> {
         frame.onGuiClosed();
         frame.setDragging(false);
      });
   }
}
