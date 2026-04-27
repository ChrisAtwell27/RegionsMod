package com.regionsmoba.events;

import com.regionsmoba.match.MatchManager;
import com.regionsmoba.team.BiomeTeam;
import com.regionsmoba.team.MatchPlayerState;
import com.regionsmoba.team.TeamAssignments;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Per-tick maintenance for mass-event mobs:
 *
 *   1. Target filter — Wardens, Pillagers, Elder Guardians, and the Wither must
 *      never aggro players on the team they were spawned to defend. We don't
 *      mixin the AI; instead we clear the target each tick if it's a teammate.
 *      The mob picks a new target on the next AI tick.
 *
 *   2. Stationary pin — Elder Guardians and the Wither are required to be
 *      stationary. We re-snap their position and zero their delta each tick.
 *      Vanilla shoot/laser attacks still fire because we leave AI enabled.
 *
 * In-memory only; reset on match start/end.
 */
public final class MassEventTargeting {

    private static final MassEventTargeting INSTANCE = new MassEventTargeting();

    public static MassEventTargeting get() {
        return INSTANCE;
    }

    private record StationaryAnchor(ResourceKey<Level> dim, double x, double y, double z) {}

    private final Map<UUID, BiomeTeam> protectedFor = new HashMap<>();
    private final Map<UUID, StationaryAnchor> stationary = new HashMap<>();

    private MassEventTargeting() {}

    public void tag(Entity entity, BiomeTeam protectedTeam) {
        if (entity == null) return;
        protectedFor.put(entity.getUUID(), protectedTeam);
    }

    public void pin(Entity entity, ServerLevel level, BlockPos pos) {
        if (entity == null || level == null) return;
        stationary.put(entity.getUUID(),
                new StationaryAnchor(level.dimension(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
    }

    public void resetAll() {
        protectedFor.clear();
        stationary.clear();
    }

    public void tick(MinecraftServer server) {
        if (!MatchManager.get().isActive() || server == null) return;

        // Target filter — drop targets that are on the protected team.
        Iterator<Map.Entry<UUID, BiomeTeam>> it = protectedFor.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, BiomeTeam> e = it.next();
            Entity entity = findEntity(server, e.getKey());
            if (entity == null || !entity.isAlive()) {
                it.remove();
                continue;
            }
            if (!(entity instanceof Mob mob)) continue;
            LivingEntity target = mob.getTarget();
            if (target == null) continue;
            UUID targetId = target.getUUID();
            MatchPlayerState s = TeamAssignments.get().state(targetId);
            if (s != null && s.team == e.getValue()) {
                mob.setTarget(null);
            }
        }

        // Stationary pin — re-snap position + zero velocity.
        Iterator<Map.Entry<UUID, StationaryAnchor>> sit = stationary.entrySet().iterator();
        while (sit.hasNext()) {
            Map.Entry<UUID, StationaryAnchor> e = sit.next();
            Entity entity = findEntity(server, e.getKey());
            if (entity == null || !entity.isAlive()) {
                sit.remove();
                continue;
            }
            StationaryAnchor a = e.getValue();
            entity.setPos(a.x, a.y, a.z);
            entity.setDeltaMovement(Vec3.ZERO);
        }
    }

    private static Entity findEntity(MinecraftServer server, UUID id) {
        for (ServerLevel lvl : server.getAllLevels()) {
            Entity e = lvl.getEntity(id);
            if (e != null) return e;
        }
        return null;
    }
}
