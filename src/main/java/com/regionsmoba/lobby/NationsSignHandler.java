package com.regionsmoba.lobby;

import com.regionsmoba.RegionsMOBA;
import com.regionsmoba.config.BlockPosData;
import com.regionsmoba.match.MatchManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

import java.util.Locale;

/**
 * Handles right-clicks on [Nations] signs. Validates sign content per the docs:
 *   Row 1 must be "[Nations]" (case-insensitive)
 *   Row 2 must be a positive integer and a multiple of 4
 *
 * Valid signs become live join boards: row 3 displays "X / Y joined" and updates
 * on every join/leave. When the threshold is met, the registered joiners start a
 * match and teleport to the lobby spawn.
 *
 * Players queued at one sign are moved when they click another. Right-clicking
 * the sign you're queued at leaves the queue.
 */
public final class NationsSignHandler {

    private static final String NATIONS_TAG = "[nations]";

    private NationsSignHandler() {}

    /** Returns true if the click was handled (operator should not see vanilla edit UI). */
    public static boolean handleClick(ServerPlayer player, ServerLevel level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SignBlockEntity sign)) return false;

        boolean front = sign.isFacingFrontText(player);
        SignText text = sign.getText(front);
        String row1 = lineString(text, 0);
        if (!isNationsTag(row1)) return false;

        // From here we know it's intended to be a [Nations] sign — handle it
        // (and intercept the click) even if row 2 is invalid, so we can tell the
        // player why it's not working.
        String row2 = lineString(text, 1).trim();
        Integer min = parseMinPlayers(row2);
        if (min == null) {
            tell(player, "Sign rejected: row 2 must be a positive integer and a multiple of 4 (got '" + row2 + "')",
                    ChatFormatting.RED);
            return true;
        }

        if (MatchManager.get().isActive()) {
            tell(player, "A match is already in progress. Wait for it to end.", ChatFormatting.YELLOW);
            return true;
        }

        BlockPosData signPos = BlockPosData.of(level, pos);
        NationsLobbyRegistry registry = NationsLobbyRegistry.get();
        BlockPosData currentlyAt = registry.currentSignFor(player.getUUID());
        NationsSignState state;

        if (signPos.equals(currentlyAt)) {
            // Right-clicked the sign they're already queued at — leave.
            state = registry.leave(player.getUUID());
            tell(player, "Left the join queue (" + (state != null ? state.joiners.size() : 0) + " / " + min + ")",
                    ChatFormatting.GRAY);
        } else {
            state = registry.join(player.getUUID(), signPos, min);
            tell(player, "Joined the queue (" + state.joiners.size() + " / " + min + ")",
                    ChatFormatting.GREEN);
        }

        if (state != null) {
            updateDisplay(level, sign, front, state);
        }

        if (state != null && state.thresholdReached()) {
            // Trigger match start — slice 4+ wires team/class flow.
            MatchManager.get().startWithJoiners(level.getServer(), state.joiners);
            // Lobby teleport is handled by MatchManager.startWithJoiners.
        }
        return true;
    }

    private static String lineString(SignText text, int line) {
        Component[] msgs = text.getMessages(false);
        if (line < 0 || line >= msgs.length) return "";
        return msgs[line].getString();
    }

    private static boolean isNationsTag(String s) {
        return s != null && s.trim().toLowerCase(Locale.ROOT).equals(NATIONS_TAG);
    }

    private static Integer parseMinPlayers(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            int v = Integer.parseInt(s);
            if (v <= 0 || v % 4 != 0) return null;
            return v;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void updateDisplay(ServerLevel level, SignBlockEntity sign, boolean front, NationsSignState state) {
        SignText text = sign.getText(front);
        Component count = Component.literal(state.joiners.size() + " / " + state.minPlayers + " joined")
                .withStyle(ChatFormatting.AQUA);
        SignText updated = text.setMessage(2, count);
        sign.setText(updated, front);
        // setText flags chunk dirty; the BE update packet is sent to nearby clients.
        sign.setChanged();
        level.sendBlockUpdated(sign.getBlockPos(), sign.getBlockState(), sign.getBlockState(),
                net.minecraft.world.level.block.Block.UPDATE_CLIENTS);
        RegionsMOBA.LOGGER.debug("Sign at {} now shows {}/{}",
                sign.getBlockPos(), state.joiners.size(), state.minPlayers);
    }

    private static void tell(ServerPlayer p, String msg, ChatFormatting color) {
        p.sendSystemMessage(Component.literal(msg).withStyle(color));
    }
}
