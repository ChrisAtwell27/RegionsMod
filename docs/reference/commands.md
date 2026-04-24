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

## Point commands

Run at the target location or while looking at the target block.

| Command | Effect |
| --- | --- |
| `/regions setspawn <biome>` | Mark current location as a team spawn |
| `/regions setconduit` | Mark ocean's conduit nexus |
| `/regions setfurnace` | Mark nether's lifeline furnace |
| `/regions setcomposter` | Mark plains composter |
| `/regions settrader <biome>` | Mark a biome's trader |
| `/regions addoredeposit <ore> <time>` | Register an ore deposit with regen time |
| `/regions addmobdeposit <mob> <time>` | Register a mob deposit with spawn interval |

## Match control

| Command | Effect |
| --- | --- |
| `/regions start` | Start a match manually |
| `/regions abort` | End the current match |
| `/regions skip-season` | Force next phase change |
| `/regions status` | Print current match state |
| `/regions version` | Print mod version |
