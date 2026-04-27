# Deposits

## Ore deposits

Each ore deposit is a single block registered with `/regions addoredeposit <time>`. When mined, the block converts to cobblestone for the configured cooldown. When the cooldown ends, the block regenerates to the original ore. Each block is mined and regenerated independently — neighbours in the same vein are unaffected.

Registration is a session: run the command once, then right-click each ore block in the vein to add it. The first click locks the ore type, so every block registered in the session must be the same type. Registered blocks display as emerald blocks during the session as a visual confirmation; run `/regions done` to end the session, at which point the markers revert to their normal ore appearance.

Outside registered deposits, blocks in all biomes behave normally.

## Block deposits

A block deposit is the same idea as an ore deposit, but for any block type — useful for trees, sand, glowstone walls, decorative crystal pillars, or anything else a map needs to regenerate after being mined. Register one with `/regions addblockdeposit <time>`. Each block is mined and regenerated independently. Unlike ore deposits, a mined block deposit breaks fully — it drops normally and leaves air in its place — then regenerates back to the registered block when the cooldown ends. There is no intermediate filler block.

Registration uses the same session flow as ore deposits: run the command, right-click the first block to lock the block type for the session, click further matching blocks to add them, and run `/regions done` to finish. Registered blocks display as emerald blocks during the session.

## Mob deposits

Each mob deposit is a location registered with `/regions addmobdeposit`. A hologram above the deposit displays a countdown to the next spawn. At 0, one mob of the configured type spawns and the timer resets.

Mob type and spawn interval are set at registration.

## Cold season effect

Mountain ore deposits have longer cooldowns during cold season. Some mountain ore deposits flood or freeze and become temporarily inaccessible.

## Match end

All deposits — ore, block, and mob — are restored to their starting state when a match ends. See [Match end and map repair](timeline.md#match-end-and-map-repair).
