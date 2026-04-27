package com.regionsmoba.command.sub;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.command.CommandHelpers;
import com.regionsmoba.config.Area;
import com.regionsmoba.config.BlockPosData;
import com.regionsmoba.config.RegionsConfig;
import com.regionsmoba.team.BiomeTeam;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Two-step area commands. pos1 sets the high corner (max X/Y/Z); pos2 sets the
 * low corner (min X/Y/Z). The area is "complete" once both corners are set.
 *
 * Covers from docs/src-md/reference/commands.md (Area + Protected area + Chamber):
 *   setlobby pos1|pos2 / unsetlobby
 *   setbiomebounds <biome> pos1|pos2 / unsetbiomebounds <biome>
 *   addprotectedarea <name> pos1|pos2 / removeprotectedarea / listprotectedareas
 *   setchamberbounds pos1|pos2 / unsetchamberbounds
 */
public final class AreaCommands {

    private AreaCommands() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        registerLobby(root);
        registerBiomeBounds(root);
        registerProtectedArea(root);
        registerChamberBounds(root);
    }

    // ---- Lobby ----

    private static void registerLobby(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setlobby")
                .then(Commands.literal("pos1").executes(ctx -> setCorner(ctx.getSource(), "Lobby", true,
                        () -> RegionsConfig.get().lobby,
                        a -> RegionsConfig.get().lobby = a)))
                .then(Commands.literal("pos2").executes(ctx -> setCorner(ctx.getSource(), "Lobby", false,
                        () -> RegionsConfig.get().lobby,
                        a -> RegionsConfig.get().lobby = a))));

        root.then(Commands.literal("unsetlobby").executes(ctx -> {
            RegionsConfig.get().lobby = null;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Lobby cleared.");
            return 1;
        }));
    }

    // ---- Biome bounds ----

    private static void registerBiomeBounds(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setbiomebounds")
                .then(Commands.argument("biome", StringArgumentType.word())
                        .suggests(CommandHelpers.BIOME_SUGGESTIONS)
                        .then(Commands.literal("pos1").executes(ctx -> biomeCorner(ctx.getSource(),
                                StringArgumentType.getString(ctx, "biome"), true)))
                        .then(Commands.literal("pos2").executes(ctx -> biomeCorner(ctx.getSource(),
                                StringArgumentType.getString(ctx, "biome"), false)))));

        root.then(Commands.literal("unsetbiomebounds")
                .then(Commands.argument("biome", StringArgumentType.word())
                        .suggests(CommandHelpers.BIOME_SUGGESTIONS)
                        .executes(ctx -> {
                            String id = StringArgumentType.getString(ctx, "biome");
                            Optional<BiomeTeam> team = BiomeTeam.fromId(id);
                            if (team.isEmpty()) {
                                CommandHelpers.fail(ctx.getSource(), "Unknown biome: " + id);
                                return 0;
                            }
                            if (RegionsConfig.get().biomeBounds.remove(team.get().id()) == null) {
                                CommandHelpers.warn(ctx.getSource(), team.get().displayName() + " bounds were not set.");
                                return 0;
                            }
                            RegionsConfig.save();
                            CommandHelpers.ok(ctx.getSource(), team.get().displayName() + " bounds cleared.");
                            return 1;
                        })));
    }

    private static int biomeCorner(CommandSourceStack src, String biomeId, boolean isHigh) {
        Optional<BiomeTeam> team = BiomeTeam.fromId(biomeId);
        if (team.isEmpty()) {
            CommandHelpers.fail(src, "Unknown biome: " + biomeId);
            return 0;
        }
        return setCorner(src, team.get().displayName() + " bounds", isHigh,
                () -> RegionsConfig.get().biomeBounds.get(team.get().id()),
                a -> RegionsConfig.get().biomeBounds.put(team.get().id(), a));
    }

    // ---- Named protected areas ----

    private static void registerProtectedArea(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("addprotectedarea")
                .then(Commands.argument("name", StringArgumentType.word())
                        .then(Commands.literal("pos1").executes(ctx -> protectedCorner(ctx.getSource(),
                                StringArgumentType.getString(ctx, "name"), true)))
                        .then(Commands.literal("pos2").executes(ctx -> protectedCorner(ctx.getSource(),
                                StringArgumentType.getString(ctx, "name"), false)))));

        root.then(Commands.literal("removeprotectedarea")
                .then(Commands.argument("name", StringArgumentType.word())
                        .executes(ctx -> {
                            String name = StringArgumentType.getString(ctx, "name");
                            if (RegionsConfig.get().protectedAreas.remove(name) == null) {
                                CommandHelpers.fail(ctx.getSource(), "No protected area named '" + name + "'");
                                return 0;
                            }
                            RegionsConfig.save();
                            CommandHelpers.ok(ctx.getSource(), "Removed protected area '" + name + "'");
                            return 1;
                        })));

        root.then(Commands.literal("listprotectedareas").executes(ctx -> {
            Map<String, Area> map = RegionsConfig.get().protectedAreas;
            if (map.isEmpty()) {
                CommandHelpers.info(ctx.getSource(), "No protected areas registered.");
                return 1;
            }
            map.forEach((name, area) -> {
                String state = area.isComplete()
                        ? "high=" + area.high() + " low=" + area.low()
                        : "incomplete";
                CommandHelpers.info(ctx.getSource(), name + ": " + state);
            });
            return 1;
        }));
    }

    private static int protectedCorner(CommandSourceStack src, String name, boolean isHigh) {
        return setCorner(src, "Protected area '" + name + "'", isHigh,
                () -> RegionsConfig.get().protectedAreas.get(name),
                a -> RegionsConfig.get().protectedAreas.put(name, a));
    }

    // ---- Trial chamber bounds ----

    private static void registerChamberBounds(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setchamberbounds")
                .then(Commands.literal("pos1").executes(ctx -> setCorner(ctx.getSource(), "Trial chamber", true,
                        () -> RegionsConfig.get().chamberBounds,
                        a -> RegionsConfig.get().chamberBounds = a)))
                .then(Commands.literal("pos2").executes(ctx -> setCorner(ctx.getSource(), "Trial chamber", false,
                        () -> RegionsConfig.get().chamberBounds,
                        a -> RegionsConfig.get().chamberBounds = a))));

        root.then(Commands.literal("unsetchamberbounds").executes(ctx -> {
            RegionsConfig.get().chamberBounds = null;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Trial chamber bounds cleared.");
            return 1;
        }));
    }

    // ---- Shared corner-set logic ----

    /**
     * Sets one corner of an Area-typed slot. Reads the existing partial Area via
     * {@code getter}, writes the merged result via {@code setter}, and reports back
     * whether the area is now complete.
     */
    private static int setCorner(
            CommandSourceStack src,
            String label,
            boolean isHigh,
            Supplier<Area> getter,
            java.util.function.Consumer<Area> setter) {
        BlockPos at = CommandHelpers.senderBlockPos(src);
        ServerLevel level = src.getLevel();
        Area existing = getter.get();
        Area corner = isHigh ? Area.onlyHigh(level, at) : Area.onlyLow(level, at);
        Area merged;
        if (existing == null) {
            merged = corner;
        } else {
            BlockPosData high = isHigh ? corner.high() : existing.high();
            BlockPosData low = isHigh ? existing.low() : corner.low();
            merged = new Area(high, low);
        }
        setter.accept(merged);
        RegionsConfig.save();

        String which = isHigh ? "high" : "low";
        if (merged.isComplete()) {
            CommandHelpers.ok(src, label + " " + which + " corner set; area complete (high=" + merged.high()
                    + " low=" + merged.low() + ")");
        } else {
            CommandHelpers.warn(src, label + " " + which + " corner set; waiting for "
                    + (isHigh ? "pos2" : "pos1"));
        }
        return 1;
    }
}
