package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.command.CommandHelpers;
import com.regionsmoba.match.MatchManager;
import com.regionsmoba.timeline.Timeline;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * /regions skip-season — force the next phase change immediately. Useful for
 * playtest pacing and debugging cold-season effects without waiting 15 minutes.
 */
public final class SkipSeasonCommand {

    private SkipSeasonCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("skip-season").executes(ctx -> {
            if (!MatchManager.get().isActive() || !Timeline.get().isRunning()) {
                CommandHelpers.fail(ctx.getSource(), "No active match — nothing to skip.");
                return 0;
            }
            Timeline.PhaseChange change = Timeline.get().skip();
            if (change == null) {
                CommandHelpers.fail(ctx.getSource(), "Skip failed.");
                return 0;
            }
            CommandHelpers.ok(ctx.getSource(), "Skipped to " + change.to() + " phase.");
            return 1;
        }));
    }
}
