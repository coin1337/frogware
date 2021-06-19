package fail.mercury.client.api.tickrate;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.manager.IManager;
import fail.mercury.client.client.events.PacketEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public final class TickRateManager implements IManager {
   private long prevTime;
   private float[] ticks = new float[20];
   private int currentTick;

   public void load() {
      this.prevTime = -1L;
      int i = 0;

      for(int len = this.ticks.length; i < len; ++i) {
         this.ticks[i] = 0.0F;
      }

      Mercury.INSTANCE.getEventManager().registerListener(this);
   }

   public float getTickRate() {
      int tickCount = 0;
      float tickRate = 0.0F;

      for(int i = 0; i < this.ticks.length; ++i) {
         float tick = this.ticks[i];
         if (tick > 0.0F) {
            tickRate += tick;
            ++tickCount;
         }
      }

      return MathHelper.func_76131_a(tickRate / (float)tickCount, 0.0F, 20.0F);
   }

   public void unload() {
      Mercury.INSTANCE.getEventManager().deregisterListener(this);
   }

   @EventHandler
   public void receivePacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.INCOMING) && event.getPacket() instanceof SPacketTimeUpdate) {
         if (this.prevTime != -1L) {
            this.ticks[this.currentTick % this.ticks.length] = MathHelper.func_76131_a(20.0F / ((float)(System.currentTimeMillis() - this.prevTime) / 1000.0F), 0.0F, 20.0F);
            ++this.currentTick;
         }

         this.prevTime = System.currentTimeMillis();
      }

   }
}
