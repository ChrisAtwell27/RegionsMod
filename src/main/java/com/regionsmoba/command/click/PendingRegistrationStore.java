package com.regionsmoba.command.click;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Per-operator pending registration store. Single-threaded (server thread only).
 * Cleared on server stop.
 */
public final class PendingRegistrationStore {

    private static final Map<UUID, PendingRegistration> map = new HashMap<>();

    private PendingRegistrationStore() {}

    public static void set(UUID player, PendingRegistration reg) {
        map.put(player, reg);
    }

    public static PendingRegistration get(UUID player) {
        return map.get(player);
    }

    public static PendingRegistration clear(UUID player) {
        return map.remove(player);
    }

    public static boolean has(UUID player) {
        return map.containsKey(player);
    }

    public static void clearAll() {
        map.clear();
    }
}
