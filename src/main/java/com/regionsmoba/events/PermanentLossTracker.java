package com.regionsmoba.events;

import com.regionsmoba.RegionsMOBA;
import com.regionsmoba.team.BiomeTeam;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.EnumMap;
import java.util.Map;

/**
 * Tracks per-team starting member count and permanent losses, and fires the
 * one-shot mass event when half (rounded down) of a team's starting members are
 * permanently lost.
 *
 * Per docs/src-md/gameplay/mass-events.md:
 *   "A player is 'permanently lost' once they are out of lives and locked into
 *    spectator mode. Ocean players who die with the conduit at 0 HP also count."
 *
 * Both paths route through {@link Lives#enterSpectator}, which calls
 * {@link #recordPermanentLoss}.
 */
public final class PermanentLossTracker {

    private static final PermanentLossTracker INSTANCE = new PermanentLossTracker();

    public static PermanentLossTracker get() {
        return INSTANCE;
    }

    private final Map<BiomeTeam, Integer> startingMembers = new EnumMap<>(BiomeTeam.class);
    private final Map<BiomeTeam, Integer> permanentLosses = new EnumMap<>(BiomeTeam.class);
    private final Map<BiomeTeam, Boolean> massEventFired = new EnumMap<>(BiomeTeam.class);

    private PermanentLossTracker() {
        resetAll();
    }

    public void resetAll() {
        startingMembers.clear();
        permanentLosses.clear();
        massEventFired.clear();
        for (BiomeTeam t : BiomeTeam.values()) {
            startingMembers.put(t, 0);
            permanentLosses.put(t, 0);
            massEventFired.put(t, false);
        }
    }

    /** Called from LobbyFlow.onTeamPicked when a player commits to a team. */
    public void recordJoin(BiomeTeam team) {
        startingMembers.merge(team, 1, Integer::sum);
    }

    public int startingMembers(BiomeTeam team) {
        return startingMembers.getOrDefault(team, 0);
    }

    public int permanentLosses(BiomeTeam team) {
        return permanentLosses.getOrDefault(team, 0);
    }

    public boolean massEventFired(BiomeTeam team) {
        return massEventFired.getOrDefault(team, false);
    }

    /**
     * Called from {@link com.regionsmoba.lifeline.Lives#enterSpectator} for every
     * permanent-loss transition. Increments the count and triggers the mass event
     * if threshold is reached.
     */
    public void recordPermanentLoss(BiomeTeam team, MinecraftServer server) {
        int losses = permanentLosses.merge(team, 1, Integer::sum);
        int starting = startingMembers.getOrDefault(team, 0);
        int threshold = starting / 2;
        RegionsMOBA.LOGGER.info("Permanent loss: team={} losses={}/{} threshold={}",
                team.id(), losses, starting, threshold);
        if (!massEventFired.getOrDefault(team, false) && threshold > 0 && losses >= threshold) {
            fire(team, server);
        }
    }

    /** /regions debug massevent fire — bypasses the threshold check. */
    public void forceFire(BiomeTeam team, MinecraftServer server) {
        if (massEventFired.getOrDefault(team, false)) {
            massEventFired.put(team, false); // allow re-fire after debug reset
        }
        fire(team, server);
    }

    /** /regions debug massevent reset — re-arm the trigger. */
    public void resetTeam(BiomeTeam team) {
        massEventFired.put(team, false);
    }

    private void fire(BiomeTeam team, MinecraftServer server) {
        massEventFired.put(team, true);
        switch (team) {
            case PLAINS -> PlainsMassEvent.spawn(server);
            case MOUNTAIN -> MountainMassEvent.spawn(server);
            case OCEAN -> OceanMassEvent.spawn(server);
            case NETHER -> NetherMassEvent.spawn(server);
        }
        broadcast(server, Component.literal("Mass event triggered for " + team.displayName())
                .withStyle(team.color(), ChatFormatting.BOLD));
    }

    private static void broadcast(MinecraftServer server, Component msg) {
        if (server == null) return;
        for (ServerPlayer p : server.getPlayerList().getPlayers()) {
            p.sendSystemMessage(msg);
        }
    }
}
