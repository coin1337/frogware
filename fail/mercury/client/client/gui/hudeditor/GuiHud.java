package fail.mercury.client.client.gui.hudeditor;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.util.MouseUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.gui.click.Menu;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiHud extends GuiScreen {
   private ScaledResolution scaledResolution = null;

   public void func_73863_a(int mx, int my, float p_drawScreen_3_) {
      super.func_73863_a(mx, my, p_drawScreen_3_);
      if (this.scaledResolution == null) {
         this.scaledResolution = new ScaledResolution(this.field_146297_k);
      }

      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         if (hudComponent.isDragging()) {
            hudComponent.setX((float)mx + hudComponent.getLastX());
            hudComponent.setY((float)my + hudComponent.getLastY());
         }

         if (hudComponent.getX() < 0.0F) {
            hudComponent.setX(0.0F);
         }

         if (hudComponent.getX() + hudComponent.getW() > (float)(new ScaledResolution(this.field_146297_k)).func_78326_a()) {
            hudComponent.setX((float)(new ScaledResolution(this.field_146297_k)).func_78326_a() - hudComponent.getW());
         }

         if (hudComponent.getY() < 0.0F) {
            hudComponent.setY(0.0F);
         }

         if (hudComponent.getY() + hudComponent.getH() > (float)(new ScaledResolution(this.field_146297_k)).func_78328_b()) {
            hudComponent.setY((float)(new ScaledResolution(this.field_146297_k)).func_78328_b() - hudComponent.getH());
         }

         if (hudComponent.isShown()) {
            hudComponent.onDraw(new ScaledResolution(this.field_146297_k));
         }

         RenderUtil.drawRect2(hudComponent.getX(), hudComponent.getY(), hudComponent.getW(), hudComponent.getH(), hudComponent.isDragging() ? -1795162112 : Integer.MIN_VALUE);
         if (hudComponent.isLabelShown()) {
            Menu.font.drawStringWithShadow(hudComponent.getLabel(), (double)(hudComponent.getX() + hudComponent.getW() / 2.0F - (float)(this.field_146297_k.field_71466_p.func_78256_a(hudComponent.getLabel()) / 2)), (double)(hudComponent.getY() + hudComponent.getH() / 2.0F - (float)(this.field_146297_k.field_71466_p.field_78288_b / 2)), (new Color(255, 255, 255, 83)).getRGB());
         }

      });
   }

   protected void func_73869_a(char p_keyTyped_1_, int p_keyTyped_2_) throws IOException {
      super.func_73869_a(p_keyTyped_1_, p_keyTyped_2_);
   }

   protected void func_73864_a(int mx, int my, int p_mouseClicked_3_) throws IOException {
      super.func_73864_a(mx, my, p_mouseClicked_3_);
      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         boolean hovered = MouseUtil.withinBounds(mx, my, hudComponent.getX(), hudComponent.getY(), hudComponent.getW(), hudComponent.getH());
         switch(p_mouseClicked_3_) {
         case 0:
            if (hovered) {
               hudComponent.setDragging(true);
               hudComponent.setLastX(hudComponent.getX() - (float)mx);
               hudComponent.setLastY(hudComponent.getY() - (float)my);
            }
            break;
         case 1:
            if (hovered) {
               hudComponent.setShown(!hudComponent.isShown());
            }
         }

      });
   }

   protected void func_146286_b(int mx, int my, int p_mouseReleased_3_) {
      super.func_146286_b(mx, my, p_mouseReleased_3_);
      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         if (p_mouseReleased_3_ == 0 && hudComponent.isDragging()) {
            hudComponent.setDragging(false);
         }

      });
   }

   public void func_146281_b() {
      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         if (hudComponent.isDragging()) {
            hudComponent.setDragging(false);
         }

      });
   }

   public boolean func_73868_f() {
      return false;
   }
}
