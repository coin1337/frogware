package fail.mercury.client.client.modules.combat;

import fail.mercury.client.api.module.Module;
import fail.mercury.client.api.module.annotations.ModuleManifest;
import fail.mercury.client.api.module.category.Category;
import fail.mercury.client.api.util.AngleUtil;
import fail.mercury.client.api.util.InventoryUtil;
import fail.mercury.client.api.util.MathUtil;
import fail.mercury.client.api.util.MotionUtil;
import fail.mercury.client.client.events.UpdateEvent;
import me.kix.lotus.property.annotations.Property;
import net.b0at.api.event.EventHandler;
import net.b0at.api.event.types.EventTiming;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@ModuleManifest(
   label = "Surround",
   category = Category.COMBAT,
   aliases = {"NoCrystal", "FeetPlace"},
   description = "Automatically surrounds you with bedrock/obsidian"
)
public class Surround extends Module {
   @Property("Toggle")
   public boolean toggle = false;
   @Property("Swing")
   public boolean swing = false;
   @Property("Rotate")
   public boolean rotate = false;
   @Property("Replenish")
   public boolean replenish = false;
   @Property("Center")
   public boolean center = false;
   public boolean centered;
   public int counter;

   public void onDisable() {
      if (this.centered && this.center) {
         this.centered = false;
      }

      this.counter = 0;
   }

   @EventHandler
   public void onUpdate(UpdateEvent event) {
      if (event.getTiming().equals(EventTiming.PRE)) {
         if (InventoryUtil.getItemCount(mc.field_71439_g.field_71069_bz, Item.func_150898_a(Blocks.field_150343_Z)) == 0 || !mc.field_71439_g.field_70122_E) {
            return;
         }

         Vec3d vec = MathUtil.interpolateEntity(mc.field_71439_g, mc.func_184121_ak());
         BlockPos playerPos = new BlockPos(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
         if (this.center) {
            if (!this.centered) {
               mc.field_71439_g.func_70107_b((double)playerPos.func_177958_n() + 0.5D, (double)playerPos.func_177956_o(), (double)playerPos.func_177952_p() + 0.5D);
               this.centered = true;
            }

            if (MotionUtil.isMoving(mc.field_71439_g)) {
               this.centered = false;
            }
         }

         if (this.center && mc.field_71439_g.field_70165_t != (double)playerPos.func_177958_n() + 0.5D && mc.field_71439_g.field_70161_v != (double)playerPos.func_177952_p() + 0.5D) {
            return;
         }

         if (InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, Item.func_150898_a(Blocks.field_150343_Z)) < 36 && this.replenish) {
            InventoryUtil.swap(InventoryUtil.getItemSlot(mc.field_71439_g.field_71069_bz, Item.func_150898_a(Blocks.field_150343_Z)), 44);
         }

         int slot = InventoryUtil.getItemSlotInHotbar(Item.func_150898_a(Blocks.field_150343_Z));
         int lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         mc.field_71439_g.field_71071_by.field_70461_c = slot;
         mc.field_71442_b.func_78765_e();
         BlockPos[] positions = new BlockPos[]{playerPos.func_177978_c(), playerPos.func_177968_d(), playerPos.func_177974_f(), playerPos.func_177976_e()};
         if (this.canPlace(positions[0])) {
            this.place(playerPos, EnumFacing.NORTH, event);
         }

         if (this.canPlace(positions[1])) {
            this.place(playerPos, EnumFacing.SOUTH, event);
         }

         if (this.canPlace(positions[2])) {
            this.place(playerPos, EnumFacing.EAST, event);
         }

         if (this.canPlace(positions[3])) {
            this.place(playerPos, EnumFacing.WEST, event);
         }

         if (this.canPlace(positions[0])) {
            this.place(positions[0], EnumFacing.UP, event);
         }

         if (this.canPlace(positions[1])) {
            this.place(positions[1], EnumFacing.UP, event);
         }

         if (this.canPlace(positions[2])) {
            this.place(positions[2], EnumFacing.UP, event);
         }

         if (this.canPlace(positions[3])) {
            this.place(positions[3], EnumFacing.UP, event);
         }

         mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
         mc.field_71442_b.func_78765_e();
         if (this.toggle) {
            this.toggle();
         }
      }

   }

   public boolean canPlace(BlockPos pos) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      return (block instanceof BlockAir || block instanceof BlockLiquid) && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos)).isEmpty();
   }

   public void place(BlockPos pos, EnumFacing direction, UpdateEvent event) {
      float[] rotations = AngleUtil.getRotationFromPosition((double)pos.func_177958_n() + 0.5D, (double)pos.func_177952_p() + 0.5D, (double)((float)pos.func_177982_a(0, 0, 0).func_177956_o() - mc.field_71439_g.func_70047_e()));
      if (this.rotate) {
         event.getRotation().setYaw(rotations[0]);
         event.getRotation().setPitch(rotations[1]);
      }

      if (mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos.func_177982_a(0, -1, 0), direction, new Vec3d(Math.random(), Math.random(), Math.random()), EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
         if (this.swing) {
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
         } else {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
         }
      }

   }
}
