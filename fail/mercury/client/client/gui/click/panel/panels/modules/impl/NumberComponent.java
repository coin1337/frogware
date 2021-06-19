package fail.mercury.client.client.gui.click.panel.panels.modules.impl;

import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import java.awt.Color;
import me.kix.lotus.property.impl.NumberProperty;
import net.minecraft.util.math.MathHelper;

public class NumberComponent extends Component {
   private NumberProperty numberProperty;
   private boolean sliding;

   public NumberComponent(NumberProperty numberProperty, float x, float y, float offsetx, float offsety, float w, float h) {
      super(numberProperty.getLabel(), x, y, offsetx, offsety, w, h);
      this.numberProperty = numberProperty;
   }

   public void init() {
      super.init();
   }

   public void moved(float x, float y) {
      super.moved(x, y);
   }

   public void drawScreen(int mx, int my, float partialTicks) {
      super.drawScreen(mx, my, partialTicks);
      float length = (float)MathHelper.func_76141_d((((Number)this.numberProperty.getValue()).floatValue() - this.numberProperty.getMinimum().floatValue()) / (this.numberProperty.getMaximum().floatValue() - this.numberProperty.getMinimum().floatValue()) * (this.getW() - 1.0F));
      RenderUtil.drawRect2(this.getX(), this.getY(), this.getW(), this.getH(), (new Color(5, 5, 5, 200)).getRGB());
      RenderUtil.drawRect2(this.getX() + 0.5F, this.getY() + this.getH() - 1.5F, length, 1.0F, Color.WHITE.getRGB());
      Menu.font.drawStringWithShadow(this.getLabel() + ": " + this.numberProperty.getValue(), (double)(this.getX() + 2.0F), (double)(this.getY() + this.getH() / 2.0F - (float)(Menu.font.getHeight() / 2) + 1.0F), -1);
      if (this.sliding) {
         if (this.numberProperty.getValue() instanceof Float) {
            float preval = ((float)mx - this.getX()) * (this.numberProperty.getMaximum().floatValue() - this.numberProperty.getMinimum().floatValue()) / this.getW() + this.numberProperty.getMinimum().floatValue();
            this.numberProperty.setValue((Number)MathUtil.roundFloat(preval, 2));
         } else if (this.numberProperty.getValue() instanceof Integer) {
            int preval = (int)(((float)mx - this.getX()) * (float)(this.numberProperty.getMaximum().intValue() - this.numberProperty.getMinimum().intValue()) / this.getW() + (float)this.numberProperty.getMinimum().intValue());
            this.numberProperty.setValue((Number)preval);
         } else if (this.numberProperty.getValue() instanceof Double) {
            double preval = (double)((float)mx - this.getX()) * (this.numberProperty.getMaximum().doubleValue() - this.numberProperty.getMinimum().doubleValue()) / (double)this.getW() + this.numberProperty.getMinimum().doubleValue();
            this.numberProperty.setValue((Number)MathUtil.roundDouble(preval, 2));
         }
      }

   }

   public void mouseClicked(int mx, int my, int button) {
      super.mouseClicked(mx, my, button);
      if (MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH()) && button == 0) {
         this.sliding = true;
      }

   }

   public void mouseReleased(int mx, int my, int button) {
      super.mouseReleased(mx, my, button);
      if (this.sliding) {
         this.sliding = false;
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
      if (this.sliding) {
         this.sliding = false;
      }

   }
}
