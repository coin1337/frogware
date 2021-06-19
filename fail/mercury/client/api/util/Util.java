package fail.mercury.client.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public interface Util {
   Minecraft mc = Minecraft.func_71410_x();

   static void sendPacket(Packet p) {
      mc.field_71439_g.field_71174_a.func_147297_a(p);
   }
}
