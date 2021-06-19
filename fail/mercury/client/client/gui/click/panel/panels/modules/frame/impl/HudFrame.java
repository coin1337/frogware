package fail.mercury.client.client.gui.click.panel.panels.modules.frame.impl;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import fail.mercury.client.client.gui.click.panel.panels.modules.frame.Frame;
import fail.mercury.client.client.gui.click.panel.panels.modules.impl.HudGuiComponent;
import java.awt.Color;
import java.util.Iterator;

public class HudFrame extends Frame {
   public HudFrame(float x, float y, float w, float h) {
      super("Hud Components", x, y, w, h);
   }

   public void init() {
      float offsetY = this.getH() - 2.0F;

      for(Iterator var2 = Mercury.INSTANCE.getHudManager().getValues().iterator(); var2.hasNext(); offsetY += 15.0F) {
         HudComponent hudComponent = (HudComponent)var2.next();
         this.getComponents().add(new HudGuiComponent(hudComponent, this.getX(), this.getY(), 2.0F, offsetY, this.getW() - 4.0F, 15.0F));
      }

      super.init();
   }

   public void moved(float x, float y) {
      super.moved(x, y);
   }

   public void drawScreen(int x, int y, float partialTicks) {
      RenderUtil.drawRect2(this.getX() - 1.0F, this.getY() - 2.0F, this.getW() + 3.0F, this.getH(), (new Color(45, 45, 45)).getRGB());
      Menu.font.drawStringWithShadow(this.getLabel(), (double)(this.getX() + this.getW() / 2.0F - (float)(Menu.font.getStringWidth(this.getLabel()) / 2)), (double)(this.getY() - 2.0F + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2)), -1);
      super.drawScreen(x, y, partialTicks);
      if (this.isExtended()) {
         this.resetHeights();
      }

   }

   public void mouseClicked(int x, int y, int button) {
      super.mouseClicked(x, y, button);
   }

   public void mouseReleased(int x, int y, int button) {
      super.mouseReleased(x, y, button);
   }

   public void keyTyped(char character, int key) {
      super.keyTyped(character, key);
   }

   private void resetHeights() {
      float offsetY = this.getH() - 2.0F;

      Component component;
      for(Iterator var2 = this.getComponents().iterator(); var2.hasNext(); offsetY += component.getH()) {
         component = (Component)var2.next();
         component.setOffsety(offsetY);
         component.moved(this.getX(), this.getY());
         Component component1;
         if (component instanceof HudGuiComponent && ((HudGuiComponent)component).isExtended()) {
            for(Iterator var4 = component.getSubComponents().iterator(); var4.hasNext(); offsetY += component1.getH()) {
               component1 = (Component)var4.next();
            }
         }
      }

   }

   private float getHeight() {
      float offsetY = 0.0F;

      Component component;
      for(Iterator var2 = this.getComponents().iterator(); var2.hasNext(); offsetY += component.getH()) {
         component = (Component)var2.next();
         Component component1;
         if (component instanceof HudGuiComponent && ((HudGuiComponent)component).isExtended()) {
            for(Iterator var4 = component.getSubComponents().iterator(); var4.hasNext(); offsetY += component1.getH()) {
               component1 = (Component)var4.next();
            }
         }
      }

      return offsetY;
   }
}
