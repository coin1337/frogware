package fail.mercury.client.client.gui.click.panel.panels.modules;

import java.util.ArrayList;

public class Component {
   private float x;
   private float y;
   private float offsetx;
   private float offsety;
   private float w;
   private float h;
   private String label;
   private ArrayList<Component> subComponents = new ArrayList();

   public Component(String label, float x, float y, float offsetx, float offsety, float w, float h) {
      this.label = label;
      this.x = x + offsetx;
      this.y = y + offsety;
      this.w = w;
      this.h = h;
      this.offsetx = offsetx;
      this.offsety = offsety;
   }

   public void init() {
      this.subComponents.forEach(Component::init);
   }

   public void moved(float x, float y) {
      this.x = x + this.offsetx;
      this.y = y + this.offsety;
      this.subComponents.forEach((subComponents) -> {
         subComponents.moved(this.getX(), this.getY());
      });
   }

   public void onGuiClosed() {
      this.subComponents.forEach((subComponents) -> {
         subComponents.onGuiClosed();
      });
   }

   public void drawScreen(int mx, int my, float partialTicks) {
      this.subComponents.forEach((subComponents) -> {
         subComponents.drawScreen(mx, my, partialTicks);
      });
   }

   public void mouseClicked(int mx, int my, int button) {
      this.subComponents.forEach((subComponents) -> {
         subComponents.mouseClicked(mx, my, button);
      });
   }

   public void mouseReleased(int mx, int my, int button) {
      this.subComponents.forEach((subComponents) -> {
         subComponents.mouseReleased(mx, my, button);
      });
   }

   public void keyTyped(char character, int key) {
      this.subComponents.forEach((subComponents) -> {
         subComponents.keyTyped(character, key);
      });
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

   public float getOffsetx() {
      return this.offsetx;
   }

   public void setOffsetx(float offsetx) {
      this.offsetx = offsetx;
   }

   public float getOffsety() {
      return this.offsety;
   }

   public void setOffsety(float offsety) {
      this.offsety = offsety;
   }

   public ArrayList<Component> getSubComponents() {
      return this.subComponents;
   }
}
