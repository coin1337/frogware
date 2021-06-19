package fail.mercury.client.client.hudcomponents;

import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.gui.hudeditor.annotation.HudManifest;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

@HudManifest(
   label = "Speed",
   x = 2.0F,
   y = 10.0F,
   width = 58.0F,
   height = 18.0F,
   showlabel = false
)
public class Speed extends HudComponent {
   public void onDraw(ScaledResolution scaledResolution) {
      super.onDraw(scaledResolution);
      if (this.mc.field_71441_e != null && this.mc.field_71439_g != null) {
         float x = this.getX();
         float y = this.getY();
         DecimalFormat df = new DecimalFormat("#.#");
         double deltaX = this.mc.field_71439_g.field_70165_t - this.mc.field_71439_g.field_70169_q;
         double deltaZ = this.mc.field_71439_g.field_70161_v - this.mc.field_71439_g.field_70166_s;
         float tickRate = this.mc.field_71428_T.field_194149_e / 1000.0F;
         String bps = "BPS: " + df.format((double)(MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ) / tickRate));
         font.drawStringWithShadow(bps, (double)x, (double)y, Color.WHITE.getRGB());
      }
   }
}
