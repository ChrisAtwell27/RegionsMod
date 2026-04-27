package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.command.CommandHelpers;
import com.regionsmoba.match.MatchManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * /regions start — manually start a match. Per the docs this bypasses the sign
 * trigger; no joiners are auto-registered. Useful for debugging and for
 * sign-less playtests.
 */
public final class StartCommand {

    private StartCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("start").executes(ctx -> {
            CommandSourceStack src = ctx.getSource();
            MatchManager match = MatchManager.get();
            if (match.isActive()) {
                CommandHelpers.fail(src, "A match is already in progress. Use /regions abort first.");
                return 0;
            }
            match.start(src.getServer());
            CommandHelpers.ok(src, "Match started.");
            return 1;
        }));
    }
}
