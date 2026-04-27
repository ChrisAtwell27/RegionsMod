package com.regionsmoba.team;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.Optional;

public enum BiomeTeam {
    OCEAN("ocean", "Ocean", ChatFormatting.BLUE),
    NETHER("nether", "Nether", ChatFormatting.RED),
    PLAINS("plains", "Plains", ChatFormatting.GREEN),
    MOUNTAIN("mountain", "Mountain", ChatFormatting.GRAY);

    private final String id;
    private final String displayName;
    private final ChatFormatting color;

    BiomeTeam(String id, String displayName, ChatFormatting color) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public ChatFormatting color() {
        return color;
    }

    public Component coloredName() {
        return Component.literal(displayName).withStyle(color);
    }

    public static Optional<BiomeTeam> fromId(String id) {
        if (id == null) return Optional.empty();
        String key = id.toLowerCase(Locale.ROOT);
        for (BiomeTeam t : values()) {
            if (t.id.equals(key)) return Optional.of(t);
        }
        return Optional.empty();
    }
}
