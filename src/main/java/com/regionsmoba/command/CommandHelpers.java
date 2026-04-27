package com.regionsmoba.command;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.regionsmoba.config.BlockPosData;
import com.regionsmoba.team.BiomeTeam;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public final class CommandHelpers {

    /** String-arg suggestion provider that lists every biome team id. */
    public static final SuggestionProvider<CommandSourceStack> BIOME_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    new String[]{
                            BiomeTeam.OCEAN.id(),
                            BiomeTeam.NETHER.id(),
                            BiomeTeam.PLAINS.id(),
                            BiomeTeam.MOUNTAIN.id()},
                    builder);

    private CommandHelpers() {}

    public static BlockPos senderBlockPos(CommandSourceStack src) {
        return BlockPos.containing(src.getPosition());
    }

    public static BlockPosData senderPosData(CommandSourceStack src) {
        return BlockPosData.of(src.getLevel(), senderBlockPos(src));
    }

    public static void ok(CommandSourceStack src, String msg) {
        src.sendSuccess(() -> Component.literal(msg).withStyle(ChatFormatting.GREEN), false);
    }

    public static void info(CommandSourceStack src, String msg) {
        src.sendSuccess(() -> Component.literal(msg).withStyle(ChatFormatting.WHITE), false);
    }

    public static void warn(CommandSourceStack src, String msg) {
        src.sendSuccess(() -> Component.literal(msg).withStyle(ChatFormatting.YELLOW), false);
    }

    public static void fail(CommandSourceStack src, String msg) {
        src.sendFailure(Component.literal(msg).withStyle(ChatFormatting.RED));
    }
}
