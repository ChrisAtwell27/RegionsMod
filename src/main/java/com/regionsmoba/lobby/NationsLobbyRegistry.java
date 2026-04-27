package com.regionsmoba.lobby;

import com.regionsmoba.config.BlockPosData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Tracks every recognised [Nations] sign and which sign each player is queued at.
 * A player can be queued at at most one sign — joining a different sign moves them.
 *
 * In-memory only. On match start, all sign states are cleared (joiners are now
 * in the match). On match end, the registry stays empty until signs are clicked
 * again.
 */
public final class NationsLobbyRegistry {

    private static final NationsLobbyRegistry INSTANCE = new NationsLobbyRegistry();

    public static NationsLobbyRegistry get() {
        return INSTANCE;
    }

    private final Map<BlockPosData, NationsSignState> signs = new HashMap<>();
    private final Map<UUID, BlockPosData> playerToSign = new HashMap<>();

    private NationsLobbyRegistry() {}

    /** Get or create the state for a sign at {@code signPos} with the given threshold. */
    public NationsSignState getOrCreate(BlockPosData signPos, int minPlayers) {
        NationsSignState state = signs.get(signPos);
        if (state == null) {
            state = new NationsSignState(signPos, minPlayers);
            signs.put(signPos, state);
        } else {
            state.minPlayers = minPlayers;
        }
        return state;
    }

    public NationsSignState get(BlockPosData signPos) {
        return signs.get(signPos);
    }

    public BlockPosData currentSignFor(UUID player) {
        return playerToSign.get(player);
    }

    /**
     * Adds the player to the given sign's queue, removing them from any other sign
     * they were queued at. Returns the sign state for follow-up checks.
     */
    public NationsSignState join(UUID player, BlockPosData signPos, int minPlayers) {
        BlockPosData prev = playerToSign.get(player);
        if (prev != null && !prev.equals(signPos)) {
            NationsSignState prevState = signs.get(prev);
            if (prevState != null) prevState.joiners.remove(player);
        }
        NationsSignState state = getOrCreate(signPos, minPlayers);
        state.joiners.add(player);
        playerToSign.put(player, signPos);
        return state;
    }

    /** Removes the player from their queued sign, if any. Returns the sign they left, or null. */
    public NationsSignState leave(UUID player) {
        BlockPosData signPos = playerToSign.remove(player);
        if (signPos == null) return null;
        NationsSignState state = signs.get(signPos);
        if (state != null) state.joiners.remove(player);
        return state;
    }

    /** Clears every sign's joiner list and the player→sign map. Called at match start. */
    public void clearAll() {
        for (NationsSignState s : signs.values()) s.joiners.clear();
        playerToSign.clear();
    }

    /** Drop every sign and player. Used by /regions reset all. */
    public void dropAll() {
        signs.clear();
        playerToSign.clear();
    }

    public Iterable<NationsSignState> allSigns() {
        return signs.values();
    }
}
