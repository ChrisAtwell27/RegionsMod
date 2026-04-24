# Signs

## [Nations] sign

Place a sign anywhere outside the lobby. Format:

```text
Row 1: [Nations]
Row 2: <min_players>
```

Rules:

- Row 1 must be `[Nations]` (case-insensitive).
- Row 2 must be a positive integer and a multiple of 4.

Once placed, the sign becomes a live join board showing the current join count.

Players right-click to join. Right-click again to leave before the match starts.

When the join count reaches the minimum, all joiners teleport to the lobby and the match begins.

## Invalid signs

If row 2 is not a positive multiple of 4, the sign is rejected. A chat message tells the placer the sign was not accepted.
