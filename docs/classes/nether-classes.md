# Nether Classes

Six classes. Adapted from Shotbow Annihilation to RegionsMod mechanics. Notable RegionsMod-specific changes are flagged **[tweak]**.

| # | Name | Weapon | Key Ability |
| --- | --- | --- | --- |
| 1 | Alchemist | Wooden tools + leather armor | Brewing Stand + Tome + Enhanced Potions |
| 2 | Enchanter | Golden sword + wooden tools | Intensifier + XP-damage shield |
| 3 | Bloodmage | Stone sword | Corrupt + Bloodcursed Terraform |
| 4 | Wizard | Wand + Spellbook | 5 spells on a shared cooldown |
| 5 | Vampire | Stone sword | Blood Sense + Insidious Dispatch + HP steal |
| 6 | Rift Walker | Basic tools + Blaze Rod | Group teleport |

**[tweak]** Annihilation XP/rank unlock gates are removed for every class — all five are available at class-select.

**[tweak]** Throughout this file, "nexus" placement restrictions are remapped to **lifeline blocks** — Ocean Conduit, Nether Furnace, Plains Composter. Mountain has no lifeline block.

**[tweak]** All Nether players have permanent Fire Resistance (team passive). Several class interactions below reference this — most notably Wizard's Inferno spell, which is useless against a Nether target.

---

## Alchemist

Spawns with wooden tools, a full set of leather armor, an **Alchemist's Brewing Stand**, and an **Alchemist's Tome**.

### Alchemist's Brewing Stand

- Place to use as a private brewing stand — only the Alchemist can open it.
- Brewing time is halved in this stand.
- Enemies break it instantly with left-click (no tool required).

### Alchemist's Tome

Right-click to receive a random potion ingredient. **90-second cooldown.** Left-click to read the Enhanced Potion recipes.

| Tier | Chance | Items |
| --- | --- | --- |
| Very Common | 32% | Nether Wart, Fermented Spider Eye |
| Common | 28% | Glistering Melon, Golden Carrot, Sugar, Spider Eye, Magma Cream |
| Uncommon | 15% | Glowstone Dust |
| Rare | 7% | Ghast Tear |
| Extremely Rare | 3% | Blaze Powder, Gunpowder **[tweak: post-permanent-PVP only]** |
| Junk | 15% | Rotten Flesh, Poisonous Potato, Snowball, String |

**[tweak]** Annihilation's "Phase 4+ only" gate on Gunpowder is remapped to **"after PVP has gone permanent"** (i.e., after the second Cold Season). This preserves the late-game-only availability without referencing the phase system we don't have.

### Enhanced Brewing

Brew in a **water-filled cauldron** instead of a brewing stand.

- Enhanced potions are **class-bound** — the Alchemist cannot switch class before drinking / splashing the potion.
- Each brew consumes water from the cauldron; keep a water supply nearby.
- Teammates cannot pick up items the Alchemist has thrown into the cauldron. Enemies can.
- Sneak on the cauldron to retrieve thrown items.

#### Recipe basics

1. Throw in a **Dusted** potion (tier II potion, glowstone- or redstone-augmented).
2. Throw in its base ingredient (blaze powder, sugar, ghast tear, etc.).
3. Optional: throw in gunpowder to turn it into a splash potion (teammates only receive level II from splash).

**[tweak]** Enhanced Fire Resistance is redundant for Nether teammates (already permanent) but remains useful for allies from Nether who spread out, and retains value if the Alchemist switches factions in later mod scenarios. Not removed, just flagged.

---

## Enchanter

Spawns with a golden sword, a set of wooden tools, and a lapis lazuli called **Intensifier**.

### XP Passive

- Enchanter gains **2x XP** from all sources.
- When the Enchanter drops to 7 HP or lower, incoming damage consumes XP levels instead of health, up to the available XP pool.

### Intensifier

Right-click the lapis lazuli.

- Teammates within 10 blocks gain **1.25x XP** for 30 seconds.
- **120-second cooldown.**

### Enchantment Boost

When enchanting an item at a vanilla enchanting table, there's a 30% chance to increase one of the rolled enchantments by +1 level (capped at the vanilla maximum).

**[tweak]** No PVP gating — the Enchanter's kit is entirely economic/defensive. Fully usable in peace windows.

---

## Bloodmage

Spawns with a stone sword, a fermented spider eye called **Corrupt**, and a Crimson Hyphae block called **Bloodcursed Terraform**.

### Poison-on-Hit

25% chance on every melee hit to inflict Poison for 2 seconds (1 damage total).

### Corrupt

Right-click the fermented spider eye.

- All enemies within 4 blocks lose 2 hearts of max HP and are inflicted with Wither for 5 seconds (total 3 damage from Wither).
- **60-second cooldown.**

### Bloodcursed Terraform

Right-click the Crimson Hyphae to place the curse field. For 30 seconds, the surrounding 8-block radius is reskinned as nether blocks and inflicts enemies inside with:

| Effect | Level | Duration |
| --- | --- | --- |
| Wither | II | 5 s |
| Blindness | I | 10 s |
| Hunger | III | 10 s |

- Enemies already in the radius at placement are immune for 10 seconds (prevents flash-trap).
- **120-second cooldown.**
- **[tweak]** Cannot be placed within 35 blocks of any **lifeline block** — Ocean Conduit, Nether Furnace, or Plains Composter (including the friendly Nether Furnace, to prevent trap-stacking on your own lifeline). Mountain has no block lifeline, so there is no placement restriction inside the mountain biome itself.
- **[tweak]** All damage and debuff effects on enemies are naturally PVP-gated. Placing the curse field outside PVP still consumes the cooldown (it's a physical block placement); this is intentional to discourage pre-placing curse traps during peace.

---

## Wizard

Spawns with wooden tools, a **Wand**, and a **Spellbook**.

- Left-click the wand (or open the Spellbook) to choose a spell.
- Right-click the wand to cast.
- Spells are instantaneous on cast.
- **Global 15-second cooldown** after any cast (blocks all spells, not just the one used).

### Spells

| Spell | Cooldown | Radius | Effect |
| --- | --- | --- | --- |
| Inferno | 50 s | 1 block | Ignites enemies for 10 s; places a fire block at their feet. |
| Void Bolt | 50 s | 1 block | Wither II for 5 s. |
| Arcane Bolt | 30 s | 1 block | 7 damage AoE (reduced by armor). Counts as a melee kill. |
| Glacial Nova | 35 s | 2 blocks | Slowness III + Mining Fatigue I for 10 s. |
| Whirlwind | 25 s | 3 blocks | Knocks enemies and the Wizard away; 5 s of no fall damage. |

**[tweak]** Inferno is **completely ineffective against other Nether players** because of the team's permanent Fire Resistance. This is a known asymmetry, not a bug — Wizards mirror-matching Nether should pick a different spell.

**[tweak]** Arcane Bolt explicitly **counts as a melee kill** for the Mountain Berserker's 15→20 HP scaling (which normally requires melee-only kills). This cross-class interaction is preserved from Annihilation's intent.

**[tweak]** All offensive spells are naturally PVP-gated. Whirlwind's self-knockback and fall-damage immunity remain usable in peace for mobility.

---

## Vampire

Spawns with a stone sword, a Potion of Night Vision, a music disc called **Blood Sense**, and a black dye called **Insidious Dispatch**.

### Passive

- Deals 1.25x melee damage.
- During the **day** (Minecraft daytime), 15% chance on melee hit to steal 1 HP (heals self).
- During the **night**, the steal chance increases to 30%.
- On death, spawns a bat named after the Vampire for 8 seconds (flavor effect).

**[tweak]** Day/night is tracked by the vanilla world time, independent of Cold Season and PVP windows. Outside PVP, hits never land on enemies, so the steal is self-gated.

### Blood Sense

Right-click the music disc.

- Reveals all enemies within a 10-block horizontal / 8-block vertical radius to the Vampire only.
- Lasts 3 seconds. **3-second cooldown.**
- Does **not** reveal:
  - Invisible enemies (potion or class-based).
  - Vanished Plains Spies. **[tweak: Annihilation's generic "Spy" reference explicitly mapped to our Plains Spy class.]**
  - Enemies who enter the radius after the ability was cast.

### Insidious Dispatch

Right-click the black dye.

- Teleports the Vampire to directly behind a targeted enemy.
- **40-second cooldown.**
- 30-block max range.
- Requires line of sight.
- The target must **not** be facing the Vampire — ability silently fails if they are.
- **[tweak]** Naturally PVP-gated — outside a PVP window, the ability fails to lock on to a target and the cooldown is not consumed.

---

## Rift Walker

Spawns with basic tools and a **Blaze Rod** used to open rifts.

### Opening a Rift

Right-click the blaze rod to open a destination list:

- Every current Nether teammate (teleport directly to them).
- Each of the **three enemy region spawn markers** (Ocean, Plains, Mountain). **[tweak: Annihilation's "all enemy territories" is concretely mapped to the three enemy region biomes.]**
- The **Nether team spawn**. **[tweak: Annihilation's "bed for your base" is remapped to the Nether spawn point — we don't use beds as anchors.]**

Selecting a destination starts a **10-second countdown**. Green rift particles appear around the Rift Walker; this is the rift area.

### Countdown and Travel

- Leaving the rift area during the countdown cancels the rift.
- Left-click the blaze rod during the countdown to cancel.
- At countdown end, the Rift Walker teleports. Any teammate **sneaking inside the rift area at the moment of travel** is pulled along.
- After a successful teleport, every traveller (including the Rift Walker) gets **Weakness II for 5 seconds**.

### Teleport Targeting Rules

A teammate **cannot be targeted as a destination** if they are:

- Within range of any **enemy lifeline block** — Ocean Conduit, Nether Furnace, or Plains Composter. **[tweak: Annihilation's "near any nexus" rule remapped. Mountain has no block lifeline, so targeting a teammate inside the mountain biome is always allowed.]**
- Invisible (potion-based).
- Vanished (Plains Spy class ability). **[tweak: explicit cross-class reference.]**

### Cooldown

After a successful teleport: **30 seconds × number of players transported** (including the Rift Walker).

- Maximum party size: **4 players** (Rift Walker + 3 teammates).
- Cancelled rifts do not consume the cooldown.

**[tweak]** No PVP gating — rifting is mobility/utility. Teleporting into an enemy biome during peace is legal but arguably pointless; the kit shines as a Cold Season opener.
