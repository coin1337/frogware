package fail.mercury.client.client.hudcomponents;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.gui.hudeditor.HudComponent;
import fail.mercury.client.api.gui.hudeditor.annotation.HudManifest;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.modules.combat.KillAura;
import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

@HudManifest(
   label = "Target HUD",
   x = 415.0F,
   y = 320.0F,
   width = 140.0F,
   height = 45.0F
)
public class TargetHUD extends HudComponent {
   public void onDraw(ScaledResolution scaledResolution) {
      super.onDraw(scaledResolution);
      if (Mercury.INSTANCE.getModuleManager().find(KillAura.class).isEnabled() && KillAura.target != null && KillAura.target instanceof EntityPlayer) {
         EntityLivingBase entity = KillAura.target;
         NetworkPlayerInfo networkPlayerInfo = this.mc.func_147114_u().func_175102_a(entity.func_110124_au());
         String ping = "Ping: " + (Objects.isNull(networkPlayerInfo) ? "0ms" : networkPlayerInfo.func_178853_c() + "ms");
         String playerName = "Name: " + StringUtils.func_76338_a(entity.func_70005_c_());
         RenderUtil.drawBorderedRect2(this.x, this.y, this.width, this.height, 0.5F, (new Color(0, 0, 0, 255)).getRGB(), (new Color(0, 0, 0, 90)).getRGB());
         RenderUtil.drawRect2(this.x, this.y, 45.0F, 45.0F, (new Color(0, 0, 0)).getRGB());
         font.drawStringWithShadow(playerName, (double)this.x + 46.5D, (double)(this.y + 4.0F), -1);
         font.drawStringWithShadow("Distance: " + MathUtil.round((double)this.mc.field_71439_g.func_70032_d(entity), 2), (double)this.x + 46.5D, (double)(this.y + 12.0F), -1);
         font.drawStringWithShadow(ping, (double)this.x + 46.5D, (double)(this.y + 28.0F), (new Color(6118236)).getRGB());
         font.drawStringWithShadow("Health: " + MathUtil.round((double)(entity.func_110143_aJ() / 2.0F), 2), (double)this.x + 46.5D, (double)(this.y + 20.0F), this.getHealthColor(entity));
         this.drawFace((double)this.x + 0.5D, (double)this.y + 0.5D, 8.0F, 8.0F, 8, 8, 44, 44, 64.0F, 64.0F, (AbstractClientPlayer)entity);
         RenderUtil.drawBorderedRect2(this.x + 46.0F, this.y + this.height - 10.0F, 92.0F, 8.0F, 0.5F, (new Color(0)).getRGB(), (new Color(35, 35, 35)).getRGB());
         float inc = 91.0F / entity.func_110138_aP();
         float end = inc * (entity.func_110143_aJ() > entity.func_110138_aP() ? entity.func_110138_aP() : entity.func_110143_aJ());
         RenderUtil.drawRect2(this.x + 46.5F, this.y + this.height - 9.5F, end, 7.0F, this.getHealthColor(entity));
      }

   }

   private void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
      try {
         ResourceLocation skin = target.func_110306_p();
         Minecraft.func_71410_x().func_110434_K().func_110577_a(skin);
         GL11.glEnable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderUtil.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
         GL11.glDisable(3042);
      } catch (Exception var15) {
      }

   }

   private int getHealthColor(EntityLivingBase player) {
      float f = player.func_110143_aJ();
      float f1 = player.func_110138_aP();
      float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
      return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | -16777216;
   }
}
