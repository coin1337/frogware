package fail.mercury.client.api.util;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Objects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class RenderUtil implements Util {
   private static final Frustum frustrum = new Frustum();
   private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(4);

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      double j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
      bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void glEnableOutlineMode(Color color) {
      buffer.put(0, (float)color.getRed() / 255.0F);
      buffer.put(1, (float)color.getGreen() / 255.0F);
      buffer.put(2, (float)color.getBlue() / 255.0F);
      buffer.put(3, (float)color.getAlpha() / 255.0F);
      mc.field_71460_t.func_175072_h();
      GL11.glTexEnv(8960, 8705, buffer);
      GL11.glTexEnvi(8960, 8704, 34160);
      GL11.glTexEnvi(8960, 34161, 7681);
      GL11.glTexEnvi(8960, 34176, 34166);
      GL11.glTexEnvi(8960, 34192, 768);
      GL11.glTexEnvi(8960, 34162, 7681);
      GL11.glTexEnvi(8960, 34184, 5890);
      GL11.glTexEnvi(8960, 34200, 770);
   }

   public static void glDisableOutlineMode() {
      GL11.glTexEnvi(8960, 8704, 8448);
      GL11.glTexEnvi(8960, 34161, 8448);
      GL11.glTexEnvi(8960, 34162, 8448);
      GL11.glTexEnvi(8960, 34176, 5890);
      GL11.glTexEnvi(8960, 34184, 5890);
      GL11.glTexEnvi(8960, 34192, 768);
      GL11.glTexEnvi(8960, 34200, 770);
   }

   public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
      Gui.func_73734_a((int)x, (int)y, (int)x2, (int)y2, col2);
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f2 = (float)(col1 >> 16 & 255) / 255.0F;
      float f3 = (float)(col1 >> 8 & 255) / 255.0F;
      float f4 = (float)(col1 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(f2, f3, f4, f);
      GL11.glLineWidth(l1);
      GL11.glBegin(1);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawBorderedRect2(float x, float y, float width, float height, float lineSize, int color, int borderColor) {
      drawRect2(x, y, width, height, color);
      drawRect2(x, y, lineSize, height, borderColor);
      drawRect2(x, y, width, lineSize, borderColor);
      drawRect2(x + width - lineSize, y, lineSize, height, borderColor);
      drawRect2(x, y + height - lineSize, width, lineSize, borderColor);
   }

   public static void drawRect2(float x, float y, float w, float h, int color) {
      float p_drawRect_2_ = x + w;
      float p_drawRect_3_ = y + h;
      float lvt_5_2_;
      if (x < p_drawRect_2_) {
         lvt_5_2_ = x;
         x = p_drawRect_2_;
         p_drawRect_2_ = lvt_5_2_;
      }

      if (y < p_drawRect_3_) {
         lvt_5_2_ = y;
         y = p_drawRect_3_;
         p_drawRect_3_ = lvt_5_2_;
      }

      float lvt_5_3_ = (float)(color >> 24 & 255) / 255.0F;
      float lvt_6_1_ = (float)(color >> 16 & 255) / 255.0F;
      float lvt_7_1_ = (float)(color >> 8 & 255) / 255.0F;
      float lvt_8_1_ = (float)(color & 255) / 255.0F;
      Tessellator lvt_9_1_ = Tessellator.func_178181_a();
      BufferBuilder lvt_10_1_ = lvt_9_1_.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179131_c(lvt_6_1_, lvt_7_1_, lvt_8_1_, lvt_5_3_);
      lvt_10_1_.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      lvt_10_1_.func_181662_b((double)x, (double)p_drawRect_3_, 0.0D).func_181675_d();
      lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)p_drawRect_3_, 0.0D).func_181675_d();
      lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)y, 0.0D).func_181675_d();
      lvt_10_1_.func_181662_b((double)x, (double)y, 0.0D).func_181675_d();
      lvt_9_1_.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawImage(ResourceLocation loc, int x, int y, int textureX, int textureY, int width, int height) {
      mc.field_71446_o.func_110577_a(loc);
      mc.field_71456_v.func_73729_b(x, y, textureX, textureY, width, height);
   }

   public static void drawRoundedRect(float x0, float y0, float x1, float y1, float radius, int color) {
      int numberOfArcs = true;
      float angleIncrement = 5.0F;
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      GL11.glDisable(2884);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(r, g, b, a);
      GL11.glBegin(5);
      GL11.glVertex2f(x0 + radius, y0);
      GL11.glVertex2f(x0 + radius, y1);
      GL11.glVertex2f(x1 - radius, y0);
      GL11.glVertex2f(x1 - radius, y1);
      GL11.glEnd();
      GL11.glBegin(5);
      GL11.glVertex2f(x0, y0 + radius);
      GL11.glVertex2f(x0 + radius, y0 + radius);
      GL11.glVertex2f(x0, y1 - radius);
      GL11.glVertex2f(x0 + radius, y1 - radius);
      GL11.glEnd();
      GL11.glBegin(5);
      GL11.glVertex2f(x1, y0 + radius);
      GL11.glVertex2f(x1 - radius, y0 + radius);
      GL11.glVertex2f(x1, y1 - radius);
      GL11.glVertex2f(x1 - radius, y1 - radius);
      GL11.glEnd();
      GL11.glBegin(6);
      float centerX = x1 - radius;
      float centerY = y0 + radius;
      GL11.glVertex2f(centerX, centerY);

      int i;
      float angle;
      for(i = 0; i <= 18; ++i) {
         angle = (float)i * 5.0F;
         GL11.glVertex2f((float)((double)centerX + (double)radius * Math.cos(Math.toRadians((double)angle))), (float)((double)centerY - (double)radius * Math.sin(Math.toRadians((double)angle))));
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x0 + radius;
      centerY = y0 + radius;
      GL11.glVertex2f(centerX, centerY);

      for(i = 0; i <= 18; ++i) {
         angle = (float)i * 5.0F;
         GL11.glVertex2f((float)((double)centerX - (double)radius * Math.cos(Math.toRadians((double)angle))), (float)((double)centerY - (double)radius * Math.sin(Math.toRadians((double)angle))));
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x0 + radius;
      centerY = y1 - radius;
      GL11.glVertex2f(centerX, centerY);

      for(i = 0; i <= 18; ++i) {
         angle = (float)i * 5.0F;
         GL11.glVertex2f((float)((double)centerX - (double)radius * Math.cos(Math.toRadians((double)angle))), (float)((double)centerY + (double)radius * Math.sin(Math.toRadians((double)angle))));
      }

      GL11.glEnd();
      GL11.glBegin(6);
      centerX = x1 - radius;
      centerY = y1 - radius;
      GL11.glVertex2f(centerX, centerY);

      for(i = 0; i <= 18; ++i) {
         angle = (float)i * 5.0F;
         GL11.glVertex2f((float)((double)centerX + (double)radius * Math.cos(Math.toRadians((double)angle))), (float)((double)centerY + (double)radius * Math.sin(Math.toRadians((double)angle))));
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2884);
      GL11.glDisable(3042);
      GlStateManager.func_179084_k();
   }

   public static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b(x, y + (double)height, 0.0D).func_187315_a((double)(u * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
      bufferbuilder.func_181662_b(x + (double)width, y + (double)height, 0.0D).func_187315_a((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
      bufferbuilder.func_181662_b(x + (double)width, y, 0.0D).func_187315_a((double)((u + (float)uWidth) * f), (double)(v * f1)).func_181675_d();
      bufferbuilder.func_181662_b(x, y, 0.0D).func_187315_a((double)(u * f), (double)(v * f1)).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawBox(AxisAlignedBB boundingBox) {
      if (boundingBox != null) {
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
         GlStateManager.func_187435_e((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187435_e((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
         GlStateManager.func_187437_J();
      }
   }

   public static void drawOutlinedBox(AxisAlignedBB bb) {
      GL11.glBegin(1);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
      GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
      GL11.glEnd();
   }

   public static void drawESPOutline(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(width);
      GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
      drawOutlinedBox(bb);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawESP(AxisAlignedBB bb, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
      drawBox(bb);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static boolean isInViewFrustrum(Entity entity) {
      return isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak;
   }

   public static boolean isInViewFrustrum(AxisAlignedBB bb) {
      Entity current = mc.func_175606_aa();
      frustrum.func_78547_a(((Entity)Objects.requireNonNull(current)).field_70165_t, current.field_70163_u, current.field_70161_v);
      return frustrum.func_78546_a(bb);
   }
}
