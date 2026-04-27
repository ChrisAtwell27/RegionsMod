package com.regionsmoba.match;

public enum MatchState {
    /** No active sign or match. Default state on server start. */
    DORMANT,
    /** A [Nations] sign exists and is collecting joiners. */
    LOBBY,
    /** Match is running: timeline ticks, classes equipped, lifelines active. */
    ACTIVE,
    /** Match just ended; map repair in progress. Returns to DORMANT once done. */
    ENDED
}
