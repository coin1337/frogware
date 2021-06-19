package fail.mercury.client.client.gui.click.panel.panels.modules.impl;

import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import java.awt.Color;
import me.kix.lotus.property.impl.BooleanProperty;

public class BooleanComponent extends Component {
   private BooleanProperty booleanProperty;

   public BooleanComponent(BooleanProperty booleanProperty, float x, float y, float offsetx, float offsety, float w, float h) {
      super(booleanProperty.getLabel(), x, y, offsetx, offsety, w, h);
      this.booleanProperty = booleanProperty;
   }

   public void init() {
      super.init();
   }

   public void moved(float x, float y) {
      super.moved(x, y);
   }

   public void drawScreen(int mx, int my, float partialTicks) {
      super.drawScreen(mx, my, partialTicks);
      RenderUtil.drawRect2(this.getX(), this.getY(), this.getW(), this.getH(), (new Color(5, 5, 5, 200)).getRGB());
      Menu.font.drawStringWithShadow(this.getLabel(), (double)(this.getX() + 2.0F), (double)(this.getY() + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2)), (Boolean)this.booleanProperty.getValue() ? -1 : -9408400);
      RenderUtil.drawRect((double)(this.getX() + this.getW() - 13.0F), (double)(this.getY() + 4.0F), (double)(this.getX() + this.getW() - 4.0F), (double)(this.getY() + 13.0F), (new Color(140, 140, 140, 200)).getRGB());
      if ((Boolean)this.booleanProperty.getValue()) {
         RenderUtil.drawRect((double)(this.getX() + this.getW() - 12.0F), (double)(this.getY() + 5.0F), (double)(this.getX() + this.getW() - 5.0F), (double)(this.getY() + 12.0F), Color.cyan.brighter().getRGB());
      }

   }

   public void mouseClicked(int mx, int my, int button) {
      super.mouseClicked(mx, my, button);
      boolean hovered = MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH());
      if (button == 0 && hovered) {
         this.booleanProperty.setValue(!(Boolean)this.booleanProperty.getValue());
      }

   }
}
