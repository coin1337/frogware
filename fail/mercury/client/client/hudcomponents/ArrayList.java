package fail.mercury.client.client.hudcomponents;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.gui.hudeditor.annotation.HudManifest;
import fail.mercury.client.api.module.Module;
import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import me.kix.lotus.property.annotations.Property;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.PotionEffect;

@HudManifest(
   label = "Array List",
   x = 2.0F,
   y = 22.0F,
   width = 70.0F,
   height = 18.0F
)
public class ArrayList extends HudComponent {
   @Property("Rainbow")
   public boolean rainbow = false;
   @Property("Effects")
   public boolean effects = false;
   private java.util.ArrayList<Module> sorted;

   public ArrayList() {
      this.sorted = new java.util.ArrayList(Mercury.INSTANCE.getModuleManager().getToggles());
   }

   public void onDraw(ScaledResolution scaledResolution) {
      super.onDraw(scaledResolution);
      if (this.mc.field_71441_e != null && this.mc.field_71439_g != null) {
         boolean compensateEffect = this.effects && this.getY() + this.getH() / 2.0F > (float)(scaledResolution.func_78328_b() / 2) && this.getX() + this.getW() / 2.0F > (float)(scaledResolution.func_78326_a() / 2) && this.mc.field_71439_g.func_70651_bq().size() > 0;
         float compensatedY = 0.0F;
         if (compensateEffect) {
            boolean bad = false;
            boolean good = false;
            Iterator var6 = this.mc.field_71439_g.func_70651_bq().iterator();

            while(var6.hasNext()) {
               PotionEffect effect = (PotionEffect)var6.next();
               if (effect.func_188419_a().func_76398_f()) {
                  bad = true;
               } else {
                  good = true;
               }
            }

            if (good) {
               compensatedY += 25.0F;
            }

            if (bad) {
               compensatedY += 25.0F;
            }
         }

         float y = this.getY() + (compensateEffect ? compensatedY : 0.0F);
         this.sorted.sort(Comparator.comparingDouble((m) -> {
            return (double)(-font.getStringWidth(this.getLabel(m)));
         }));
         Iterator var9 = this.sorted.iterator();

         while(var9.hasNext()) {
            Module module = (Module)var9.next();
            if (module.isEnabled() && !module.isHidden()) {
               font.drawStringWithShadow(this.getLabel(module), (double)(this.getX() + (this.getX() + this.getW() / 2.0F > (float)(scaledResolution.func_78326_a() / 2) ? this.getW() - (float)font.getStringWidth(this.getLabel(module)) : 0.0F)), (double)(y + (this.getY() + this.getH() / 2.0F > (float)(scaledResolution.func_78328_b() / 2) ? this.getH() - (float)font.getHeight() : 0.0F)), this.rainbow ? getRainbow(6000, (int)(y * 30.0F), 0.85F) : -1);
               y += this.getY() + this.getH() / 2.0F > (float)(scaledResolution.func_78328_b() / 2) ? (float)(-(font.getHeight() + 1)) : (float)(font.getHeight() + 1);
            }
         }

      }
   }

   public static int getRainbow(int speed, int offset, float s) {
      float hue = (float)((System.currentTimeMillis() + (long)offset) % (long)speed);
      hue /= (float)speed;
      return Color.getHSBColor(hue, s, 1.0F).getRGB();
   }

   public String getLabel(Module module) {
      StringBuilder string = new StringBuilder(module.getFakeLabel() != null && module.getFakeLabel().equals("") ? module.getLabel() : module.getFakeLabel());
      if (module.getSuffix() != null && !module.getSuffix().equals("")) {
         string.append(" ").append(ChatFormatting.GRAY + module.getSuffix());
      }

      return string.toString();
   }
}
