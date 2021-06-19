package fail.mercury.client.client.modules.visual;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.RenderUtil;
import fail.mercury.client.client.events.Render3DEvent;
import fail.mercury.client.client.events.UpdateEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.kix.lotus.property.annotations.Clamp;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@ModuleManifest(
   label = "HoleESP",
   category = Category.VISUAL,
   fakelabel = "Hole ESP",
   aliases = {"holes"},
   description = "Renders holes that would protect you from crystal explosions."
)
public class HoleESP extends Module {
   public final List<BlockPos> positions = new ArrayList();
   @Property("Red")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int red = 200;
   @Property("Green")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int green = 117;
   @Property("Blue")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int blue = 255;
   @Property("Alpha")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int alpha = 119;
   @Property("Distance")
   @Clamp(
      minimum = "0",
      maximum = "255"
   )
   public int distance = 5;

   public void onToggle() {
      this.positions.clear();
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      Vec3i pos = MathUtil.interpolateEntityInt(mc.field_71439_g, mc.func_184121_ak());

      for(int x = pos.func_177958_n() - this.distance; x < pos.func_177958_n() + this.distance; ++x) {
         for(int z = pos.func_177952_p() - this.distance; z < pos.func_177952_p() + this.distance; ++z) {
            for(int y = pos.func_177956_o(); y > pos.func_177956_o() - 4; --y) {
               BlockPos blockPos = new BlockPos(x, y, z);
               if (this.isHole(blockPos)) {
                  this.positions.add(blockPos);
               }
            }
         }
      }

   }

   @EventHandler
   public void onRender(Render3DEvent event) {
      Color clr = new Color(this.red, this.green, this.blue, this.alpha);
      Iterator var3 = this.positions.iterator();

      while(var3.hasNext()) {
         BlockPos position = (BlockPos)var3.next();
         AxisAlignedBB bb = new AxisAlignedBB((double)position.func_177958_n() - mc.func_175598_ae().field_78730_l, (double)position.func_177956_o() - mc.func_175598_ae().field_78731_m, (double)position.func_177952_p() - mc.func_175598_ae().field_78728_n, (double)(position.func_177958_n() + 1) - mc.func_175598_ae().field_78730_l, (double)(position.func_177956_o() + 1) - mc.func_175598_ae().field_78731_m, (double)(position.func_177952_p() + 1) - mc.func_175598_ae().field_78728_n);
         RenderUtil.drawESP(bb, (float)clr.getRed(), (float)clr.getGreen(), (float)clr.getBlue(), (float)clr.getAlpha());
      }

   }

   public boolean isHole(BlockPos pos) {
      BlockPos[] touchingBlocks = new BlockPos[]{pos.func_177978_c(), pos.func_177968_d(), pos.func_177974_f(), pos.func_177976_e()};
      int validHorizontalBlocks = 0;
      BlockPos[] var4 = touchingBlocks;
      int var5 = touchingBlocks.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockPos touching = var4[var6];
         IBlockState touchingState = mc.field_71441_e.func_180495_p(touching);
         if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
            ++validHorizontalBlocks;
         }
      }

      if (validHorizontalBlocks < 4) {
         return false;
      } else if (mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else if (this.positions.contains(pos)) {
         return false;
      } else {
         return true;
      }
   }
}
