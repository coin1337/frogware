package fail.mercury.client.api.audio;

import fail.mercury.client.api.util.Util;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl.Type;
import net.minecraft.util.ResourceLocation;

public class AudioPlayer implements Util {
   static Clip clip;
   ResourceLocation location;

   public AudioPlayer(ResourceLocation location) {
      if (clip == null) {
         try {
            clip = AudioSystem.getClip();
            this.location = location;
         } catch (LineUnavailableException var3) {
            var3.printStackTrace();
         }
      }

   }

   public void play() {
      if (clip != null && this.location != null) {
         try {
            InputStream in = mc.func_110442_L().func_110536_a(this.location).func_110527_b();
            System.out.println(mc.func_110442_L().func_110536_a(this.location).func_110527_b());
            clip.open(AudioSystem.getAudioInputStream(in));
            clip.start();
         } catch (UnsupportedAudioFileException | LineUnavailableException | IOException var2) {
            var2.printStackTrace();
         }
      }

   }

   public void stop() {
      if (clip != null && this.location != null && clip.isActive()) {
         clip.stop();
      }

   }

   public void close() {
      if (clip != null && this.location != null && clip.isOpen()) {
         clip.close();
      }

   }

   public float getVolume() {
      FloatControl gainControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
      return (float)Math.pow(10.0D, (double)(gainControl.getValue() / 20.0F));
   }

   public void setVolume(float volume) {
      if (!(volume < 0.0F) && !(volume > 1.0F)) {
         FloatControl gainControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
         gainControl.setValue(20.0F * (float)Math.log10((double)volume));
      } else {
         throw new IllegalArgumentException("Volume not valid: " + volume);
      }
   }
}
