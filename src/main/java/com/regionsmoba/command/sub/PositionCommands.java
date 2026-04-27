package com.regionsmoba.command.sub;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.regionsmoba.command.CommandHelpers;
import com.regionsmoba.config.BlockPosData;
import com.regionsmoba.config.RegionsConfig;
import com.regionsmoba.team.BiomeTeam;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Position commands. The operator's current location is registered.
 *
 * Covers from docs/src-md/reference/commands.md (Position commands section):
 *   setlobbyspawn / unsetlobbyspawn
 *   setspawn <biome> / removespawn <biome> <index> / listspawns
 *   setwardenspawn / unsetwardenspawn
 *   setguardianspawn <index> / removeguardianspawn <index> / listguardianspawns
 *   setwitherspawn / unsetwitherspawn
 *
 * Plus the related Trial Chamber position commands (addchamberspawn /
 * removechamberspawn / listchamberspawns).
 */
public final class PositionCommands {

    private PositionCommands() {}

    public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
        registerLobbySpawn(root);
        registerTeamSpawn(root);
        registerWardenSpawn(root);
        registerGuardianSpawn(root);
        registerWitherSpawn(root);
        registerChamberSpawn(root);
    }

    // ---- Lobby spawn ----

    private static void registerLobbySpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setlobbyspawn").executes(ctx -> {
            BlockPosData pos = CommandHelpers.senderPosData(ctx.getSource());
            RegionsConfig.get().lobbySpawn = pos;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Lobby spawn set to (" + pos + ")");
            return 1;
        }));
        root.then(Commands.literal("unsetlobbyspawn").executes(ctx -> {
            RegionsConfig.get().lobbySpawn = null;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Lobby spawn cleared.");
            return 1;
        }));
    }

    // ---- Team spawns (one biome can have multiple, indexed) ----

    private static void registerTeamSpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setspawn")
                .then(Commands.argument("biome", StringArgumentType.word())
                        .suggests(CommandHelpers.BIOME_SUGGESTIONS)
                        .executes(ctx -> setSpawn(ctx.getSource(), StringArgumentType.getString(ctx, "biome")))));

        root.then(Commands.literal("removespawn")
                .then(Commands.argument("biome", StringArgumentType.word())
                        .suggests(CommandHelpers.BIOME_SUGGESTIONS)
                        .then(Commands.argument("index", IntegerArgumentType.integer(0))
                                .executes(ctx -> removeSpawn(
                                        ctx.getSource(),
                                        StringArgumentType.getString(ctx, "biome"),
                                        IntegerArgumentType.getInteger(ctx, "index"))))));

        root.then(Commands.literal("listspawns").executes(ctx -> listSpawns(ctx.getSource())));
    }

    private static int setSpawn(CommandSourceStack src, String biomeId) {
        Optional<BiomeTeam> team = BiomeTeam.fromId(biomeId);
        if (team.isEmpty()) {
            CommandHelpers.fail(src, "Unknown biome: " + biomeId);
            return 0;
        }
        BlockPosData pos = CommandHelpers.senderPosData(src);
        List<BlockPosData> list = RegionsConfig.get().spawnsFor(team.get());
        list.add(pos);
        RegionsConfig.save();
        int index = list.size() - 1;
        CommandHelpers.ok(src, team.get().displayName() + " spawn #" + index + " set to (" + pos + ")");
        return index + 1;
    }

    private static int removeSpawn(CommandSourceStack src, String biomeId, int index) {
        Optional<BiomeTeam> team = BiomeTeam.fromId(biomeId);
        if (team.isEmpty()) {
            CommandHelpers.fail(src, "Unknown biome: " + biomeId);
            return 0;
        }
        List<BlockPosData> list = RegionsConfig.get().spawnsFor(team.get());
        if (index < 0 || index >= list.size()) {
            CommandHelpers.fail(src, team.get().displayName() + " has no spawn #" + index);
            return 0;
        }
        BlockPosData removed = list.remove(index);
        RegionsConfig.save();
        CommandHelpers.ok(src, "Removed " + team.get().displayName() + " spawn #" + index + " (" + removed + ")");
        return 1;
    }

    private static int listSpawns(CommandSourceStack src) {
        boolean any = false;
        for (BiomeTeam team : BiomeTeam.values()) {
            List<BlockPosData> list = RegionsConfig.get().spawnsFor(team);
            if (list.isEmpty()) continue;
            any = true;
            CommandHelpers.info(src, team.displayName() + " (" + list.size() + "):");
            for (int i = 0; i < list.size(); i++) {
                CommandHelpers.info(src, "  #" + i + " " + list.get(i));
            }
        }
        if (!any) CommandHelpers.info(src, "No team spawns registered.");
        return 1;
    }

    // ---- Warden spawn (single) ----

    private static void registerWardenSpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setwardenspawn").executes(ctx -> {
            BlockPosData pos = CommandHelpers.senderPosData(ctx.getSource());
            RegionsConfig.get().wardenSpawn = pos;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Warden spawn set to (" + pos + ")");
            return 1;
        }));
        root.then(Commands.literal("unsetwardenspawn").executes(ctx -> {
            RegionsConfig.get().wardenSpawn = null;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Warden spawn cleared.");
            return 1;
        }));
    }

    // ---- Guardian spawns (indexed slots) ----

    private static void registerGuardianSpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setguardianspawn")
                .then(Commands.argument("index", IntegerArgumentType.integer(0))
                        .executes(ctx -> {
                            int index = IntegerArgumentType.getInteger(ctx, "index");
                            BlockPosData pos = CommandHelpers.senderPosData(ctx.getSource());
                            RegionsConfig.get().guardianSpawns.put(index, pos);
                            RegionsConfig.save();
                            CommandHelpers.ok(ctx.getSource(), "Guardian spawn #" + index + " set to (" + pos + ")");
                            return 1;
                        })));

        root.then(Commands.literal("removeguardianspawn")
                .then(Commands.argument("index", IntegerArgumentType.integer(0))
                        .executes(ctx -> {
                            int index = IntegerArgumentType.getInteger(ctx, "index");
                            BlockPosData removed = RegionsConfig.get().guardianSpawns.remove(index);
                            if (removed == null) {
                                CommandHelpers.fail(ctx.getSource(), "No guardian spawn at index " + index);
                                return 0;
                            }
                            RegionsConfig.save();
                            CommandHelpers.ok(ctx.getSource(), "Removed guardian spawn #" + index);
                            return 1;
                        })));

        root.then(Commands.literal("listguardianspawns").executes(ctx -> {
            Map<Integer, BlockPosData> map = RegionsConfig.get().guardianSpawns;
            if (map.isEmpty()) {
                CommandHelpers.info(ctx.getSource(), "No guardian spawns registered.");
                return 1;
            }
            map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> CommandHelpers.info(ctx.getSource(), "#" + e.getKey() + " " + e.getValue()));
            return 1;
        }));
    }

    // ---- Wither spawn (single) ----

    private static void registerWitherSpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("setwitherspawn").executes(ctx -> {
            BlockPosData pos = CommandHelpers.senderPosData(ctx.getSource());
            RegionsConfig.get().witherSpawn = pos;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Wither spawn set to (" + pos + ")");
            return 1;
        }));
        root.then(Commands.literal("unsetwitherspawn").executes(ctx -> {
            RegionsConfig.get().witherSpawn = null;
            RegionsConfig.save();
            CommandHelpers.ok(ctx.getSource(), "Wither spawn cleared.");
            return 1;
        }));
    }

    // ---- Trial chamber spawns (auto-indexed list) ----

    private static void registerChamberSpawn(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("addchamberspawn").executes(ctx -> {
            BlockPosData pos = CommandHelpers.senderPosData(ctx.getSource());
            RegionsConfig.get().chamberSpawns.add(pos);
            RegionsConfig.save();
            int index = RegionsConfig.get().chamberSpawns.size() - 1;
            CommandHelpers.ok(ctx.getSource(), "Chamber spawn #" + index + " added at (" + pos + ")");
            return index + 1;
        }));

        root.then(Commands.literal("removechamberspawn")
                .then(Commands.argument("index", IntegerArgumentType.integer(0))
                        .executes(ctx -> {
                            int index = IntegerArgumentType.getInteger(ctx, "index");
                            List<BlockPosData> list = RegionsConfig.get().chamberSpawns;
                            if (index >= list.size()) {
                                CommandHelpers.fail(ctx.getSource(), "No chamber spawn at index " + index);
                                return 0;
                            }
                            list.remove(index);
                            RegionsConfig.save();
                            CommandHelpers.ok(ctx.getSource(), "Removed chamber spawn #" + index);
                            return 1;
                        })));

        root.then(Commands.literal("listchamberspawns").executes(ctx -> {
            List<BlockPosData> list = RegionsConfig.get().chamberSpawns;
            if (list.isEmpty()) {
                CommandHelpers.info(ctx.getSource(), "No chamber spawns registered.");
                return 1;
            }
            for (int i = 0; i < list.size(); i++) {
                CommandHelpers.info(ctx.getSource(), "#" + i + " " + list.get(i));
            }
            return 1;
        }));
    }
}
