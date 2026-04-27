package com.regionsmoba.team;

/**
 * Per-player runtime state inside an active match. Lives are 3 by default
 * (per docs/src-md/gameplay/timeline.md). Team and class are null until the
 * player makes their picks in the lobby.
 */
public final class MatchPlayerState {

    public static final int DEFAULT_LIVES = 3;

    public BiomeTeam team;
    public BiomeClass biomeClass;
    public int lives = DEFAULT_LIVES;
    public boolean spectator;

    public boolean hasTeam() {
        return team != null;
    }

    public boolean hasClass() {
        return biomeClass != null;
    }
}
