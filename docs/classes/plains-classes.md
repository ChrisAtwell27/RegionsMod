# Plains Classes

Six classes. Adapted from Shotbow Annihilation to RegionsMod mechanics. Notable RegionsMod-specific changes are flagged **[tweak]**.

| # | Name | Weapon | Key Ability |
| --- | --- | --- | --- |
| 1 | Archer | Punch I bow | Rain of Arrows + Poison Shot |
| 2 | Spy | Golden sword | Vanish + Flee clone |
| 3 | Farmer | Wooden tools + stone hoe | Feast + Famine |
| 4 | Bard | Wooden tools | Buffbox (4 songs) |
| 5 | Lumberjack | Efficiency I stone axe | Brute Force |
| 6 | Scout | Golden sword + Grapple | Grapple hook traversal |

**[tweak]** All Annihilation rank/XP unlock gates (Novice-II, 15,000 XP, etc.) are removed — every class is available at class-select in RegionsMod.

---

## Archer

Spawns with a Punch I bow, the **Arrow of Infinity**, and a Potion of Instant Health I.

### Passive

- +1 base damage with any bow.
- Never runs out of arrows while the Arrow of Infinity is in inventory, regardless of which bow is held.
- Can craft arrows without a feather — yields 3 arrows per craft instead of the usual 4.

### Bow Specials

Left-click any bow to cycle between two special attacks. Firing a special consumes only 1 arrow.

**Rain of Arrows** (one left-click)

- A circle of particles shows where the volley will land.
- On firing, a ring of arrows drops inside that circle.
- **30-second cooldown.**

**Poison Shot** (two left-clicks)

- On impact, spawns a lingering poison cloud that poisons enemies walking through it.
- **50-second cooldown.**

**[tweak]** Damage from both specials is naturally gated by PVP windows (Cold Season / post-2-seasons). No extra restriction needed — arrows just don't hurt teammates or neutral players.

---

## Spy

Spawns with a golden sword.

### Vanish

- Sneaking while still for ~2 seconds turns the Spy fully invisible — armor, held item, and particles all hidden.
- Can move up to ~3 blocks while vanished before breaking invisibility.
- Unvanishes instantly on: breaking a block, using a bow, eating, or any other action.
- Enemies can also unvanish a Spy by hitting them or right-clicking them.

### Flee

Right-click to spawn a decoy clone at the Spy's position and grant the Spy 6 seconds of full invisibility (armor hidden). The clone wanders and punches nearby enemies as a distraction.

- **40-second cooldown.**
- **[tweak]** Flee cannot be used within 20 blocks of an enemy **lifeline block** — specifically Ocean's Conduit, Nether's Furnace, or Plains' Composter. Mountain has no lifeline block, so Flee works freely inside the mountain biome.
- **[tweak]** The "Mercenary's Mark of Death" clause from Annihilation is dropped — no Mercenary class exists in RegionsMod.

### Backstab

Attacking an enemy from behind deals +1 damage.

---

## Farmer

Spawns with a set of wooden tools including a shovel, a stone hoe, 15 bonemeal, a golden carrot named **Feast**, and a dead bush named **Famine**.

### Harvest Passive

- Breaking tall grass has a chance to drop useful items.
- Crops harvested by the Farmer instantly regrow.
- **[tweak]** The instant-regrow bypasses the Cold Season crop-freeze — the ability is character-driven, not environmental. This is a major part of the Farmer's value to Plains during cold snaps.
- While harvesting crops, each break has a 1/100 chance for one of: Gold Ore, Iron Ore, Coal, Books, Bottles of Enchanting, Gold Nuggets, or Iron Hoes.
- **[tweak]** Annihilation's Ghast Tears, Nether Warts, and Soul Sand drops are removed — Nether materials belong to the Nether trader's economy, so Plains shouldn't produce them.
- Apples drop at 1/400.

### Feast

Right-click the golden carrot.

- Fully refills hunger for the Farmer and all teammates within 13 blocks, plus 4 saturation on top.
- Clears the Hunger potion effect on all affected players.
- **30-second cooldown.**

### Famine

Right-click the dead bush.

- Applies Hunger 20 for 30 seconds to every enemy within 13 blocks, draining them to ~2.5 hunger bars.
- **90-second cooldown.**
- **[tweak]** Famine can only be cast during a PVP window (Cold Season, or after PVP has gone permanent). Outside PVP, right-clicking does nothing and does not consume the cooldown.

### Food Synergy

30% chance when eating any food to restore an extra 2 HP and gain 2 saturation.

---

## Bard

Spawns with a set of wooden tools and a **Buffbox**. Place it and right-click to select from 4 songs.

| Song | Target | Effect | Duration |
| --- | --- | --- | --- |
| Invigorate | Teammates | Regeneration I | 20 s |
| Enlighten | Teammates | Speed I | 25 s |
| Intimidate | Enemies | Weakness III | 20 s |
| Shackle | Enemies | Slowness II | 15 s |

- Affects players within 15 blocks, shown by expanding note particles.
- Songs pass through walls and loop continuously.
- Switching songs has a **15-second cooldown**.
- **[tweak]** Enemy-target songs (Intimidate, Shackle) only apply during PVP windows. Outside PVP, the Buffbox still plays them — particles and sound fire — but no effect lands on non-PVP-legal targets. Teammate-target songs always apply.

### Buffbox

- Can be destroyed manually by the owner or by an enemy.
- Can be recalled manually by the owner.
- Teammates cannot destroy it.
- If the Bard recalls or breaks their own Buffbox, re-placement cooldown is **10 seconds**.
- If an enemy breaks it, re-placement cooldown is **30 seconds**.

---

## Lumberjack

Spawns with an Efficiency I stone axe and a brick block called **Brute Force**.

### Logging Passive

- +0.5 hearts bonus damage with any axe.
- 80% chance to get an extra log when mining a log block.

### Brute Force

Right-click the brick block. For 15 seconds, each axe hit on an enemy deals bonus durability damage to their armor.

| Axe Tier | Bonus Armor Durability per Hit |
| --- | --- |
| Wood / Gold | 3 |
| Stone | 6 |
| Iron | 9 |
| Diamond / Netherite | 12 |

- **45-second cooldown.**
- **[tweak]** Brute Force can be toggled on outside PVP windows, but armor-damage only ticks when the axe actually lands a hit on a PVP-legal target. This lets the Lumberjack pre-activate during the last seconds of peacetime to line up a Cold Season opener.

---

## Scout

Spawns with a golden sword and a **Grapple**.

### Grapple

Right-click the grapple to cast. The hook attaches to the first solid block it hits. Right-click again to reel in, launching the Scout toward the anchor.

- Launch strength scales with the vertical distance between the Scout and the hook — the higher the hook is above the player, the stronger the pull.
- While the grapple is held in the main hand, all fall damage is halved.

### Restrictions

The grapple cannot be used while:

- On fire.
- Under the Slowness effect.
- Immobilized (e.g., Ocean Immobilizer's stun). **[tweak: Annihilation's generic "immobilize" reference explicitly mapped to our Ocean Immobilizer class.]**
- Held in the off-hand slot.

### Combat Tag

Being hit by an enemy applies a 5-second **combat tag** during which the grapple cannot be used.

**[tweak]** Combat-tag timer only arms during PVP windows, since enemy hits can't land outside them. Practically: the Scout can grapple freely in peace even if recently brushed by a mob, and the ability "enemies can disable your grapple" only exists during Cold Season or after permanent PVP.

**[tweak]** No PVP gating on the grapple itself — it's a pure traversal tool.
