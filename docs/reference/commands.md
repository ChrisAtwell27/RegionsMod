# Commands

All commands are operator-only. Exact argument syntax is finalized in the implementation spec.

## Setup

| Command | Effect |
| --- | --- |
| `/regions setlobby` | Mark current location as lobby spawn |
| `/regions setspawn <biome>` | Mark current location as a team spawn |
| `/regions setbiomebounds <biome>` | Define biome territory |
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
