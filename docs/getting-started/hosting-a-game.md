# Hosting a Game

A match needs a map with four biome regions, configured areas, and a `[Nations]` sign.

## Build the map

One world with four biome regions (plains, mountain, nether, ocean) and a lobby area. Each biome needs room for a team spawn, deposits, and a lifeline block where applicable.

## Register regions

All commands are operator-only. Most require standing near or looking at the target block.

```text
/regions setlobby
/regions setspawn <biome>
/regions setbiomebounds <biome>
/regions setconduit
/regions setfurnace
/regions setcomposter
/regions settrader <biome>
/regions addoredeposit <ore> <time>
/regions addmobdeposit <mob> <time>
```

All registered areas are mine and place protected.

## Place a [Nations] sign

Place a sign outside the lobby with:

```text
Row 1: [Nations]
Row 2: <min_players>
```

Row 1 must be `[Nations]`. Row 2 must be a positive integer and a multiple of 4.

Players right-click to join. When the join count reaches the minimum, all joiners teleport to the lobby and the match begins.

## Admin commands during a match

| Command | Effect |
| --- | --- |
| `/regions abort` | End the match |
| `/regions skip-season` | Force next phase change |
| `/regions status` | Print match state |
