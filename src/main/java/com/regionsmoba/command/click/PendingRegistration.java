package com.regionsmoba.command.click;

import com.regionsmoba.team.BiomeTeam;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * One operator's outstanding click-driven registration. Created by a target/deposit
 * command, consumed by the next matching click (one-shot kinds) or by /regions done
 * (session kinds).
 */
public final class PendingRegistration {

    public enum Kind {
        // Single-block targets — consumed by one block click.
        SET_CONDUIT,
        SET_FURNACE,
        SET_COMPOSTER,
        // Entity target — consumed by one entity click.
        SET_TRADER,
        // Removal — consumed by one block click matching a registered deposit.
        REMOVE_ORE_DEPOSIT,
        REMOVE_BLOCK_DEPOSIT,
        REMOVE_MOB_DEPOSIT,
        // Session — persists across many clicks until /regions done.
        ADD_ORE_DEPOSIT_SESSION,
        ADD_BLOCK_DEPOSIT_SESSION,
        // One-shot deposit add (mob deposits aren't a session — one click registers one spawner).
        ADD_MOB_DEPOSIT
    }

    public final Kind kind;
    /** SET_TRADER. */
    public BiomeTeam team;
    /** ADD_*_DEPOSIT_SESSION + ADD_MOB_DEPOSIT regen/interval, in seconds. */
    public int regenSeconds;
    /** ADD_MOB_DEPOSIT. */
    public String mobId;
    /**
     * Locked block/ore id for a deposit session — set on the first click. Subsequent
     * clicks must match this id. Null until first click.
     */
    public String lockedItemId;
    /** Number of items registered in the current session, for status messages. */
    public int registered;
    /**
     * Positions marked with the visual emerald block during a deposit session, so we
     * can revert each one to its actual world state on /regions done.
     */
    public final List<BlockPos> markedPositions = new ArrayList<>();

    public PendingRegistration(Kind kind) {
        this.kind = kind;
    }

    public boolean isSession() {
        return kind == Kind.ADD_ORE_DEPOSIT_SESSION || kind == Kind.ADD_BLOCK_DEPOSIT_SESSION;
    }
}
