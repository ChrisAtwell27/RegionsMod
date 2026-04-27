package com.regionsmoba.lobby;

import com.regionsmoba.team.BiomeClass;
import com.regionsmoba.team.BiomeTeam;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * 6-slot class picker, populated with the player's biome's classes in doc order.
 *
 * Per-class icons are placeholders for slice 4 — paper named with the class name.
 * Slice 9 will swap to thematic icons matching each class's signature item.
 */
public final class ClassPickerMenu extends PickerMenu {

    public static Component title(BiomeTeam team) {
        return Component.literal("Pick your " + team.displayName() + " class").withStyle(team.color());
    }

    private final List<BiomeClass> slotOrder;

    public ClassPickerMenu(int syncId, Inventory playerInv, BiomeTeam team) {
        super(syncId, playerInv, makeDisplay(team));
        this.slotOrder = BiomeClass.forTeam(team);
    }

    private static SimpleContainer makeDisplay(BiomeTeam team) {
        List<BiomeClass> classes = BiomeClass.forTeam(team);
        NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < classes.size() && i < 9; i++) {
            stacks.set(i, icon(classes.get(i)));
        }
        return makeContainer(stacks);
    }

    @Override
    protected void onSlotClicked(int slot, Player player) {
        if (slot >= slotOrder.size()) return;
        BiomeClass picked = slotOrder.get(slot);
        LobbyFlow.onClassPicked(player, picked);
    }

    private static ItemStack icon(BiomeClass biomeClass) {
        ItemStack stack = new ItemStack(Items.PAPER);
        stack.set(DataComponents.CUSTOM_NAME,
                Component.literal(biomeClass.displayName()).withStyle(ChatFormatting.WHITE));
        return stack;
    }
}
