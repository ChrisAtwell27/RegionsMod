# Timeline

Matches run at least 60 minutes. Alternating 15-minute warm and cold phases.

## Phases

| Time | Phase | PVP |
| --- | --- | --- |
| 0:00–15:00 | Warm 1 | Off |
| 15:00–30:00 | Cold 1 | On |
| 30:00–45:00 | Warm 2 | Off |
| 45:00–60:00 | Cold 2 | On |
| 60:00+ | Alternating | Permanent On |

After Cold 2 ends, PVP stays on permanently. Cold season environmental effects keep cycling every 15 minutes.

PVP is always on inside the [Trial Chamber](trial-chamber.md) regardless of phase.

## Lives

Every player starts with 3 lives. Dying respawns you at team spawn with -1 life. At 0 lives, spectator mode for the rest of the match.

If the ocean conduit reaches 0 HP, the next death for every ocean player is final regardless of lives remaining.

## Match end and map repair

When a match ends — whether the timer-driven end condition fires or an operator runs `/regions abort` — the map fully repairs itself.

**Scope.** Repair covers every dimension referenced by a registration. A registration in the overworld pulls in the overworld; a Nether biome bounds in `the_nether` pulls that in too. Cross-dimension setups (overworld lobby + nether-biome territory + an end trial chamber, etc.) are fully supported — each match dimension is snapshotted and repaired independently. Dimensions with no registrations are untouched.

**Snapshot.** Block changes are recorded against a per-dimension delta starting at `/regions start`. On match end, each match dimension is restored to its t=0 state. This includes:

- Player-broken blocks (including non-deposit terrain mined during PVP phases).
- Player-placed blocks (forts, towers, bridges).
- Mob-driven damage — creeper holes, Wither skull blasts, Warden sonic destruction, Pillager fire.
- Block-entity contents — chests, hoppers, signs, banners, and anything else with stored NBT come back exactly as they were before the first mutation.
- Mass-event debris and spawn traces.
- Deposit cooldown states — every ore deposit reverts to its registered ore, every block deposit to its registered block, regardless of cooldown.

**Entities and items.** Every mod-spawned entity (mass-event mobs, mob-deposit spawns, trial-wave mobs) is despawned at match end across every match dimension. Every dropped item in those dimensions is also cleared. Vanilla wandering mobs are left alone. Traders are re-spawned at their registered points.

**Implications for map authors.** Match dimensions are disposable — anything that needs to survive across matches (server hub, operator build areas, persistent storage) must live in a dimension with no registrations. Any dimension with a single registered block, position, or area is in scope for repair.

To verify the repair behaves as expected without ending a match, run `/regions debug map repair`.
