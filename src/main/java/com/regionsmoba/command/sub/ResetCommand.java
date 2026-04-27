package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.config.RegionsConfig;
import com.regionsmoba.match.MatchManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * `/regions reset all` — wipes every registration. Two-step confirmation:
 * the first invocation prints a warning; `/regions reset all confirm` actually
 * performs the wipe. Refuses while a match is active.
 */
public final class ResetCommand {

    private ResetCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("reset")
                .then(Commands.literal("all")
                        .executes(ctx -> warn(ctx.getSource()))
                        .then(Commands.literal("confirm").executes(ctx -> doReset(ctx.getSource())))));
    }

    private static int warn(CommandSourceStack src) {
        if (!MatchManager.get().isDormant()) {
            src.sendFailure(Component.literal("Cannot reset while a match is in progress. Run /regions abort first.")
                    .withStyle(ChatFormatting.RED));
            return 0;
        }
        src.sendSuccess(() -> Component.literal(
                "This will delete every area, position, target, deposit, and spawn. "
                        + "Run /regions reset all confirm to proceed.")
                .withStyle(ChatFormatting.YELLOW), false);
        return 1;
    }

    private static int doReset(CommandSourceStack src) {
        if (!MatchManager.get().isDormant()) {
            src.sendFailure(Component.literal("Cannot reset while a match is in progress.")
                    .withStyle(ChatFormatting.RED));
            return 0;
        }
        RegionsConfig.resetAll();
        src.sendSuccess(() -> Component.literal("All registrations cleared.")
                .withStyle(ChatFormatting.GREEN), true);
        return 1;
    }
}
