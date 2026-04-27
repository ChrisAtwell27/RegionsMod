package com.regionsmoba.lobby;

import com.regionsmoba.team.BiomeTeam;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * 4-slot team picker. Slot order matches docs/src-md/getting-started/joining-a-game.md:
 *   slot 0: Ocean (blue), 1: Nether (red), 2: Plains (green), 3: Mountain (gray).
 *
 * The picker uses a 9-slot chest menu so the icons sit in the leftmost row;
 * remaining slots stay empty (clicks ignored).
 */
public final class TeamPickerMenu extends PickerMenu {

    /** Order is doc-canonical (Ocean → Nether → Plains → Mountain). */
    private static final BiomeTeam[] SLOT_ORDER = {
            BiomeTeam.OCEAN, BiomeTeam.NETHER, BiomeTeam.PLAINS, BiomeTeam.MOUNTAIN
    };

    public static final Component TITLE = Component.literal("Pick your nation").withStyle(ChatFormatting.GOLD);

    public static SimpleContainer makeDisplay() {
        NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_ORDER.length; i++) {
            stacks.set(i, icon(SLOT_ORDER[i]));
        }
        return makeContainer(stacks);
    }

    public TeamPickerMenu(int syncId, Inventory playerInv) {
        super(syncId, playerInv, makeDisplay());
    }

    @Override
    protected void onSlotClicked(int slot, Player player) {
        if (slot >= SLOT_ORDER.length) return;
        BiomeTeam team = SLOT_ORDER[slot];
        // Defer to LobbyFlow — it knows about capacity, teleport, and the next picker.
        LobbyFlow.onTeamPicked(player, team);
    }

    private static ItemStack icon(BiomeTeam team) {
        Item item = switch (team) {
            case OCEAN -> Items.BLUE_CONCRETE;
            case NETHER -> Items.RED_CONCRETE;
            case PLAINS -> Items.GREEN_CONCRETE;
            case MOUNTAIN -> Items.GRAY_CONCRETE;
        };
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponents.CUSTOM_NAME,
                Component.literal(team.displayName()).withStyle(team.color()));
        return stack;
    }
}
