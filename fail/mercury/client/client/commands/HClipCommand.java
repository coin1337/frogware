package fail.mercury.client.client.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import fail.mercury.client.api.command.Command;
import fail.mercury.client.api.command.annotation.CommandManifest;
import fail.mercury.client.api.util.ChatUtil;
import fail.mercury.client.api.util.MotionUtil;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.math.BlockPos;

@CommandManifest(
   label = "hclip",
   description = "Teleports you forward",
   aliases = {"h"}
)
public class HClipCommand extends Command {
   public void onRun(String[] args) {
      if (args.length <= 1) {
         ChatUtil.print("Not enough args.");
      } else {
         boolean bypass = args.length > 2 && args[2].equalsIgnoreCase("bypass");

         try {
            double blocks = Double.parseDouble(args[1]);
            boolean ground = this.mc.field_71439_g.field_70122_E;
            double[] dir = MotionUtil.forward(blocks);
            Block block = this.mc.field_71441_e.func_180495_p(new BlockPos(this.mc.field_71439_g.field_70165_t + dir[0], this.mc.field_71439_g.field_70163_u - 0.1D, this.mc.field_71439_g.field_70161_v + dir[1])).func_177230_c();
            if (bypass) {
               for(double x = 0.0625D; x < blocks; x += 0.262D) {
                  double[] dir2 = MotionUtil.forward(x);
                  this.mc.field_71439_g.field_71174_a.func_147297_a(new Position(this.mc.field_71439_g.field_70165_t + dir2[0], this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + dir2[1], ground));
                  this.mc.field_71439_g.func_70634_a(this.mc.field_71439_g.field_70165_t + dir2[0], this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + dir2[1]);
               }

               this.mc.field_71439_g.field_71174_a.func_147297_a(new Position(this.mc.field_71439_g.field_70165_t + this.mc.field_71439_g.field_70159_w, 0.0D, this.mc.field_71439_g.field_70161_v + this.mc.field_71439_g.field_70179_y, ground));
               ChatUtil.print("Zoomed " + blocks + " blocks (Bypass).");
            } else {
               this.mc.field_71439_g.field_71174_a.func_147297_a(new Position(this.mc.field_71439_g.field_70165_t + dir[0], this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + dir[1], ground));
               this.mc.field_71439_g.func_70634_a(this.mc.field_71439_g.field_70165_t + dir[0], this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + dir[1]);
               ChatUtil.print("Zoomed " + blocks + " blocks.");
            }
         } catch (NumberFormatException var11) {
            ChatUtil.print("Invalid number " + ChatFormatting.GRAY + args[1]);
         }

      }
   }
}
