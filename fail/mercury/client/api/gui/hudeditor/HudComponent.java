package fail.mercury.client.api.gui.hudeditor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fail.mercury.client.Mercury;
import fail.mercury.client.api.gui.hudeditor.annotation.HudManifest;
import fail.mercury.client.api.util.font.CFontRenderer;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class HudComponent {
   public float x;
   public float y;
   public float width;
   public float height;
   public float lastX;
   public float lastY;
   public String label;
   private boolean shown;
   private boolean dragging;
   private boolean showLabel;
   public Minecraft mc = Minecraft.func_71410_x();
   private ScaledResolution sr;
   public static CFontRenderer font = new CFontRenderer(new Font("Calibri", 0, 20), true, true);

   public HudComponent() {
      this.sr = new ScaledResolution(this.mc);
      if (this.getClass().isAnnotationPresent(HudManifest.class)) {
         HudManifest hudManifest = (HudManifest)this.getClass().getAnnotation(HudManifest.class);
         this.label = hudManifest.label();
         this.x = hudManifest.x();
         this.y = hudManifest.y();
         this.width = hudManifest.width();
         this.height = hudManifest.height();
         this.shown = hudManifest.shown();
         this.showLabel = hudManifest.showlabel();
      }

   }

   public void onDraw(ScaledResolution scaledResolution) {
   }

   public void onResize(ScaledResolution scaledResolution) {
      if (this.sr.func_78326_a() < scaledResolution.func_78326_a() && this.getX() > (float)this.sr.func_78326_a() - this.getW() - 20.0F) {
         this.setX((float)scaledResolution.func_78326_a() - this.getW() - 2.0F);
      }

      if (this.sr.func_78328_b() < scaledResolution.func_78328_b() && this.getY() > (float)this.sr.func_78328_b() - this.getH() - 20.0F) {
         this.setY((float)scaledResolution.func_78328_b() - this.getH() - 2.0F);
      }

      if (this.sr.func_78328_b() != scaledResolution.func_78328_b() || this.sr.func_78326_a() != scaledResolution.func_78326_a()) {
         this.sr = scaledResolution;
      }

   }

   public void onFullScreen(float w, float h) {
      if ((float)this.sr.func_78326_a() < w && this.getX() > (float)this.sr.func_78326_a() - this.getW() - 20.0F) {
         this.setX(w - ((float)this.sr.func_78326_a() - this.getW()) - 2.0F);
      }

      if ((float)this.sr.func_78328_b() < h && this.getY() > (float)this.sr.func_78328_b() - this.getH() - 20.0F) {
         this.setY(h - ((float)this.sr.func_78328_b() - this.getH()) - 2.0F);
      }

      if (this.sr.func_78328_b() != (new ScaledResolution(Minecraft.func_71410_x())).func_78328_b() || this.sr.func_78326_a() != (new ScaledResolution(Minecraft.func_71410_x())).func_78326_a()) {
         this.sr = new ScaledResolution(Minecraft.func_71410_x());
      }

   }

   public void save(JsonObject directory) {
      directory.addProperty("x", this.x);
      directory.addProperty("y", this.y);
      directory.addProperty("shown", this.shown);
      if (Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this) != null) {
         Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this).forEach((property) -> {
            directory.addProperty(property.getLabel(), property.getValue().toString());
         });
      }

   }

   public void load(JsonObject directory) {
      directory.entrySet().forEach((data) -> {
         String var2 = (String)data.getKey();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case 120:
            if (var2.equals("x")) {
               var3 = 1;
            }
            break;
         case 121:
            if (var2.equals("y")) {
               var3 = 2;
            }
            break;
         case 3373707:
            if (var2.equals("name")) {
               var3 = 0;
            }
            break;
         case 109413649:
            if (var2.equals("shown")) {
               var3 = 3;
            }
         }

         switch(var3) {
         case 0:
            return;
         case 1:
            this.setX((float)((JsonElement)data.getValue()).getAsInt());
            return;
         case 2:
            this.setY((float)((JsonElement)data.getValue()).getAsInt());
            return;
         case 3:
            this.setShown(((JsonElement)data.getValue()).getAsBoolean());
            return;
         default:
         }
      });
      if (Mercury.INSTANCE.getPropertyManager().getPropertiesFromObject(this) != null) {
         directory.entrySet().forEach((entry) -> {
            Mercury.INSTANCE.getPropertyManager().getProperty(this, (String)entry.getKey()).ifPresent((property) -> {
               property.setValue(((JsonElement)entry.getValue()).getAsString());
            });
         });
      }

   }

   public void init() {
      Mercury.INSTANCE.getPropertyManager().scan(this);
   }

   public float getX() {
      return this.x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return this.y;
   }

   public void setY(float y) {
      this.y = y;
   }

   public float getW() {
      return this.width;
   }

   public float getH() {
      return this.height;
   }

   public String getLabel() {
      return this.label;
   }

   public float getLastX() {
      return this.lastX;
   }

   public void setLastX(float lastX) {
      this.lastX = lastX;
   }

   public float getLastY() {
      return this.lastY;
   }

   public void setLastY(float lastY) {
      this.lastY = lastY;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   public boolean isShown() {
      return this.shown;
   }

   public void setShown(boolean shown) {
      this.shown = shown;
   }

   public boolean isLabelShown() {
      return this.showLabel;
   }

   public void setLabelShown(boolean shown) {
      this.showLabel = shown;
   }
}
