package com.regionsmoba.command.click;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Per-docs: "Registered blocks display as emerald blocks for the duration of the
 * session as a visual marker." We send a fake block-update packet to the operator
 * only — server world state is never modified, so map-snapshot tracking is not
 * polluted. On /regions done we send the actual world state to revert each marker.
 *
 * Marker visibility caveat: if the chunk is reloaded on the client (the operator
 * walks far away then comes back, or relogs), the client redraws from server
 * state and the emerald disappears. The deposit registration itself is unaffected;
 * this is a known visual-only limitation.
 */
public final class VisualMarkers {

    private static final BlockState MARKER_STATE = Blocks.EMERALD_BLOCK.defaultBlockState();

    private VisualMarkers() {}

    public static void mark(ServerPlayer player, BlockPos pos) {
        player.connection.send(new ClientboundBlockUpdatePacket(pos.immutable(), MARKER_STATE));
    }

    public static void revert(ServerPlayer player, ServerLevel level, BlockPos pos) {
        BlockState actual = level.getBlockState(pos);
        player.connection.send(new ClientboundBlockUpdatePacket(pos.immutable(), actual));
    }

    public static void revertAll(ServerPlayer player, ServerLevel level, Iterable<BlockPos> positions) {
        for (BlockPos pos : positions) {
            revert(player, level, pos);
        }
    }
}
