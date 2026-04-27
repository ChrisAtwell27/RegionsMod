# Protected Areas

A protected area is a region where default block interactions are disabled to keep match-critical objects and zones intact.

## Sources of protection

Protected areas come from two places:

1. **Implicit** — any location registered with a `/regions` command is protected. This includes the lobby, biome bounds, the three lifeline blocks (Conduit, Furnace, Composter), traders, and every registered ore, block, and mob deposit.
2. **Explicit named zones** — arbitrary rectangles created with `/regions addprotectedarea <name> pos1 / pos2`. Useful for sealing trader huts, build stages, parkour courses, or any area a map author wants intact.

## Blocked inside protected areas

- Mining blocks
- Placing blocks
- Interacting with block entities (chests, furnaces)
- Damaging entities marked as protected (traders)

## Explicit exceptions

- **Mining an ore or block deposit is allowed inside any protected area.** Ore deposits convert to cobblestone and start their regen timer; block deposits break fully (drop their item, leave air) and start their regen timer. A named protected area drawn across a cluster of deposits leaves those deposits fully functional — every other block in the zone is unmineable, but the deposits mine and regenerate normally.
- Breaking the ocean conduit is allowed. Each break removes 1 HP and respawns the block.
- Feeding fuel to the nether furnace is allowed.
- Right-clicking the plains composter with a crop is allowed.

## Unprotected areas

Anywhere not covered by an implicit or explicit protected area is free to mine, place, and interact with normally. This is the default for most of the map.

## Visualizing protected areas

Run `/regions debug show protected` to toggle a red particle outline around every protected area — helpful when laying out deposit clusters or verifying that a named zone covers what you intended.

## Editing the map after registration

Toggle build mode with `/regions debug buildmode on` to disable every block protection (named areas, deposits, chamber, lifelines) for yourself only. Other players still see normal protections. Run `/regions debug buildmode off` to re-enable them. This is the intended way to fix a stray click without re-creating registrations.
