package fail.mercury.client.api.mixin;

import com.mojang.authlib.GameProfile;
import fail.mercury.client.client.capes.LayerCape;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractClientPlayer.class})
public class MixinAbstractClientPlayer {
   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")}
   )
   public void AbstractClientPlayer(World worldIn, GameProfile playerProfile, CallbackInfo callbackInfo) {
      Iterator var4 = Minecraft.func_71410_x().func_175598_ae().getSkinMap().values().iterator();

      while(var4.hasNext()) {
         RenderPlayer renderPlayer = (RenderPlayer)var4.next();
         LayerCape cape = new LayerCape(renderPlayer);
         renderPlayer.func_177094_a(cape);
      }

   }
}
