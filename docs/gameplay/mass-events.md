# Mass Events

When a team permanently loses half of its starting members (rounded down), a one-time mass event fires for that team's region to give them an edge.

A player is "permanently lost" once they are out of lives and locked into spectator mode. Ocean players who die with the conduit at 0 HP also count.

Each team's event fires at most once per match. Event entities can be killed with enough effort.

## Summary

| Team | Event | Trigger |
| --- | --- | --- |
| Plains | 3 independent Pillager patrol squads roam the biome, attacking non-plains players | Half of plains permanently lost |
| Mountain | A Warden spawns at a preset point, ignores dwarves, 100 HP | Half of mountain permanently lost |
| Ocean | Stationary Elder Guardians spawn at preset points, default HP | Half of ocean permanently lost |
| Nether | A stationary Wither spawns at a preset point, shoots skulls at non-nether players, default HP | Half of nether permanently lost |

## Plains — Pillager patrols

Three Pillager squads spawn inside the plains biome and patrol independently. Each squad targets any player who is not on the plains team.

Squads are not stationary. They path around the biome and engage on sight. Plains players are ignored.

No spawn command — squads spawn and path within the registered plains biome bounds.

## Mountain — Warden

A single Warden spawns at a preset point inside the mountain biome.

- HP: 100 (reduced from vanilla)
- Never aggros mountain (dwarf) players
- Attacks any non-mountain player in range using standard Warden behavior

Spawn point is set with `/regions setwardenspawn`.

## Ocean — Elder Guardian turrets

One or more Elder Guardians spawn at preset points inside the ocean biome. Each Elder Guardian is stationary — it cannot move from its spawn point — but it can attack any non-ocean player in range.

- HP: default (vanilla Elder Guardian)
- Cannot path or reposition
- Attacks normally (laser beam, thorns aura)

Spawn points are set with `/regions setguardianspawn <index>`, where `<index>` selects which guardian slot is being placed. Any number of guardian slots may be registered.

## Nether — Wither turret

A single stationary Wither spawns at a preset point inside the nether biome. It cannot move but fires wither skulls at any player who is not on the nether team.

- HP: default (vanilla Wither)
- Cannot path or reposition
- Only targets non-nether players

Spawn point is set with `/regions setwitherspawn`.
