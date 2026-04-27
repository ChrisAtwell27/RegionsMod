package com.regionsmoba.team;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

/**
 * Applies and refreshes team passive mob effects per the team docs:
 *   Ocean    — permanent Water Breathing
 *   Nether   — permanent Fire Resistance (damage floor handled by separate slice)
 *   Mountain — permanent Night Vision
 *   Plains   — none
 *
 * "Permanent" is implemented as MobEffectInstance.INFINITE_DURATION (-1) so the
 * effect never decays. Refresh on respawn is wired by the death-handler slice.
 */
public final class TeamPassives {

    private TeamPassives() {}

    public static void apply(ServerPlayer player, BiomeTeam team) {
        Holder<MobEffect> effect = effectFor(team);
        if (effect == null) return;
        MobEffectInstance instance = new MobEffectInstance(
                effect,
                MobEffectInstance.INFINITE_DURATION,
                0,
                /* ambient */ true,
                /* visible */ false,
                /* showIcon */ true);
        player.addEffect(instance);
    }

    public static void clear(ServerPlayer player, BiomeTeam team) {
        Holder<MobEffect> effect = effectFor(team);
        if (effect == null) return;
        player.removeEffect(effect);
    }

    private static Holder<MobEffect> effectFor(BiomeTeam team) {
        return switch (team) {
            case OCEAN -> MobEffects.WATER_BREATHING;
            case NETHER -> MobEffects.FIRE_RESISTANCE;
            case MOUNTAIN -> MobEffects.NIGHT_VISION;
            case PLAINS -> null;
        };
    }
}
