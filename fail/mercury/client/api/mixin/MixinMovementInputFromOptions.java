package fail.mercury.client.api.mixin;

import fail.mercury.client.Mercury;
import fail.mercury.client.client.modules.movement.Scaffold;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({MovementInputFromOptions.class})
public class MixinMovementInputFromOptions extends MovementInput {
   @Shadow
   @Final
   public GameSettings field_78903_e;

   @Overwrite
   public void func_78898_a() {
      this.field_78902_a = 0.0F;
      this.field_192832_b = 0.0F;
      if (this.field_78903_e.field_74351_w.func_151470_d()) {
         ++this.field_192832_b;
         this.field_187255_c = true;
      } else {
         this.field_187255_c = false;
      }

      if (this.field_78903_e.field_74368_y.func_151470_d()) {
         --this.field_192832_b;
         this.field_187256_d = true;
      } else {
         this.field_187256_d = false;
      }

      if (this.field_78903_e.field_74370_x.func_151470_d()) {
         ++this.field_78902_a;
         this.field_187257_e = true;
      } else {
         this.field_187257_e = false;
      }

      if (this.field_78903_e.field_74366_z.func_151470_d()) {
         --this.field_78902_a;
         this.field_187258_f = true;
      } else {
         this.field_187258_f = false;
      }

      this.field_78901_c = this.field_78903_e.field_74314_A.func_151470_d();
      if (Mercury.INSTANCE.getModuleManager().find(Scaffold.class).isEnabled() && Scaffold.down) {
         this.field_78899_d = false;
      } else {
         this.field_78899_d = this.field_78903_e.field_74311_E.func_151470_d();
      }

      if (this.field_78899_d) {
         this.field_78902_a = (float)((double)this.field_78902_a * 0.3D);
         this.field_192832_b = (float)((double)this.field_192832_b * 0.3D);
      }

   }
}
