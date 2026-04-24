# Commands

All commands are operator-only. Exact argument syntax is finalized in the implementation spec.

## Area commands

Run at each corner. `pos1` is the high corner (max X, Y, Z). `pos2` is the low corner (min X, Y, Z). The area is the cuboid between them.

| Command | Effect |
| --- | --- |
| `/regions setlobby pos1` | Mark lobby high corner |
| `/regions setlobby pos2` | Mark lobby low corner |
| `/regions setbiomebounds <biome> pos1` | Mark biome high corner |
| `/regions setbiomebounds <biome> pos2` | Mark biome low corner |

## Protected area commands

Named rectangular zones where block-mining is blocked for every block **except registered ore deposits**. Deposits inside a protected area continue to function normally — mined, cobbled, and regenerated on schedule. See [Protected Areas](protected-areas.md).

| Command | Effect |
| --- | --- |
| `/regions addprotectedarea <name> pos1` | Mark high corner of a new named protected area |
| `/regions addprotectedarea <name> pos2` | Mark low corner; area is created once both corners are set |
| `/regions removeprotectedarea <name>` | Delete a named protected area |
| `/regions listprotectedareas` | Print all registered protected areas with their bounds |

## Trial chamber commands

A single fixed arena at the center of the map. Block-mining is blocked everywhere inside the bounds. See [Trial Chamber](../gameplay/trial-chamber.md).

| Command | Effect |
| --- | --- |
| `/regions setchamberbounds pos1` | Mark trial chamber high corner |
| `/regions setchamberbounds pos2` | Mark trial chamber low corner |
| `/regions addchamberspawn` | Register the current position as a mob spawn point for trial waves. Returns the new index. |
| `/regions removechamberspawn <index>` | Remove a mob spawn point by index |
| `/regions listchamberspawns` | Print all registered chamber mob spawn points with their positions |

## Target commands

Run the command, then right-click the intended block or entity. The next right-click registers the target.

| Command | Effect |
| --- | --- |
| `/regions setconduit` | Register the next right-clicked block as the ocean conduit |
| `/regions setfurnace` | Register the next right-clicked block as the nether furnace |
| `/regions setcomposter` | Register the next right-clicked block as the plains composter |
| `/regions settrader <biome>` | Register the next right-clicked entity as a biome's trader |
| `/regions addoredeposit <time>` | Register the next right-clicked ore block as a deposit with regen time. Ore type inferred from the block. |
| `/regions addmobdeposit <mob> <time>` | Register the next right-clicked block as a mob spawn point with spawn interval |

## Position commands

Run at the target position.

| Command | Effect |
| --- | --- |
| `/regions setspawn <biome>` | Mark current position as a team spawn |
| `/regions setwardenspawn` | Mark current position as the mountain mass-event Warden spawn |
| `/regions setguardianspawn <index>` | Mark current position as an ocean mass-event Elder Guardian spawn slot. Any number of slots may be registered. |
| `/regions setwitherspawn` | Mark current position as the nether mass-event Wither spawn |

## Match control

| Command | Effect |
| --- | --- |
| `/regions start` | Start a match manually |
| `/regions abort` | End the current match |
| `/regions skip-season` | Force next phase change |
| `/regions status` | Print current match state |
| `/regions version` | Print mod version |

## Debug commands

Grouped under `/regions debug <...>`. Operator-only and intended for development, map testing, and live troubleshooting. Avoid using in scored matches.

### Visualization

Toggles particle overlays for registered zones and objects. Toggling a second time turns the overlay off.

| Command | Effect |
| --- | --- |
| `/regions debug show protected` | Outline every protected area in red particles |
| `/regions debug show deposits` | Highlight ore and mob deposits, with regen timer above each |
| `/regions debug show spawns` | Mark all team spawn points |
| `/regions debug show biomes` | Outline biome bounds in team colors |
| `/regions debug show lifelines` | Mark the Conduit, Furnace, and Composter with their current state |
| `/regions debug show lobby` | Outline the lobby area |
| `/regions debug show all` | Enable every overlay at once |
| `/regions debug show off` | Turn every overlay off |

### Match state

| Command | Effect |
| --- | --- |
| `/regions debug season <cold\|warm>` | Force the current season to cold or warm without changing the timer |
| `/regions debug pvp <on\|off\|permanent>` | Force the PVP flag state |
| `/regions debug seasontimer <seconds>` | Set the remaining seconds on the current season timer |
| `/regions debug bloodtribute <satisfy\|reset\|fail>` | Satisfy, reset, or fail the current Mountain Blood Tribute |
| `/regions debug state` | Dump full match state (season, timers, lifelines, player counts) to the command sender |
| `/regions debug reload` | Re-read configuration files without restarting the server |

### Lifelines

| Command | Effect |
| --- | --- |
| `/regions debug conduit hp <0-200>` | Set Ocean Conduit HP |
| `/regions debug furnace <lit\|unlit>` | Force Nether Furnace state |
| `/regions debug furnace fuel <ticks>` | Set remaining fuel ticks in the Nether Furnace |
| `/regions debug composter uses <count>` | Set emerald-conversion count since match start (for economy debugging) |

### Deposits

| Command | Effect |
| --- | --- |
| `/regions debug deposits regen` | Instantly regenerate every ore deposit |
| `/regions debug deposits reset` | Reset every ore and mob deposit to its starting state and clear cooldowns |
| `/regions debug deposit info` | Print info on the next right-clicked deposit block (type, cooldown, regen time) |

### Players

| Command | Effect |
| --- | --- |
| `/regions debug team <player> <biome>` | Force-assign a player to a biome team |
| `/regions debug class <player> <class>` | Force a player's class (grants kit, clears previous class items) |
| `/regions debug lives <player> <count>` | Set a player's remaining lives |
| `/regions debug cooldowns <player> clear` | Clear every class-ability cooldown on a player |
| `/regions debug kit <player>` | Re-grant the player's current class kit |
| `/regions debug tp <player> <biome\|lobby>` | Teleport a player to a biome spawn or the lobby |
| `/regions debug spectate <player>` | Force a player into spectator state |

### Entities

| Command | Effect |
| --- | --- |
| `/regions debug trader respawn <biome>` | Respawn the biome trader at its registered location |
| `/regions debug mob kill <radius>` | Kill every mod-spawned mob in radius (does not touch vanilla mobs) |
