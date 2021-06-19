package fail.mercury.client.client.hudcomponents;

import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.gui.hudeditor.annotation.HudManifest;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;

@HudManifest(
   label = "Watermark",
   x = 2.0F,
   y = 2.0F,
   width = 58.0F,
   height = 18.0F,
   showlabel = false
)
public class Watermark extends HudComponent {
   public void onDraw(ScaledResolution scaledResolution) {
      super.onDraw(scaledResolution);
      if (this.mc.field_71441_e != null && this.mc.field_71439_g != null) {
         float x = this.getX();
         float y = this.getY();
         font.drawStringWithShadow("FrogWare", (double)x, (double)y, Color.WHITE.getRGB());
      }
   }
}
