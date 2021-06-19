package fail.mercury.client.client.gui.click.panel.panels.modules.impl;

import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import java.awt.Color;
import me.kix.lotus.property.impl.string.impl.ModeStringProperty;
import org.apache.commons.lang3.StringUtils;

public class ModeComponent extends Component {
   private ModeStringProperty ModeStringProperty;

   public ModeComponent(ModeStringProperty ModeStringProperty, float x, float y, float offsetx, float offsety, float w, float h) {
      super(ModeStringProperty.getLabel(), x, y, offsetx, offsety, w, h);
      this.ModeStringProperty = ModeStringProperty;
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
      Menu.font.drawStringWithShadow(StringUtils.capitalize(this.getLabel()) + ": " + StringUtils.capitalize(((String)this.ModeStringProperty.getValue()).toLowerCase()), (double)(this.getX() + 2.0F), (double)(this.getY() + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2)), -1);
   }

   public void mouseClicked(int mx, int my, int button) {
      super.mouseClicked(mx, my, button);
      boolean hovered = MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH());
      if (button == 0 && hovered) {
         this.ModeStringProperty.increment();
      }

      if (button == 1 && hovered) {
         this.ModeStringProperty.decrement();
      }

   }
}
