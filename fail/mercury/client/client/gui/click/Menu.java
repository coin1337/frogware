package fail.mercury.client.client.gui.click;

import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.api.util.font.CFontRenderer;
import fail.mercury.client.client.gui.click.panel.Panel;
import fail.mercury.client.client.gui.click.panel.panels.ConfigPanel;
import fail.mercury.client.client.gui.click.panel.panels.FriendsPanel;
import fail.mercury.client.client.gui.click.panel.panels.ModulesPanel;
import fail.mercury.client.client.gui.click.panel.panels.SettingsPanel;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class Menu extends GuiScreen {
   public List<Panel> panels = new ArrayList();
   public Panel currentPanel;
   public static CFontRenderer font = new CFontRenderer(new Font("Calibri", 0, 20), true, true);

   public void init() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.panels.add(this.currentPanel = new ModulesPanel());
      this.panels.add(new FriendsPanel());
      this.panels.add(new ConfigPanel());
      this.panels.add(new SettingsPanel());
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      super.func_73863_a(mouseX, mouseY, partialTicks);
      ScaledResolution res = new ScaledResolution(this.field_146297_k);
      int x = 0;

      Panel panel;
      for(Iterator var6 = this.panels.iterator(); var6.hasNext(); x += font.getStringWidth(panel.getLabel()) + 10) {
         panel = (Panel)var6.next();
         float renderX = (float)(res.func_78326_a() / 2 + x - this.panels.size() * 20);
         RenderUtil.drawRect((double)(renderX - 5.0F), 0.0D, (double)(renderX + (float)font.getStringWidth(panel.getLabel()) + 5.0F), (double)((float)font.getHeight() + 6.0F), (new Color(0, 0, 0, panel == this.currentPanel ? 170 : 137)).getRGB());
         font.drawStringWithShadow(panel.getLabel(), (double)renderX, 4.0D, -1);
      }

      this.currentPanel.draw(mouseX, mouseY, partialTicks);
   }

   public void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.func_73864_a(mouseX, mouseY, mouseButton);
      ScaledResolution res = new ScaledResolution(this.field_146297_k);
      int x = 0;

      Panel panel;
      for(Iterator var6 = this.panels.iterator(); var6.hasNext(); x += font.getStringWidth(panel.getLabel()) + 10) {
         panel = (Panel)var6.next();
         float renderX = (float)(res.func_78326_a() / 2 + x - this.panels.size() * 20);
         if (MouseUtil.withinBounds(mouseX, mouseY, renderX - 4.0F, 0.0F, renderX + (float)font.getStringWidth(panel.getLabel()) + 5.0F, (float)font.getHeight() + 6.0F)) {
            this.currentPanel = panel;
         }
      }

      this.currentPanel.mouseClicked(mouseX, mouseY, mouseButton);
   }

   public void func_146286_b(int mouseX, int mouseY, int mouseButton) {
      super.func_146286_b(mouseX, mouseY, mouseButton);
      this.currentPanel.mouseReleased(mouseX, mouseY, mouseButton);
   }

   public void func_73869_a(char typedChar, int key) {
      if (key == 1) {
         Minecraft.func_71410_x().field_71439_g.func_71053_j();
      }

      this.currentPanel.keyTyped(typedChar, key);
   }

   public void func_146281_b() {
      this.currentPanel.onGuiClosed();
   }

   public boolean func_73868_f() {
      return false;
   }
}
