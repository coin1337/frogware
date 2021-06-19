package fail.mercury.client.client.capes;

import com.google.gson.Gson;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Capes {
   public static final Logger log = LogManager.getLogger("FrogWare");
   private static Capes INSTANCE;
   private Capes.CapeUser[] capeUser;

   public Capes() {
      INSTANCE = this;

      try {
         HttpsURLConnection connection = (HttpsURLConnection)(new URL("https://raw.githubusercontent.com/Crystallinqq/mercurycapes/master/capes.json")).openConnection();
         connection.connect();
         this.capeUser = (Capes.CapeUser[])(new Gson()).fromJson(new InputStreamReader(connection.getInputStream()), Capes.CapeUser[].class);
         connection.disconnect();
      } catch (Exception var5) {
         log.error("Failed to load capes");
         var5.printStackTrace();
      }

      if (this.capeUser != null) {
         Capes.CapeUser[] var6 = this.capeUser;
         int var2 = var6.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Capes.CapeUser user = var6[var3];
            this.bindTexture(user.url, "capes/baldent/" + formatUUID(user.uuid));
         }
      }

   }

   public static ResourceLocation getCapeResource(AbstractClientPlayer player) {
      Capes.CapeUser[] var1 = INSTANCE.capeUser;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Capes.CapeUser user = var1[var3];
         if (player.func_110124_au().toString().equalsIgnoreCase(user.uuid)) {
            return new ResourceLocation("capes/baldent/" + formatUUID(user.uuid));
         }
      }

      return null;
   }

   private void bindTexture(String url, String resource) {
      IImageBuffer iib = new IImageBuffer() {
         public BufferedImage func_78432_a(BufferedImage image) {
            return Capes.this.parseCape(image);
         }

         public void func_152634_a() {
         }
      };
      ResourceLocation rl = new ResourceLocation(resource);
      TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
      textureManager.func_110581_b(rl);
      ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, url, (ResourceLocation)null, iib);
      textureManager.func_110579_a(rl, textureCape);
   }

   private BufferedImage parseCape(BufferedImage img) {
      int imageWidth = 64;
      int imageHeight = 32;
      int srcWidth = img.getWidth();

      for(int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
         imageWidth *= 2;
      }

      BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
      Graphics g = imgNew.getGraphics();
      g.drawImage(img, 0, 0, (ImageObserver)null);
      g.dispose();
      return imgNew;
   }

   private static String formatUUID(String uuid) {
      return uuid.replaceAll("-", "");
   }

   private class CapeUser {
      public String uuid;
      public String url;
   }
}
