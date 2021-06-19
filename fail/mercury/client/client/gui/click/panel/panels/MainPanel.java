package fail.mercury.client.client.gui.click.panel.panels;

import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.Panel;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class MainPanel extends Panel {
   public MainPanel() {
      super("Home");
   }

   public void draw(int mouseX, int mouseY, float partialTicks) {
      Minecraft mc = Minecraft.func_71410_x();
      Menu.font.drawStringWithShadow("lol", 20.0D, 20.0D, -1);
      ScaledResolution res = new ScaledResolution(mc);
      Gui.func_73734_a(res.func_78326_a() / 2 - 150, 20, res.func_78326_a() / 2 + 150, 500, (new Color(0, 0, 0, 137)).getRGB());
   }
}
