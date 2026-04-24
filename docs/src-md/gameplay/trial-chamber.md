# Trial Chamber

A fixed arena at the center of the map where all four biomes meet. Every 10 minutes, trial spawners activate a large enemy wave. The team with the most kills in the wave earns the loot.

## Location

Center of the map, spanning the intersection of all four biomes. Exact bounds are set by an operator with the chamber commands.

## Waves

Waves fire on a 10-minute cadence.

- Every registered mob spawn point releases enemies at the start of a wave.
- Kills are tracked per team for the duration of the wave.
- The wave ends when every spawned enemy is dead or despawned.

## Scoring

At the end of a wave, the team with the most enemy kills wins the loot. Ties split the loot evenly between tied teams.

## Loot

Loot varies per wave. Typical drops:

- Long-duration (10-minute) potion buffs — Strength, Speed, and similar beneficial effects.
- Emeralds.
- Other valuables from a rotating drop table.

Potion buffs are applied to every living member of the winning team. Item drops appear inside the chamber.

## Block protection

No blocks can be broken inside the chamber bounds. This applies to every team, including operators during a scored match. Trial spawners, structural blocks, and any cosmetic blocks in the chamber are all unbreakable.

This protection is stricter than a named [Protected Area](../reference/protected-areas.md): deposits do not function inside the chamber.

## Setup

Chamber bounds and mob spawn points are registered via operator commands. See [Commands](../reference/commands.md).
