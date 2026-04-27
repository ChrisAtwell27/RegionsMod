package com.regionsmoba.team;

import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Every class in the game. Each {@link BiomeTeam} has 6 classes (24 total). Names
 * match docs/src-md/classes/{team}-classes.md exactly.
 *
 * Slice 4 wires class selection only — actual kits and abilities land in slice 9.
 */
public enum BiomeClass {

    // ---- Ocean ----
    OCEAN_DEFENDER(BiomeTeam.OCEAN, "Defender"),
    OCEAN_IMMOBILIZER(BiomeTeam.OCEAN, "Immobilizer"),
    OCEAN_NEPTUNE(BiomeTeam.OCEAN, "Neptune"),
    OCEAN_SIREN(BiomeTeam.OCEAN, "Siren"),
    OCEAN_HEALER(BiomeTeam.OCEAN, "Healer"),
    OCEAN_TRANSPORTER(BiomeTeam.OCEAN, "Transporter"),

    // ---- Nether ----
    NETHER_ALCHEMIST(BiomeTeam.NETHER, "Alchemist"),
    NETHER_ENCHANTER(BiomeTeam.NETHER, "Enchanter"),
    NETHER_BLOODMAGE(BiomeTeam.NETHER, "Bloodmage"),
    NETHER_WIZARD(BiomeTeam.NETHER, "Wizard"),
    NETHER_VAMPIRE(BiomeTeam.NETHER, "Vampire"),
    NETHER_RIFT_WALKER(BiomeTeam.NETHER, "Rift Walker"),

    // ---- Plains ----
    PLAINS_ARCHER(BiomeTeam.PLAINS, "Archer"),
    PLAINS_SPY(BiomeTeam.PLAINS, "Spy"),
    PLAINS_FARMER(BiomeTeam.PLAINS, "Farmer"),
    PLAINS_BARD(BiomeTeam.PLAINS, "Bard"),
    PLAINS_LUMBERJACK(BiomeTeam.PLAINS, "Lumberjack"),
    PLAINS_SCOUT(BiomeTeam.PLAINS, "Scout"),

    // ---- Mountain ----
    MOUNTAIN_BUILDER(BiomeTeam.MOUNTAIN, "Builder"),
    MOUNTAIN_WARRIOR(BiomeTeam.MOUNTAIN, "Warrior"),
    MOUNTAIN_BERSERKER(BiomeTeam.MOUNTAIN, "Berserker"),
    MOUNTAIN_MINER(BiomeTeam.MOUNTAIN, "Miner"),
    MOUNTAIN_TINKERER(BiomeTeam.MOUNTAIN, "Tinkerer"),
    MOUNTAIN_ACROBAT(BiomeTeam.MOUNTAIN, "Acrobat");

    private final BiomeTeam team;
    private final String displayName;

    BiomeClass(BiomeTeam team, String displayName) {
        this.team = team;
        this.displayName = displayName;
    }

    public BiomeTeam team() {
        return team;
    }

    public String displayName() {
        return displayName;
    }

    /** Lowercase id for chat / commands, e.g. "defender", "rift_walker". */
    public String id() {
        return displayName.toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    public static List<BiomeClass> forTeam(BiomeTeam team) {
        return EnumSet.allOf(BiomeClass.class).stream()
                .filter(c -> c.team == team)
                .collect(Collectors.toList());
    }

    public static Optional<BiomeClass> fromId(BiomeTeam team, String id) {
        if (id == null) return Optional.empty();
        String key = id.toLowerCase(Locale.ROOT);
        for (BiomeClass c : forTeam(team)) {
            if (c.id().equals(key)) return Optional.of(c);
        }
        return Optional.empty();
    }

    public static Set<BiomeClass> all() {
        return EnumSet.allOf(BiomeClass.class);
    }
}
