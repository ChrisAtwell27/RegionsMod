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

## Match control

| Command | Effect |
| --- | --- |
| `/regions start` | Start a match manually |
| `/regions abort` | End the current match |
| `/regions skip-season` | Force next phase change |
| `/regions status` | Print current match state |
| `/regions version` | Print mod version |
