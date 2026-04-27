package com.regionsmoba.mixin;

import com.regionsmoba.match.MatchManager;
import com.regionsmoba.repair.WorldSnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Records the original BlockState (and BlockEntity NBT) at each position the
 * first time it mutates during an active match, in any match dimension.
 *
 * Targets Level (not ServerLevel) because Level holds the canonical setBlock
 * implementation; the 3-arg overload delegates to the 4-arg one we hook here.
 */
@Mixin(Level.class)
public class LevelSetBlockMixin {

    @Inject(
            method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At("HEAD"))
    private void regionsmoba$snapshotChange(
            BlockPos pos, BlockState newState, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        Level self = (Level) (Object) this;
        if (!(self instanceof ServerLevel level)) return;

        WorldSnapshot snapshot = MatchManager.get().snapshot(level);
        if (snapshot == null || snapshot.isRestoring()) return;

        BlockState oldState = level.getBlockState(pos);
        if (oldState == newState) return;

        snapshot.recordIfFirst(level, pos.immutable(), oldState);
    }
}
