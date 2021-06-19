package fail.mercury.client.client.gui.click.panel.panels.modules.frame;

import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.client.gui.click.panel.panels.modules.Component;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Frame {
   private float x;
   private float y;
   private float lastx;
   private float lasty;
   private float w;
   private float h;
   private String label;
   private boolean dragging;
   private boolean extended;
   private ArrayList<Component> components = new ArrayList();

   public Frame(String label, float x, float y, float w, float h) {
      this.label = label;
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }

   public void init() {
      this.components.forEach(Component::init);
   }

   public void moved(float x, float y) {
      this.x = x;
      this.y = y;
      this.components.forEach((component) -> {
         component.moved(x, y);
      });
   }

   public void drawScreen(int mx, int my, float partialTicks) {
      if (this.isDragging()) {
         this.setX((float)mx + this.getLastx());
         this.setY((float)my + this.getLasty());
         this.moved(this.getX(), this.getY());
      }

      if (this.getX() < 0.0F) {
         this.setX(0.0F);
         this.moved(this.getX(), this.getY());
      }

      if (this.getX() + this.getW() > (float)(new ScaledResolution(Minecraft.func_71410_x())).func_78326_a()) {
         this.setX((float)(new ScaledResolution(Minecraft.func_71410_x())).func_78326_a() - this.getW());
         this.moved(this.getX(), this.getY());
      }

      if (this.getY() < 0.0F) {
         this.setY(0.0F);
         this.moved(this.getX(), this.getY());
      }

      if (this.getY() + this.getH() > (float)(new ScaledResolution(Minecraft.func_71410_x())).func_78328_b()) {
         this.setY((float)(new ScaledResolution(Minecraft.func_71410_x())).func_78328_b() - this.getH());
         this.moved(this.getX(), this.getY());
      }

      if (this.isExtended()) {
         this.components.forEach((component) -> {
            component.drawScreen(mx, my, partialTicks);
         });
      }

   }

   public void mouseClicked(int mx, int my, int button) {
      boolean hovered = MouseUtil.withinBounds(mx, my, this.getX(), this.getY(), this.getW(), this.getH());
      switch(button) {
      case 0:
         if (hovered) {
            this.setDragging(true);
            this.setLastx(this.getX() - (float)mx);
            this.setLasty(this.getY() - (float)my);
         }
         break;
      case 1:
         if (hovered) {
            this.setExtended(!this.isExtended());
         }
      }

      if (this.isExtended()) {
         this.components.forEach((component) -> {
            component.mouseClicked(mx, my, button);
         });
      }

   }

   public void mouseReleased(int mx, int my, int button) {
      switch(button) {
      case 0:
         if (this.isDragging()) {
            this.setDragging(false);
         }
      default:
         if (this.isExtended()) {
            this.components.forEach((component) -> {
               component.mouseReleased(mx, my, button);
            });
         }

      }
   }

   public void onGuiClosed() {
      this.components.forEach((component) -> {
         component.onGuiClosed();
      });
   }

   public void keyTyped(char character, int key) {
      if (this.isExtended()) {
         this.components.forEach((component) -> {
            component.keyTyped(character, key);
         });
      }

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
      return this.w;
   }

   public void setW(float w) {
      this.w = w;
   }

   public float getH() {
      return this.h;
   }

   public void setH(float h) {
      this.h = h;
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public void setExtended(boolean extended) {
      this.extended = extended;
   }

   public float getLastx() {
      return this.lastx;
   }

   public void setLastx(float lastx) {
      this.lastx = lastx;
   }

   public float getLasty() {
      return this.lasty;
   }

   public void setLasty(float lasty) {
      this.lasty = lasty;
   }

   public ArrayList<Component> getComponents() {
      return this.components;
   }
}
