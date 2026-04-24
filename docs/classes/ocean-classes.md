# Ocean Classes

Six classes. Adapted from Shotbow Annihilation to RegionsMOBA mechanics. Notable RegionsMOBA-specific changes are flagged **[tweak]**.

| # | Name | Weapon | Key Ability |
| --- | --- | --- | --- |
| 1 | Defender | Wooden tools + chain chestplate | Guardian's Warp + Conduit HP scaling |
| 2 | Immobilizer | — | Single-target stun + AoE slow |
| 3 | Neptune | Stone sword + trident | Ground Freeze + Tidebringer |
| 4 | Siren | — | Execute + life drain |
| 5 | Healer | Blood bag | Team heal + cleanse |
| 6 | Transporter | Wooden tools + Nether Quartz | Linked portals |

**[tweak]** Annihilation XP/rank unlock gates are removed for every class — all five are available at class-select.

**[tweak]** Throughout this file, "Nexus" is replaced with **Conduit** (Ocean's lifeline block).

---

## Defender

Spawns with a set of wooden tools, a chain chestplate, a lime dye named **Guardian's Warp**, and a prismarine shard named **Alert Item**.

### Conduit Regen

While within 50 blocks of the friendly Conduit, the Defender has Regeneration I.

### Conduit HP Scaling

**[tweak]** The Defender's bonus max HP scales with missing Conduit HP:

```text
bonusHearts = floor( (200 - conduitHP) / 20 )     // capped at +10 hearts (+20 HP)
```

- At full Conduit (200 HP) → no bonus.
- At half Conduit (100 HP) → +5 hearts.
- At 0 Conduit (the "next death is final" threshold) → +10 hearts.
- Only applies while within the 50-block Conduit radius.

**[tweak]** Because RegionsMOBA's Conduit only loses HP (never repairs), the Defender's max HP is monotonically non-decreasing during a match. This is intentional as a pure Ocean comeback mechanic; Annihilation's "Handyman can heal your nexus and shrink your Defender" concern doesn't apply here.

### Guardian's Warp

Right-click the lime dye to teleport to a safe point near the Conduit. **20-second cooldown.**

### Alert Item

Right-click the prismarine shard to place it at your feet.

- Plays an alert sound to all Ocean teammates if an enemy picks it up.
- An enemy holding an item in their off-hand or main hand cannot pick it up.

---

## Immobilizer

Spawns with no unique weapon — standard starter kit only.

### Right-Click: Single-Target Stun

Point at an enemy within 5 blocks and right-click.

- Both the Immobilizer and the target get **Slowness XI + negative Jump Boost** (full movement lock) for 2–5 seconds, scaled with the target's armor.
- Target additionally gets **Mining Fatigue II + Absorption II** for the same duration.
- During the stun, both players can drink potions, throw potions, or eat food.
- The target cannot be re-immobilized for 15 seconds after release.

### Left-Click: AoE Slow

Left-click to apply **Slowness III** for 5 seconds to all enemies within 5 blocks.

### Cooldown

**30 seconds per use** (shared between right-click and left-click).

**[tweak]** Right-click stun can only be triggered during a PVP window. Outside PVP the cooldown does not start, and the ability simply fails with a "Not in combat phase" message. (The stun is a hostile effect; firing it in peace has no meaningful target.)

---

## Neptune

Spawns with a stone sword, basic tools, a block of ice called **Ground Freeze**, a trident called **Tidebringer**, and 10 lily pads.

### Aquatic Passive

Cannot take drowning damage in water.

### Ground Freeze

Right-click the ice block to toggle.

- While toggled on, walking over water or lava freezes it up to 8 blocks ahead (11×7 area), or up to 6 blocks diagonally.
- Does not trigger while the player is submerged — must be walking above the surface.
- **[tweak]** Ground Freeze is explicitly compatible with Cold Season. Ocean's water freezes environmentally during Cold Season; Neptune's active freeze overrides in-biome heat sources so the player can still cut paths through their team's own water with a heat-touched furnace nearby.

### Tidebringer

Right-click to toggle between two trident modes.

**Riptide** — Launches the player through the air while in water or during rain. **20-second cooldown.**

**Curse of the Sea** — Thrown at an enemy: on hit, inflicts drowning damage over 8 seconds (ignored if the target is already underwater). Returns on hit or miss. **20-second cooldown.**

**[tweak]** Curse of the Sea damage is naturally PVP-gated; an out-of-combat hit registers the return animation but deals no damage over time.

---

## Siren

**[tweak]** Renamed from Annihilation's "Succubus" at user request.

Spawns with a red dye as the cooldown indicator and standard starter tools.

### See Enemy Health

At all times, the Siren sees each enemy player's current HP as a hearts bar above their head.

### Drain (Right-Click)

Right-click an enemy within line of sight.

- If the target is at **30% HP or less**, they die instantly and the remaining HP is transferred to the Siren as healing.
- If the target is above 30% HP, the Siren takes the shortfall as **true damage** (armor and resistance ignored).
- The 30% threshold is relative to the target's current max HP, not the vanilla 20 HP bar — e.g., a Mountain Berserker at 40 HP max triggers the execute at 12 HP or lower.
- Requires line of sight.
- **60-second cooldown.** The red dye's durability bar shows time remaining.

**[tweak]** The Annihilation reference to killing "through Enchanter levels" is dropped — no Enchanter class exists in RegionsMOBA. The Berserker max-HP example is kept because that class exists on Mountain.

**[tweak]** Drain can only fire during a PVP window. Outside PVP, right-click does nothing and the cooldown is not consumed.

---

## Healer

Spawns with a blood bag and the ability to see teammates' health bars.

### Right-Click: Team Regen

- Grants **Regeneration III** for 3 seconds to up to 3 of the lowest-HP allies within 6 blocks.
- Other Healers in range only receive 1.5 seconds of Regeneration III from this use.
- **15-second cooldown.**

### Left-Click: Focused Heal

- Heals one targeted teammate for **15 HP** (7.5 hearts).
- Cleanses all negative potion effects from both the Healer and the target.
- **45-second cooldown.**

### Healer XP

Healing another player awards the Healer 2 XP per player healed.

**[tweak]** No PVP gating needed — Healer abilities are friendly-only. They are fully usable during peacetime for topping off teammates (e.g., after mob encounters or cold-water damage).

---

## Transporter

Spawns with wooden tools and a piece of **Nether Quartz** used to create portal pairs.

### Creating a Portal

Right-click the Nether Quartz on a block to convert it to nether quartz ore — this is the first portal block. Right-click a second block to create its pair; both begin emitting white smoke.

- Only **one portal pair** can be active at a time.
- Cannot place underwater.
- Cannot place if there's a block within 2 blocks directly above the portal block.
- **[tweak]** Portals cannot be placed inside any [Protected Area](../reference/protected-areas.md). This rule is preserved from Annihilation but explicitly remapped to our protected-areas system.

### Using a Portal

- Sneak over a portal block to travel to its partner.
- **2-second cooldown per portal** (on the block, not the player).
- Enemies cannot use the portal.
- Every time you or a teammate travels through your portal, you gain 1 XP.

### Breaking a Portal

- Right-click the Quartz on an existing portal block to dismantle it.
- Create a new first portal to replace an existing pair.
- Mine one of the portal blocks.
- **Enemies can break portals** (teammates cannot).
- When a portal is broken by an enemy, you are alerted in chat and by an audio cue.
- **[tweak]** If a regenerating **ore deposit** (see [Deposits](../gameplay/deposits.md)) would spawn blocks overlapping a portal, the portal is instantly destroyed. This replaces Annihilation's "if a resource regenerates in the same place" rule with our deposit system.

### Persistence and Death

- A completed portal pair lasts forever — dying does not break it.
- However, if only **one** side has been placed and the Transporter dies before placing the second, the unpaired block is destroyed on death.

### Naming

- Run `/portal <name>` to name the portal. The name shows as a hologram above the block and when standing on it.

**[tweak]** No PVP gating — the kit is traversal/utility. Portals can be laid during peace for Cold Season preparation.
