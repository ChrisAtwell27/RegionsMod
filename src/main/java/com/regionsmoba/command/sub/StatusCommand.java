package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.config.RegionsConfig;
import com.regionsmoba.match.MatchManager;
import com.regionsmoba.match.MatchState;
import com.regionsmoba.timeline.MatchPhase;
import com.regionsmoba.timeline.Timeline;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class StatusCommand {

    private StatusCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("status").executes(ctx -> {
            CommandSourceStack src = ctx.getSource();
            MatchManager match = MatchManager.get();
            RegionsConfig.Counts counts = RegionsConfig.get().counts();

            src.sendSuccess(() -> header("Match"), false);
            src.sendSuccess(() -> kv("State", match.state().name(), stateColor(match.state())), false);
            if (match.isActive()) {
                long ticks = match.elapsedTicks();
                long seconds = ticks / 20;
                src.sendSuccess(() -> kv("Elapsed", seconds + "s", ChatFormatting.WHITE), false);
                Timeline t = Timeline.get();
                if (t.isRunning()) {
                    src.sendSuccess(() -> kv("Phase", t.phase().name(), phaseColor(t.phase())), false);
                    src.sendSuccess(() -> kv("Phase ends in", t.secondsRemainingInPhase() + "s", ChatFormatting.WHITE), false);
                    src.sendSuccess(() -> kv("PVP", t.phase() == MatchPhase.COLD || t.isPvpPermanent() ? "ON" : "OFF",
                            (t.phase() == MatchPhase.COLD || t.isPvpPermanent()) ? ChatFormatting.RED : ChatFormatting.GREEN), false);
                    if (t.isPvpPermanent()) {
                        src.sendSuccess(() -> kv("PVP-Permanent", "yes", ChatFormatting.RED), false);
                    }
                }
                src.sendSuccess(() -> kv("Players", String.valueOf(match.matchPlayers().size()), ChatFormatting.WHITE), false);
            }

            src.sendSuccess(() -> header("Registrations"), false);
            src.sendSuccess(() -> kv("Areas", String.valueOf(counts.areas()), ChatFormatting.WHITE), false);
            src.sendSuccess(() -> kv("Positions", String.valueOf(counts.positions()), ChatFormatting.WHITE), false);
            src.sendSuccess(() -> kv("Targets", String.valueOf(counts.targets()), ChatFormatting.WHITE), false);
            src.sendSuccess(() -> kv("Deposits", String.valueOf(counts.deposits()), ChatFormatting.WHITE), false);
            return 1;
        }));
    }

    private static Component header(String text) {
        return Component.literal("== " + text + " ==").withStyle(ChatFormatting.GOLD);
    }

    private static Component kv(String key, String value, ChatFormatting valueColor) {
        return Component.literal(key + ": ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(value).withStyle(valueColor));
    }

    private static ChatFormatting stateColor(MatchState state) {
        return switch (state) {
            case DORMANT -> ChatFormatting.DARK_GRAY;
            case LOBBY -> ChatFormatting.YELLOW;
            case ACTIVE -> ChatFormatting.GREEN;
            case ENDED -> ChatFormatting.RED;
        };
    }

    private static ChatFormatting phaseColor(MatchPhase phase) {
        return phase == MatchPhase.COLD ? ChatFormatting.AQUA : ChatFormatting.GOLD;
    }
}
