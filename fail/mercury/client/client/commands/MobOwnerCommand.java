package fail.mercury.client.client.commands;

import fail.mercury.client.Mercury;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.RayTraceResult.Type;

@CommandManifest(
   label = "MobOwner",
   aliases = {"owner", "mowner"},
   description = "Shows the owner of the targeted entity."
)
public class MobOwnerCommand extends Command {
   public void onRun(String[] args) {
      if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72313_a == Type.ENTITY) {
         Entity entity = this.mc.field_71476_x.field_72308_g;
         if (entity instanceof EntityTameable) {
            EntityTameable tamable = (EntityTameable)entity;
            if (tamable.func_70909_n()) {
               String name = Mercury.INSTANCE.getProfileManager().getName(tamable.func_184753_b());
               ChatUtil.print(String.format("Entity %s is owned by %s.", tamable.func_70005_c_(), name));
            } else {
               ChatUtil.print(String.format("Entity %s is not tamable.", entity.func_70005_c_()));
            }
         }
      }

   }
}
