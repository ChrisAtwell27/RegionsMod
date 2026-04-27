package com.regionsmoba.lobby;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

/**
 * Read-only single-row chest menu (9 slots). Subclasses populate the container
 * and override {@link #onSlotClicked(int, Player)} to handle picks. All item
 * movement is blocked: shift-click, swap, drop, drag — every interaction is
 * intercepted as a slot click on the chest portion only. Player-inventory clicks
 * are no-ops.
 */
public abstract class PickerMenu extends ChestMenu {

    protected final Container display;

    protected PickerMenu(int syncId, Inventory playerInv, Container display) {
        super(MenuType.GENERIC_9x1, syncId, playerInv, display, 1);
        this.display = display;
    }

    protected static SimpleContainer makeContainer(NonNullList<ItemStack> stacks) {
        SimpleContainer c = new SimpleContainer(9);
        for (int i = 0; i < Math.min(9, stacks.size()); i++) {
            c.setItem(i, stacks.get(i));
        }
        return c;
    }

    /** Called when the player clicks chest slot {@code slot} (0-8). Subclasses act. */
    protected abstract void onSlotClicked(int slot, Player player);

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Block shift-click moves entirely — items must stay in place.
        return ItemStack.EMPTY;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickType, Player player) {
        // Re-route every click into our handler if it's a chest slot. Skip the
        // super call so vanilla never moves items, opens a context menu, etc.
        if (slotId >= 0 && slotId < 9) {
            onSlotClicked(slotId, player);
        }
        // Player-inventory clicks (slotId 9..44) are silently ignored.
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
