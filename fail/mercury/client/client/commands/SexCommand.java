package fail.mercury.client.client.commands;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.api.util.TimerUtil;
import fail.mercury.client.client.events.UpdateEvent;
import net.b0at.api.event.EventHandler;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.Session;

@CommandManifest(
   label = "Sex",
   description = "owo"
)
public class SexCommand extends Command {
   String bottom;
   String top;
   public static EntityOtherPlayerMP bottomEntity;
   public static EntityOtherPlayerMP topEntity;
   public TimerUtil shiftTimer = new TimerUtil();
   public TimerUtil endTimer = new TimerUtil();

   public void onRun(String[] args) {
      if (args.length <= 1) {
         ChatUtil.print("Not enough args.");
      } else {
         Mercury.INSTANCE.getEventManager().registerListener(this);
         this.bottom = args[1];
         this.top = args[2];
         double[] dir = MotionUtil.forward(2.0D);
         String bottomID = Mercury.INSTANCE.getProfileManager().getUUID(this.bottom).toString();
         String topID = Mercury.INSTANCE.getProfileManager().getUUID(this.top).toString();
         Session bottomSession = new Session(this.bottom, bottomID, "69691", "legacy");
         Session topSession = new Session(this.top, topID, "69692", "legacy");
         bottomEntity = new EntityOtherPlayerMP(this.mc.field_71441_e, bottomSession.func_148256_e());
         topEntity = new EntityOtherPlayerMP(this.mc.field_71441_e, topSession.func_148256_e());
         bottomEntity.field_70165_t = this.mc.field_71439_g.field_70165_t + dir[0];
         bottomEntity.field_70161_v = this.mc.field_71439_g.field_70161_v + dir[1] + 1.0D;
         bottomEntity.field_70163_u = this.mc.field_71439_g.field_70163_u;
         bottomEntity.field_70177_z = this.mc.field_71439_g.field_70177_z - 90.0F;
         bottomEntity.field_70125_A = 90.0F;
         topEntity.field_70165_t = this.mc.field_71439_g.field_70165_t + dir[0];
         topEntity.field_70161_v = this.mc.field_71439_g.field_70161_v + dir[1];
         topEntity.field_70163_u = this.mc.field_71439_g.field_70163_u;
         topEntity.field_70177_z = this.mc.field_71439_g.field_70177_z - 90.0F;
         topEntity.field_70125_A = 180.0F;
         this.mc.field_71441_e.func_73027_a(69691, bottomEntity);
         this.mc.field_71441_e.func_73027_a(69692, topEntity);
         ChatUtil.print(String.format("%s and %s are now doing da sex", this.bottom, this.top));
      }
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (this.shiftTimer.hasReached(1000L)) {
         bottomEntity.func_70095_a(true);
      }

      if (this.shiftTimer.hasReached(2000L)) {
         this.shiftTimer.reset();
         bottomEntity.func_70095_a(false);
      }

      if (this.endTimer.hasReached(10000L)) {
         this.mc.field_71441_e.func_72900_e(bottomEntity);
         this.mc.field_71441_e.func_72900_e(topEntity);
         Mercury.INSTANCE.getEventManager().deregisterListener(this);
      }

   }
}
