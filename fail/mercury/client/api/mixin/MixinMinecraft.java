package fail.mercury.client.api.mixin;

import fail.mercury.client.Mercury;
import fail.mercury.client.client.capes.Capes;
import fail.mercury.client.client.events.FullScreenEvent;
import fail.mercury.client.client.events.KeypressEvent;
import fail.mercury.client.client.events.ResizeEvent;
import fail.mercury.client.client.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MixinMinecraft {
   @Shadow
   public int field_71443_c;
   @Shadow
   public int field_71440_d;

   @Inject(
      method = {"init"},
      at = {@At("RETURN")}
   )
   private void init(CallbackInfo ci) {
      Mercury.INSTANCE.init();
   }

   @Inject(
      method = {"runTick"},
      at = {@At("HEAD")}
   )
   private void runTick(CallbackInfo ci) {
      Mercury.INSTANCE.getEventManager().fireEvent(new TickEvent());
   }

   @Inject(
      method = {"init"},
      at = {@At("RETURN")}
   )
   public void startCapes(CallbackInfo ci) {
      new Capes();
   }

   @Inject(
      method = {"runTickKeyboard"},
      at = {@At(
   value = "INVOKE",
   remap = false,
   target = "Lorg/lwjgl/input/Keyboard;getEventKey()I",
   ordinal = 0,
   shift = At.Shift.BEFORE
)}
   )
   private void onKeyboard(CallbackInfo callbackInfo) {
      int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
      if (Keyboard.getEventKeyState()) {
         Mercury.INSTANCE.getEventManager().fireEvent(new KeypressEvent(i));
      }

   }

   @Inject(
      method = {"toggleFullscreen"},
      at = {@At("RETURN")}
   )
   private void onToggleFullscreen(CallbackInfo info) {
      FullScreenEvent event = new FullScreenEvent((float)this.field_71443_c, (float)this.field_71440_d);
      Mercury.INSTANCE.getEventManager().fireEvent(event);
   }

   @Inject(
      method = {"resize"},
      at = {@At("HEAD")}
   )
   public void onResize(int width, int height, CallbackInfo callbackInfo) {
      ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
      ResizeEvent rsevent = new ResizeEvent(scaledresolution);
      Mercury.INSTANCE.getEventManager().fireEvent(rsevent);
   }
}
