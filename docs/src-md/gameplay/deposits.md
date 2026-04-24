# Deposits

## Ore deposits

Each ore deposit is a single block registered with `/regions addoredeposit`. When mined, the block converts to cobblestone for a set cooldown. When the cooldown ends, the block regenerates to the original ore.

Outside registered deposits, blocks in all biomes behave normally.

## Mob deposits

Each mob deposit is a location registered with `/regions addmobdeposit`. A hologram above the deposit displays a countdown to the next spawn. At 0, one mob of the configured type spawns and the timer resets.

Mob type and spawn interval are set at registration.

## Cold season effect

Mountain ore deposits have longer cooldowns during cold season. Some mountain ore deposits flood or freeze and become temporarily inaccessible.
