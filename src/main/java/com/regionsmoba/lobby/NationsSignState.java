package com.regionsmoba.lobby;

import com.regionsmoba.config.BlockPosData;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Per-sign join state. The sign's own row 2 sets minPlayers; we re-read it at each
 * click so the operator can edit the threshold and have it take effect immediately.
 *
 * Insertion-ordered joiner set so the first-to-join is teleported first (cosmetic).
 */
public final class NationsSignState {

    public final BlockPosData signPos;
    public int minPlayers;
    public final Set<UUID> joiners = new LinkedHashSet<>();

    public NationsSignState(BlockPosData signPos, int minPlayers) {
        this.signPos = signPos;
        this.minPlayers = minPlayers;
    }

    public boolean thresholdReached() {
        return joiners.size() >= minPlayers;
    }
}
