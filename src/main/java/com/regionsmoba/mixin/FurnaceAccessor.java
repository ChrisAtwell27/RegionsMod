package com.regionsmoba.mixin;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor for AbstractFurnaceBlockEntity.litTimeRemaining so FurnaceLifeline can
 * decrement an extra tick per server tick during cold season — the doc rule
 * "Furnace burns fuel twice as fast" is implemented as one extra decrement
 * applied alongside the normal tick.
 */
@Mixin(AbstractFurnaceBlockEntity.class)
public interface FurnaceAccessor {

    @Accessor("litTimeRemaining")
    int regionsmoba$getLitTimeRemaining();

    @Accessor("litTimeRemaining")
    void regionsmoba$setLitTimeRemaining(int value);
}
