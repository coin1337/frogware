package fail.mercury.client.client.gui.click.panel.panels.modules.impl;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import java.awt.Color;
import java.util.Iterator;
import me.kix.lotus.property.IProperty;
import me.kix.lotus.property.impl.BooleanProperty;
import me.kix.lotus.property.impl.NumberProperty;
import me.kix.lotus.property.impl.string.impl.ModeStringProperty;

public class HudGuiComponent extends Component {
   private HudComponent hudComponent;
   private boolean extended;

   public HudGuiComponent(HudComponent hudComponent, float x, float y, float offsetx, float offsety, float w, float h) {
      super(hudComponent.getLabel(), x, y, offsetx, offsety, w, h);
      this.hudComponent = hudComponent;
   }

   public void init() {
      super.init();
      if (Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this.hudComponent) != null) {
         float offsetY = this.getH();
         Iterator var2 = Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this.hudComponent).iterator();

         while(var2.hasNext()) {
            IProperty property = (IProperty)var2.next();
            if (property.getValue() instanceof Boolean) {
               this.getSubComponents().add(new BooleanComponent((BooleanProperty)property, this.getX(), this.getY(), 0.0F, offsetY, this.getW(), this.getH()));
               offsetY += 15.0F;
            }

            if (property.getValue() instanceof Number) {
               this.getSubComponents().add(new NumberComponent((NumberProperty)property, this.getX(), this.getY(), 0.0F, offsetY, this.getW(), this.getH()));
               offsetY += 15.0F;
            }

            if (property instanceof ModeStringProperty) {
               this.getSubComponents().add(new ModeComponent((ModeStringProperty)property, this.getX(), this.getY(), 0.0F, offsetY, this.getW(), this.getH()));
               offsetY += 15.0F;
            }
         }
      }

   }

   public void moved(float x, float y) {
      super.moved(x, y);
   }

   public void drawScreen(int mx, int my, float partialTicks) {
      boolean hovered = MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH());
      RenderUtil.drawRect2(this.getX(), this.getY(), this.getW(), this.getH(), hovered ? (new Color(0, 0, 0, 200)).getRGB() : (this.hudComponent.isShown() ? (new Color(5, 5, 5, 200)).getRGB() : (new Color(14, 14, 14, 200)).getRGB()));
      if (this.hudComponent.isShown()) {
         RenderUtil.drawRect2(this.getX(), this.getY(), 1.0F, this.getH(), Color.CYAN.darker().getRGB());
      }

      try {
         if (!Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this.hudComponent).isEmpty()) {
            Menu.font.drawStringWithShadow(this.isExtended() ? "-" : "+", (double)(this.getX() + this.getW() - 10.0F), (double)(this.getY() + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2)), (new Color(200, 200, 200, 255)).getRGB());
         }
      } catch (Exception var6) {
      }

      Menu.font.drawStringWithShadow(this.getLabel(), (double)(this.getX() + this.getW() / 2.0F - (float)(Menu.font.getStringWidth(this.getLabel()) / 2)), (double)(this.getY() + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2)), this.hudComponent.isShown() ? -1 : -9408400);
      if (this.isExtended()) {
         super.drawScreen(mx, my, partialTicks);
      }

   }

   public void mouseClicked(int mx, int my, int button) {
      boolean hovered = MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH());
      switch(button) {
      case 0:
         if (hovered) {
            this.hudComponent.setShown(!this.hudComponent.isShown());
         }
         break;
      case 1:
         if (hovered && !this.getSubComponents().isEmpty()) {
            this.setExtended(!this.isExtended());
         }
      }

      if (this.isExtended()) {
         super.mouseClicked(mx, my, button);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      if (this.isExtended()) {
         super.mouseReleased(x, y, button);
      }

   }

   public HudComponent getHudComponent() {
      return this.hudComponent;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public void setExtended(boolean extended) {
      this.extended = extended;
   }
}
