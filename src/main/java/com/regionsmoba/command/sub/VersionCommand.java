package com.regionsmoba.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.RegionsMOBA;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class VersionCommand {

    private VersionCommand() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("version").executes(ctx -> {
            String version = FabricLoader.getInstance()
                    .getModContainer(RegionsMOBA.MOD_ID)
                    .map(c -> c.getMetadata().getVersion().getFriendlyString())
                    .orElse("unknown");
            ctx.getSource().sendSuccess(
                    () -> Component.literal("RegionsMOBA " + version).withStyle(ChatFormatting.AQUA),
                    false);
            return 1;
        }));
    }
}
