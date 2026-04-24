// ---- Team data ----
const TEAMS = {
  ocean: {
    name: 'Ocean',
    tag: 'BLUE · 200HP CONDUIT',
    color: 'var(--ocean)',
    lifeline: 'Conduit nexus (200 HP).',
    passive: 'None.',
    cold: 'Water freezes without heat. Drowned spawn in ocean water and hunt any non-ocean player. All teams get Slowness I in water.',
    trader: 'Villager — aquatic goods, water-related gear.',
    mass: 'Stationary Elder Guardians spawn at preset points and attack any non-ocean player.',
    link: 'teams/ocean.html',
  },
  nether: {
    name: 'Nether',
    tag: 'RED · FURNACE BURNS',
    color: 'var(--nether)',
    lifeline: 'Furnace must stay burning.',
    passive: 'Permanent Fire Resistance. Immune to lava & fire for the whole match.',
    cold: 'Lifeline furnace burns fuel 2x. Cold water damages Nether players on contact.',
    trader: 'Piglin — nether materials, fire-resistant gear.',
    mass: 'A stationary Wither spawns at the preset point, firing skulls at any non-nether player.',
    link: 'teams/nether.html',
  },
  plains: {
    name: 'Plains',
    tag: 'GREEN · EMERALD MONOPOLY',
    color: 'var(--plains)',
    lifeline: 'None structural. Defense is economic — only source of emeralds on the map.',
    passive: 'None.',
    cold: 'Crops stop growing unless within 1–2 blocks of a heat source.',
    trader: 'Villager — crops, food, farming tools.',
    mass: 'Three Pillager patrol squads spawn inside the biome and engage any non-plains player on sight.',
    link: 'teams/plains.html',
  },
  mountain: {
    name: 'Mountain',
    tag: 'GRAY · BLOOD TRIBUTE',
    color: 'var(--mountain)',
    lifeline: 'Blood Tribute each cold season. Miss it, every dwarf loses 1 life.',
    passive: 'Permanent Night Vision. Every dwarf sees in the dark for the whole match.',
    cold: 'Blood Tribute active. Deposit cooldowns increase. Some deposits flood or freeze.',
    trader: 'Villager — ores, ingots, mining tools.',
    mass: 'A single Warden spawns (100 HP, never aggros dwarves) and attacks any non-mountain player.',
    link: 'teams/mountain.html',
  },
};

// ---- Build team tiles ----
const grid = document.getElementById('teams-grid');
Object.entries(TEAMS).forEach(([key, t], i) => {
  const tile = document.createElement('div');
  tile.className = `team-tile bg-${key}`;
  tile.dataset.team = key;
  if (i === 0) tile.classList.add('is-selected');
  // 6x4 pixel banner
  let blocks = '';
  for (let r = 0; r < 4; r++) for (let c = 0; c < 6; c++) {
    const op = Math.random() > 0.55 ? 0.5 : 0.15;
    blocks += `<span style="background:rgba(255,255,255,${op.toFixed(2)})"></span>`;
  }
  tile.innerHTML = `
    <div class="team-tile__sub">${String(i+1).padStart(2,'0')} // ${t.tag}</div>
    <div class="team-tile__label">${t.name}</div>
    <div class="team-tile__blocks">${blocks}</div>
    <div class="team-tile__lifeline"><b>Lifeline</b>${t.lifeline}</div>
  `;
  tile.addEventListener('click', () => selectTeam(key));
  grid.appendChild(tile);
});

const drawer = document.getElementById('team-drawer');
function selectTeam(key) {
  document.querySelectorAll('.team-tile').forEach(el => el.classList.toggle('is-selected', el.dataset.team === key));
  const t = TEAMS[key];
  drawer.innerHTML = `
    <div class="team-drawer__banner bg-${key}">
      <div style="font-family:var(--mono);font-size:11px;text-transform:uppercase;letter-spacing:0.1em;opacity:0.85;">${t.tag}</div>
      <h3>${t.name}</h3>
      <div style="font-size:12px;opacity:0.95;line-height:1.5;">${t.lifeline}</div>
      <a class="btn" href="${t.link}" style="margin-top:auto;align-self:flex-start;">Full page ▸</a>
    </div>
    <div class="team-drawer__cell">
      <h4>Passive</h4>
      <p>${t.passive}</p>
      <h4 style="margin-top:16px;">Trader</h4>
      <p>${t.trader}</p>
    </div>
    <div class="team-drawer__cell">
      <h4>Cold-season effect</h4>
      <p>${t.cold}</p>
      <h4 style="margin-top:16px;">Mass event</h4>
      <p>${t.mass}</p>
    </div>
  `;
}
selectTeam('ocean');

// ---- Lifeline pixel icons (8x8) ----
function drawIcon(id, pattern, palette) {
  const el = document.getElementById(id);
  if (!el) return;
  pattern.split('').forEach(ch => {
    const s = document.createElement('span');
    s.style.background = palette[ch] || 'transparent';
    el.appendChild(s);
  });
}
// Conduit
drawIcon('ico-ocean',
  '........'+
  '...#....'+
  '..#*#...'+
  '.#*@*#..'+
  '..#*#...'+
  '...#....'+
  '........'+
  '........',
  {'.':'#9EC3E8','#':'#1E3F80','*':'#5C82B8','@':'#E8C547'});
// Furnace
drawIcon('ico-nether',
  '########'+
  '#......#'+
  '#.####.#'+
  '#.#@@#.#'+
  '#.#**#.#'+
  '#.####.#'+
  '#......#'+
  '########',
  {'#':'#3A3126','.':'#7A5E48','@':'#E8C547','*':'#B8342C'});
// Composter
drawIcon('ico-plains',
  '########'+
  '#......#'+
  '#.gggg.#'+
  '#.gGGg.#'+
  '#.gGGg.#'+
  '#.gggg.#'+
  '#......#'+
  '########',
  {'#':'#3A3126','.':'#BFA77A','g':'#82C26F','G':'#2F6926'});
// Mountain (blood tribute -> sword)
drawIcon('ico-mountain',
  '...##...'+
  '...##...'+
  '...##...'+
  '...##...'+
  '.######.'+
  '...##...'+
  '...##...'+
  '...@@...',
  {'.':'#A8A8B0','#':'#E8C547','@':'#7E1F1A'});

// ---- Timeline scrubber ----
const scrub = document.getElementById('tl-scrub');
const clockEl = document.getElementById('tl-clock');
const phaseEl = document.getElementById('tl-phase');
const pvpEl = document.getElementById('tl-pvp');
const btEl = document.getElementById('tl-bt');
const playhead = document.getElementById('tl-playhead');
const playBtn = document.getElementById('tl-play');
const resetBtn = document.getElementById('tl-reset');

const effects = {
  ocean: document.getElementById('eff-ocean'),
  nether: document.getElementById('eff-nether'),
  plains: document.getElementById('eff-plains'),
  mountain: document.getElementById('eff-mountain'),
};
const effectCells = document.querySelectorAll('.timeline__effects .effect');

function phaseFor(min) {
  if (min < 15) return { name: 'Warm 1', kind: 'warm', pvp: false };
  if (min < 30) return { name: 'Cold 1', kind: 'cold', pvp: true };
  if (min < 45) return { name: 'Warm 2', kind: 'warm', pvp: false };
  if (min < 60) return { name: 'Cold 2', kind: 'cold', pvp: true };
  return { name: 'Permanent PVP', kind: 'permanent', pvp: true };
}

function fmt(min) {
  const h = Math.floor(min/60).toString().padStart(2,'0');
  const m = (min%60).toString().padStart(2,'0');
  return `${h}:${m}`;
}

function render(min) {
  const p = phaseFor(min);
  clockEl.textContent = fmt(min);
  phaseEl.textContent = p.name;
  phaseEl.dataset.phase = p.kind;
  pvpEl.textContent = p.pvp ? 'ON' : 'off';
  pvpEl.className = p.pvp ? '' : 'off';
  btEl.textContent = (p.kind === 'cold') ? 'active' : (p.kind === 'permanent' ? 'cycling' : 'idle');

  // playhead position across 75 min = 100%
  const pct = Math.min(100, (min / 75) * 100);
  playhead.style.left = `${pct}%`;

  // effect copy per phase
  const isCold = p.kind === 'cold' || p.kind === 'permanent';
  effects.ocean.textContent = isCold
    ? 'Water freezes without heat. Drowned spawn in ocean water and hunt non-ocean players.'
    : 'Water is warm. Conduit holds the line.';
  effects.nether.textContent = isCold
    ? 'Furnace burns fuel 2x as fast. Cold water now damages Nether players.'
    : 'Furnace burns normally. Fire resistance passive always on.';
  effects.plains.textContent = isCold
    ? 'Crops stop growing without heat. Farmer\u2019s instant-regrow bypasses this.'
    : 'Crops growing. Emeralds flowing from the composter.';
  effects.mountain.textContent = isCold
    ? 'Blood Tribute timer active. Deposit cooldowns up. Some deposits flooded / frozen.'
    : 'Deposits regen at base rate. Blood Tribute idle.';

  effectCells.forEach(c => {
    c.classList.toggle('is-active', !isCold);
    c.classList.toggle('is-hostile', isCold);
  });
}

scrub.addEventListener('input', e => render(parseInt(e.target.value,10)));
render(0);

let playing = false;
let rafId = null;
let lastT = 0;
function tick(t) {
  if (!playing) return;
  if (!lastT) lastT = t;
  const dt = (t - lastT) / 1000;
  lastT = t;
  let v = parseInt(scrub.value,10) + dt * 3; // 3 minutes per real second
  if (v >= 75) { v = 75; playing = false; playBtn.textContent = '▶ Play'; }
  scrub.value = v;
  render(Math.floor(v));
  if (playing) rafId = requestAnimationFrame(tick);
}
playBtn.addEventListener('click', () => {
  playing = !playing;
  playBtn.textContent = playing ? '❙❙ Pause' : '▶ Play';
  lastT = 0;
  if (playing) rafId = requestAnimationFrame(tick);
});
resetBtn.addEventListener('click', () => {
  playing = false;
  playBtn.textContent = '▶ Play';
  scrub.value = 0;
  render(0);
});

// ---- Classes ----
const CLASSES = {
  ocean: [
    { no:1, name:'Defender', weapon:'Wooden tools + chain chest', ab:'Guardian\u2019s Warp', back:'Max HP scales with missing Conduit HP, capped at +10 hearts near the conduit.' },
    { no:2, name:'Immobilizer', weapon:'Starter kit', ab:'Single-target stun', back:'Right-click locks target + self in place 2–5s. Left-click AoE Slowness III. 30s shared CD.' },
    { no:3, name:'Neptune', weapon:'Stone sword + Trident', ab:'Ground Freeze + Tidebringer', back:'Freezes walked-over water 8 blocks ahead. Tidebringer toggles Riptide / Curse of the Sea.' },
    { no:4, name:'Siren', weapon:'Starter kit', ab:'Execute on 30% HP', back:'Sees enemy HP. Drain executes at \u2264 30% and heals for leftover HP. 60s CD.' },
    { no:5, name:'Healer', weapon:'Blood bag', ab:'Team Regen + cleanse', back:'Right-click: Regen III to 3 lowest-HP allies. Left-click: heal 15 HP + cleanse.' },
    { no:6, name:'Transporter', weapon:'Wooden tools + Quartz', ab:'Linked portals', back:'Right-click quartz on two blocks to pair. Sneak to travel. One pair at a time.' },
  ],
  nether: [
    { no:1, name:'Alchemist', weapon:'Leather armor + Brewing Tome', ab:'Enhanced potions', back:'Private brewing stand (2x speed). Tome rolls random potion ingredients. Enhanced brews in a cauldron.' },
    { no:2, name:'Enchanter', weapon:'Golden sword', ab:'XP-damage shield', back:'2x XP gain. Below 7 HP, incoming damage eats XP levels instead of health. Intensifier = +25% team XP.' },
    { no:3, name:'Bloodmage', weapon:'Stone sword', ab:'Corrupt + Bloodcursed Terraform', back:'Corrupt = 4-block AoE Wither. Terraform = 8-block curse field with Wither II / Blindness / Hunger III.' },
    { no:4, name:'Wizard', weapon:'Wand + Spellbook', ab:'5 spells, shared 15s GCD', back:'Inferno, Void Bolt, Arcane Bolt, Glacial Nova, Whirlwind. Choose one per cast.' },
    { no:5, name:'Vampire', weapon:'Stone sword', ab:'Blood Sense + teleport', back:'Sees enemies in a 10-block radius. Insidious Dispatch teleports behind a target not facing you.' },
    { no:6, name:'Rift Walker', weapon:'Blaze Rod', ab:'Group teleport', back:'Opens a 10s rift. Sneaking teammates are pulled along. Max party of 4. Targets enemy region markers.' },
  ],
  plains: [
    { no:1, name:'Archer', weapon:'Punch I bow', ab:'Rain of Arrows + Poison Shot', back:'+1 base bow damage. Arrow of Infinity. Left-click cycles specials.' },
    { no:2, name:'Spy', weapon:'Golden sword', ab:'Vanish + Flee clone', back:'Sneak 2s to go invisible. Flee spawns a punching decoy and 6s of full invisibility.' },
    { no:3, name:'Farmer', weapon:'Wooden + stone hoe', ab:'Feast + Famine', back:'Feast = team hunger refill. Famine = AoE Hunger 20. Crops the Farmer harvests instantly regrow — bypasses cold season.' },
    { no:4, name:'Bard', weapon:'Wooden tools', ab:'Buffbox (4 songs)', back:'Invigorate (Regen I), Enlighten (Speed I), Intimidate (Weak III), Shackle (Slow II). 15-block radius.' },
    { no:5, name:'Lumberjack', weapon:'Efficiency I stone axe', ab:'Brute Force', back:'+0.5 heart axe damage. 80% extra log. Brute Force shreds enemy armor durability for 15s.' },
    { no:6, name:'Scout', weapon:'Golden sword + Grapple', ab:'Grapple hook', back:'Right-click grapple to fire, right-click again to reel. 5s combat tag disables grapple mid-fight.' },
  ],
  mountain: [
    { no:1, name:'Builder', weapon:'Wooden tools', ab:'Resource Drop + Replication Cache', back:'Random building blocks every 90s. Cache replicates inserted building blocks at 10 blocks / 3s.' },
    { no:2, name:'Warrior', weapon:'Wooden sword', ab:'Frenzy', back:'+1 base melee. Frenzy: +1 dmg, Speed I, slow regen, 25% extra dmg taken. 12s.' },
    { no:3, name:'Berserker', weapon:'Stone sword', ab:'Heart stacking', back:'Kill enemies to gain +1 max heart up to 15. Melee-only to 20. Death drops 5 hearts. Armor-diff damage formula.' },
    { no:4, name:'Miner', weapon:'Eff I / Unb I pickaxe', ab:'Gold Rush', back:'67% double ore passive. Gold Rush forces 100% double + 67% triple for 10s, ignores cold-season slowdown.' },
    { no:5, name:'Tinkerer', weapon:'Stone sword', ab:'PowerPads + Disenchanting', back:'5 buff pads (Speed, Haste, Speed II, Haste II, Absorption). Books disenchant non-armor items.' },
    { no:6, name:'Acrobat', weapon:'Bow + 6 arrows', ab:'Double jump + fall immunity', back:'Mid-air double jump up to 6 blocks. No fall damage, ever. Hunger floor of 3.5 bars.' },
  ],
};

const classTabs = document.getElementById('class-tabs');
const classGrid = document.getElementById('class-grid');
const classFullLink = document.getElementById('class-full-link');
const TEAM_LABELS = { ocean: 'Ocean', nether: 'Nether', plains: 'Plains', mountain: 'Mountain' };
function renderClasses(team) {
  classTabs.querySelectorAll('button').forEach(b => b.setAttribute('aria-selected', b.dataset.team === team ? 'true' : 'false'));
  if (classFullLink) {
    classFullLink.href = `classes/${team}-classes.html`;
    classFullLink.textContent = `Full ${TEAM_LABELS[team]} classes page ▸`;
  }
  classGrid.innerHTML = '';
  CLASSES[team].forEach(c => {
    const card = document.createElement('div');
    card.className = 'class-card';
    card.innerHTML = `
      <div class="class-card__inner">
        <div class="class-card__face class-card__face--front" data-team="${team}">
          <div class="class-card__no">${String(c.no).padStart(2,'0')} / 06</div>
          <div class="class-card__name">${c.name}</div>
          <div class="class-card__weapon">${c.weapon}</div>
          <div class="class-card__ability">▸ ${c.ab}</div>
          <span class="class-card__hint">flip ▸</span>
        </div>
        <div class="class-card__face class-card__face--back">
          <div class="class-card__back-title">${c.name}</div>
          <div class="class-card__back-body">${c.back}</div>
          <div class="class-card__ability" style="color:var(--ink-muted);">▸ ${c.ab}</div>
          <span class="class-card__hint" style="color:var(--ink-muted);">◂ back</span>
        </div>
      </div>`;
    card.addEventListener('click', () => card.classList.toggle('is-flipped'));
    classGrid.appendChild(card);
  });
}
classTabs.addEventListener('click', e => {
  const b = e.target.closest('button');
  if (b) renderClasses(b.dataset.team);
});
renderClasses('ocean');
