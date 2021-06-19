package fail.mercury.client.client.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.client.events.PacketEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.util.HashMap;
import java.util.Objects;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;

@ModuleManifest(
   label = "TotemDetector",
   fakelabel = "Totem Detector",
   category = Category.MISC
)
public class TotemDetector extends Module {
   private HashMap<String, Integer> popList = new HashMap();
   private Entity entity;
   @Property("Range")
   @Clamp(
      minimum = "1",
      maximum = "1000"
   )
   public double range = 50.0D;

   @EventHandler
   public void onPacket(PacketEvent event) {
      if (event.getType().equals(PacketEvent.Type.INCOMING) && event.getPacket() instanceof SPacketEntityStatus) {
         SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
         if (packet.func_149160_c() == 35) {
            this.entity = packet.func_149161_a(mc.field_71441_e);
            if (this.entity != mc.field_71439_g && (double)mc.field_71439_g.func_70032_d(this.entity) <= this.range) {
               if (!this.popList.containsKey(this.entity.func_70005_c_())) {
                  ChatUtil.print(ChatFormatting.RED + this.entity.func_70005_c_() + ChatFormatting.WHITE + " has popped 1 totem!");
                  this.popList.put(this.entity.func_70005_c_(), 1);
               } else {
                  int popCounter = (Integer)this.popList.get(this.entity.func_70005_c_());
                  ++popCounter;
                  this.popList.put(this.entity.func_70005_c_(), popCounter);
                  ChatUtil.print(ChatFormatting.RED + this.entity.func_70005_c_() + ChatFormatting.WHITE + " has popped " + popCounter + " totems!");
               }
            }
         }
      }

   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (!Objects.isNull(mc.field_71441_e) && !Objects.isNull(mc.field_71439_g) && !Objects.isNull(mc.field_71441_e.field_73010_i)) {
         mc.field_71441_e.field_73010_i.forEach((e) -> {
            if (!Objects.isNull(this.entity)) {
               if (e.func_70005_c_().equals(this.entity.func_70005_c_()) && this.entity.field_70128_L) {
                  this.popList.remove(this.entity.func_70005_c_());
               }

            }
         });
      }
   }
}
