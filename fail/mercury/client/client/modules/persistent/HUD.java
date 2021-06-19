package fail.mercury.client.client.modules.persistent;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.client.events.FullScreenEvent;
import fail.mercury.client.client.events.Render2DEvent;
import fail.mercury.client.client.events.ResizeEvent;
import fail.mercury.client.client.gui.hudeditor.GuiHud;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@ModuleManifest(
   label = "HUD",
   aliases = {"Overlay"},
   category = Category.VISUAL,
   persistent = true,
   hidden = true
)
public class HUD extends Module {
   @EventHandler
   public void onRender(Render2DEvent eventRender) {
      if (!mc.field_71474_y.field_74330_P && !(mc.field_71462_r instanceof GuiHud)) {
         new ScaledResolution(mc);
         Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
            if (hudComponent.getX() < 0.0F) {
               hudComponent.setX(0.0F);
            }

            if (hudComponent.getX() + hudComponent.getW() > (float)(new ScaledResolution(Minecraft.func_71410_x())).func_78326_a()) {
               hudComponent.setX((float)(new ScaledResolution(Minecraft.func_71410_x())).func_78326_a() - hudComponent.getW());
            }

            if (hudComponent.getY() < 0.0F) {
               hudComponent.setY(0.0F);
            }

            if (hudComponent.getY() + hudComponent.getH() > (float)(new ScaledResolution(Minecraft.func_71410_x())).func_78328_b()) {
               hudComponent.setY((float)(new ScaledResolution(Minecraft.func_71410_x())).func_78328_b() - hudComponent.getH());
            }

            if (hudComponent.isShown()) {
               hudComponent.onDraw(new ScaledResolution(mc));
            }

         });
      }
   }

   @EventHandler
   public void onScreenResize(ResizeEvent event) {
      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         if (hudComponent.isShown()) {
            hudComponent.onResize(event.getSr());
         }

      });
   }

   @EventHandler
   public void onFullScreen(FullScreenEvent event) {
      Mercury.INSTANCE.getHudManager().getValues().forEach((hudComponent) -> {
         if (hudComponent.isShown()) {
            hudComponent.onFullScreen(event.getWidth(), event.getHeight());
         }

      });
   }
}
