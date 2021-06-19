package fail.mercury.client.api.mixin;

import fail.mercury.client.Mercury;
import fail.mercury.client.client.events.CollisionBoxEvent;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Block.class})
public abstract class MixinBlock {
   private CollisionBoxEvent bbEvent;

   @Inject(
      method = {"addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void in(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState, CallbackInfo ci) {
      Block block = (Block)this;
      this.bbEvent = new CollisionBoxEvent(block, pos, block.func_180646_a(state, world, pos), collidingBoxes, entity);
      Mercury.INSTANCE.getEventManager().fireEvent(this.bbEvent);
      if (this.bbEvent.getBoundingBox() == null) {
         ci.cancel();
      }

   }

   @Redirect(
      method = {"addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/state/IBlockState;getCollisionBoundingBox(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/AxisAlignedBB;"
)
   )
   private AxisAlignedBB getBB(IBlockState state, IBlockAccess world, BlockPos pos) {
      AxisAlignedBB bb = this.bbEvent == null ? state.func_185890_d(world, pos) : this.bbEvent.getBoundingBox();
      return bb;
   }
}
