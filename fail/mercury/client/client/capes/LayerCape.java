package fail.mercury.client.client.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer playerRenderer;

   public LayerCape(RenderPlayer playerRenderer) {
      this.playerRenderer = playerRenderer;
   }

   public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      ResourceLocation rl = Capes.getCapeResource(player);
      ItemStack itemstack = player.func_184582_a(EntityEquipmentSlot.CHEST);
      if (player.func_152122_n() && !player.func_82150_aj() && player.func_175148_a(EnumPlayerModelParts.CAPE) && itemstack.func_77973_b() != Items.field_185160_cR && rl != null) {
         float f9 = 0.14F;
         float f10 = 0.0F;
         if (player.func_70093_af()) {
            f9 = 0.1F;
            f10 = 0.09F;
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.playerRenderer.func_110776_a(rl);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, f10, f9);
         double d0 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * (new Float(partialTicks)).doubleValue() - (player.field_70169_q + (player.field_70165_t - player.field_70169_q) * (new Float(partialTicks)).doubleValue());
         double d1 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * (new Float(partialTicks)).doubleValue() - (player.field_70167_r + (player.field_70163_u - player.field_70167_r) * (new Float(partialTicks)).doubleValue());
         double d2 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * (new Float(partialTicks)).doubleValue() - (player.field_70166_s + (player.field_70161_v - player.field_70166_s) * (new Float(partialTicks)).doubleValue());
         float f = player.field_70760_ar + (player.field_70761_aq - player.field_70760_ar) * partialTicks;
         double d3 = (new Float(MathHelper.func_76126_a(f * 0.01745329F))).doubleValue();
         double d4 = (new Float(-MathHelper.func_76134_b(f * 0.01745329F))).doubleValue();
         float f1 = (new Double(d1)).floatValue() * 10.0F;
         f1 = MathHelper.func_76131_a(f1, 3.0F, 32.0F);
         float f2 = (new Double(d0 * d3 + d2 * d4)).floatValue() * 100.0F;
         float f3 = (new Double(d0 * d4 - d2 * d3)).floatValue() * 100.0F;
         if (f2 < 0.0F) {
            f2 = 0.0F;
         }

         float f4 = player.field_71107_bF + (player.field_71109_bG - player.field_71107_bF) * partialTicks;
         f1 += MathHelper.func_76126_a((player.field_70141_P + (player.field_70140_Q - player.field_70141_P) * partialTicks) * 6.0F) * 32.0F * f4;
         if (player.func_70093_af()) {
            f1 += 20.0F;
         }

         GlStateManager.func_179114_b(5.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
         this.playerRenderer.func_177087_b().func_178728_c(0.0625F);
         GlStateManager.func_179121_F();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
