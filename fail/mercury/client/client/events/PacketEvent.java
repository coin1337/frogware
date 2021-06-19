package fail.mercury.client.client.events;

import net.b0at.api.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
   private Packet packet;
   private PacketEvent.Type type;

   public PacketEvent(PacketEvent.Type type, Packet packet) {
      this.type = type;
      this.packet = packet;
   }

   public PacketEvent.Type getType() {
      return this.type;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public static enum Type {
      INCOMING,
      OUTGOING;
   }
}
