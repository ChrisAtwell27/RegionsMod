package com.regionsmoba.repair;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Tracks entities the mod spawns during a match — mass-event mobs, mob-deposit
 * spawns, trial-wave mobs. On match end, every tracked entity is despawned along
 * with every dropped item in the match dimensions, per the timeline doc:
 * "Mod-spawned entities ... and dropped items in the world despawn at match end.
 * Vanilla wandering mobs are left alone."
 *
 * In-memory only — a server crash mid-match drops the tracking, but the match
 * also drops, so that's coherent.
 */
public final class ModEntities {

    private static final Set<UUID> tracked = new HashSet<>();

    private ModEntities() {}

    /** Tag an entity as mod-spawned — call this every time we spawn one. */
    public static void track(Entity entity) {
        if (entity != null) tracked.add(entity.getUUID());
    }

    public static boolean isTracked(Entity entity) {
        return entity != null && tracked.contains(entity.getUUID());
    }

    public static int trackedCount() {
        return tracked.size();
    }

    public static void clearAll() {
        tracked.clear();
    }

    /**
     * Despawn every tracked entity and every dropped item in the level. Returns the
     * total killed for telemetry.
     */
    public static int despawnInLevel(ServerLevel level) {
        int killed = 0;
        for (Entity e : level.getAllEntities()) {
            if (e instanceof ItemEntity) {
                e.discard();
                killed++;
                continue;
            }
            if (tracked.contains(e.getUUID())) {
                e.discard();
                killed++;
            }
        }
        return killed;
    }
}
