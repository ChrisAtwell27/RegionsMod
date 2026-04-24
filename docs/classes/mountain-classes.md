# Mountain Classes

Six classes. Adapted from Shotbow Annihilation to RegionsMOBA mechanics. Notable RegionsMOBA-specific changes are flagged **[tweak]**.

| # | Name | Weapon | Key Ability |
| --- | --- | --- | --- |
| 1 | Builder | Wooden tools | Resource Drop + Replication Cache |
| 2 | Warrior | Wooden sword | Frenzy |
| 3 | Berserker | Stone sword | Max-HP scaling + Unbreakable Will |
| 4 | Miner | Stone pickaxe (Eff I / Unb I) | Gold Rush |
| 5 | Tinkerer | Stone sword | PowerPads + Disenchanting |
| 6 | Acrobat | Bow + 6 arrows | Double jump + fall immunity |

---

## Builder

Spawns with a full set of wooden tools, a book titled **Resource Drop**, and a composter called **Replication Cache**.

### Resource Drop

Right-click to open a chest menu containing a random mix of blocks. **90-second cooldown.**

| Material | Max Amount |
| --- | --- |
| Spruce Planks **[tweak: was Oak]** | 70 |
| Cobblestone **[tweak: was Dirt]** | 60 |
| Stone | 50 |
| Stone Bricks **[tweak: was Bricks]** | 40 |
| Wool | 30 |
| Glass | 20 |
| Spruce Stairs | 20 |
| Spruce Fence | 10 |
| Iron Bars | 10 |
| Torch | 5 |

**[tweak]** The Annihilation "Delaying Block" is removed — no equivalent exists in RegionsMOBA.

### Replication Cache

Place on the ground to start replicating blocks. Left-click with a block to insert it.

Replicable blocks: Glass (all colors), Planks (all wood types), Stone, Stone Bricks, Wool (all colors), Cobblestone, Dirt, Grass Block, Concrete (all colors), Terracotta, Stained Glass (all colors), Deepslate **[tweak]**.

**[tweak]** Bricks removed from the replication list (not found in mountain biome); Deepslate added so Mountain defenders can wall with regional stone.

| Action | Result |
| --- | --- |
| Left-click | View items inside |
| Shift + Left-click | Replace current item (deletes all replicated items) |
| Right-click | Collect a stack of replicated items |
| Shift + Right-click | Collect all replicated items (excess sent to stash for 60 seconds) |

Produces 10 blocks every 3 seconds, max 960 blocks held (15 stacks). When broken, incurs a 45-second cooldown.

### Passive

The Builder gets 2 XP for about every 1.5 seconds of continuous block placement.

---

## Warrior

Spawns with a wooden sword, a Potion of Instant Health I, and blaze powder labelled **Frenzy**. The Warrior deals +1 melee damage passively.

### Frenzy

Right-click to activate for **12 seconds**. **60-second cooldown.**

- +1 additional melee damage (stacks with the passive, +2 total).
- Speed I for the duration.
- Heals 2 HP every 4 seconds, up to 6 HP total.
- Takes 25% more damage while active.

**[tweak]** Frenzy can only be activated during a Cold Season (when PVP is on) or after PVP has gone permanent. Triggering outside a PVP window consumes the cooldown but only grants the Speed I and heal portions (no damage bonus, no damage penalty) — lets the Warrior still use it for rotations without being useless in peace phases.

**[tweak]** Killing any enemy during Frenzy reduces the remaining cooldown by 10 seconds on expiry. Supports the Blood Tribute loop.

---

## Berserker

Spawns with a stone sword and a Netherite Ingot called **Unbreakable Will**.

### Heart Stacking

- Killing enemies grants +1 max heart, up to 15 hearts total.
- From 15 → 20, only melee kills count, and each kill grants +½ heart (10 melee kills to cap).
- Dying removes 5 hearts (25 HP).
- Hearts persist across class change and disconnection. Dying on a non-Berserker class still removes 5 Berserker hearts.

**[tweak]** Enemy deaths *inside the mountain biome* (any cause, any team) count as half-heart kills toward the Berserker's pool, mirroring the Blood Tribute satisfaction rule. This aligns the class with Mountain's lifeline rather than rewarding pure solo roaming.

**[tweak]** Because players only have 3 lives, repeated death-at-cap is self-limiting. No extra cap adjustment needed.

### Unbreakable Will

Right-click the ingot.

- Grants 20 seconds of Speed I.
- Full immunity to knockback and movement-impairing effects (Slowness, Levitation, Slow Falling, Blindness, Nausea) for the duration.
- **65-second cooldown.**

### Armor-Difference Damage

The Berserker deals more damage to better-armored enemies.

```text
difference = victimArmorValue - attackerArmorValue   (both clamped to 20)
finalDamage = damageDealt + (damageDealt * difference / 200.0)
```

`damageDealt` is Minecraft's post-modifier damage (potions, enchantments included).

---

## Miner

Spawns with an Efficiency I / Unbreaking I stone pickaxe called **Miner's Passion**, and a gold nugget called **Gold Rush**.

### Double-Ore Passive

Every ore mined has a 67% chance to yield two.

- Affected ores: Iron, Gold, Coal, Diamond. **[tweak: Emerald removed — Plains-exclusive resource in RegionsMOBA.]**

### Gold Rush

Right-click to activate. Lasts **10 seconds**. **60-second cooldown.**

- Ore drops boosted to 100% double, with a 67% chance of triple.
- 20% chance per ore for 1 bonus coal.
- **[tweak]** Mountain ore deposits ignore the Cold Season cooldown increase while Gold Rush is active. This keeps the class playable during the cold-season deposit squeeze — otherwise Gold Rush lines up with exactly the worst mining window.

### Blast Furnace Drops

- The first ore mined drops a Blast Furnace.
- Every 16 ores received (not mined) drops another Blast Furnace.

---

## Tinkerer

Spawns with a stone sword, a Speed I PowerPad, a Haste I PowerPad, and 10 undroppable books.

### PowerPads

Place a PowerPad block; a pressure plate appears on top. Stepping on it (self or teammate) grants a buff.

| Block | Effect | Duration |
| --- | --- | --- |
| Redstone Block | Speed I | 45 s |
| Coal Block | Haste I | 45 s |
| Diamond Block | Speed II | 20 s |
| Gold Block | Haste II | 15 s |
| Deepslate Block **[tweak: was Emerald]** | Absorption I | 20 s |

**[tweak]** Emerald PowerPad replaced with Deepslate. Emeralds are a Plains-only resource in RegionsMOBA; locking the best defensive pad behind an enemy team's economy made the class unreliable. Deepslate is mountain-native, so the Absorption pad now obtains from deep mining.

- Spawn with Redstone and Coal pads only. The other three are crafted from their block material as you mine.
- Stepping on a pad refreshes its buff to full duration.
- A higher-level buff is not replaced by a lower-level pad of the same family until it expires.
- You get XP each time a teammate receives a new buff from one of your pads.
- Pads last until broken. When broken by an enemy, the block fragments into 8 items (4 to the owner, 4 to the breaker).

### Disenchanting

The 10 spawn books disenchant non-armor items at full durability. Place the enchanted item and one book anywhere in the crafting grid; the enchantment moves to the book and can be transferred via anvil at XP cost.

**[tweak]** Armor disenchanting remains excluded — same rule as Annihilation.

---

## Acrobat

Spawns with wooden tools, a bow, and 6 arrows.

### Double Jump

Double-tap jump in mid-air to leap upward.

- Reaches up to 6 blocks above the start point.
- **10-second cooldown.**

### Fall Immunity

Takes no fall damage at any height.

### Stamina Passive

Hunger cannot drop below **3.5 bars** (7 half-units). Hunger damage is therefore impossible, and sprint is always available.

**[tweak]** The hunger floor explicitly **counters Plains Farmer's Famine ability** (Hunger 20 → drain to 2.5 bars). On an Acrobat, Famine bottoms out at 3.5 bars instead. This cross-class interaction is intentional — the class picks up a clean hunger-debuff counter that neither side has to configure for.

**[tweak]** No PVP gating — all Acrobat abilities are self-mobility and work in every phase.
