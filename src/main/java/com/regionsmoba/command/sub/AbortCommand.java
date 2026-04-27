package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.match.MatchManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class AbortCommand {

    private AbortCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("abort").executes(ctx -> {
            MatchManager match = MatchManager.get();
            if (match.isDormant()) {
                ctx.getSource().sendFailure(
                        Component.literal("No active match to abort.").withStyle(ChatFormatting.RED));
                return 0;
            }
            match.abort();
            ctx.getSource().sendSuccess(
                    () -> Component.literal("Match aborted.").withStyle(ChatFormatting.YELLOW), true);
            return 1;
        }));
    }
}
