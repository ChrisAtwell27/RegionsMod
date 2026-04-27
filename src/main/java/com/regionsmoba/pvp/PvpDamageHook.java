package com.regionsmoba.pvp;

import com.regionsmoba.match.MatchManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Filters damage events through {@link PvpManager}. Cancels player-vs-player
 * damage during warm phases (and any time PVP is otherwise off), with the trial
 * chamber as an override.
 *
 * Non-player damage (mobs, environment) is untouched. PVE remains vanilla.
 */
public final class PvpDamageHook {

    private PvpDamageHook() {}

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(PvpDamageHook::onAllowDamage);
    }

    private static boolean onAllowDamage(LivingEntity entity, DamageSource source, float amount) {
        if (!MatchManager.get().isActive()) return true;
        if (!(entity instanceof Player target)) return true;
        // Find the human attacker — direct or via projectile/tnt owner.
        Player attacker = attackerOf(source);
        if (attacker == null) return true; // not PVP — let it through (mobs, environment, fall, etc.)
        return PvpManager.isPvpAllowedFor(attacker, target);
    }

    private static Player attackerOf(DamageSource source) {
        if (source.getEntity() instanceof Player p) return p;
        if (source.getDirectEntity() instanceof Player p) return p;
        return null;
    }
}
