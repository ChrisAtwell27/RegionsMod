package com.regionsmoba.protection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Per-operator opt-out from block protection. Toggled by /regions debug buildmode.
 * In-memory only — clears on server stop.
 */
public final class BuildMode {

    private static final Set<UUID> enabled = new HashSet<>();

    private BuildMode() {}

    public static boolean isEnabled(UUID player) {
        return enabled.contains(player);
    }

    public static boolean toggle(UUID player) {
        if (enabled.add(player)) return true;
        enabled.remove(player);
        return false;
    }

    public static void set(UUID player, boolean on) {
        if (on) enabled.add(player);
        else enabled.remove(player);
    }

    public static void clearAll() {
        enabled.clear();
    }
}
