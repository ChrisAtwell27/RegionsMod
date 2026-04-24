// Shared sidebar injector for docs pages. Each page sets data-active="<slug>" on <body>.
(function () {
  const depthDir = location.pathname.match(/\/site\/([^/]+)\//);
  const up = depthDir ? '../' : '';

  const sections = [
    { title: 'Getting started', items: [
      { slug: 'installation', label: 'Installation', href: up + 'getting-started/installation.html' },
      { slug: 'hosting-a-game', label: 'Host a game', href: up + 'getting-started/hosting-a-game.html' },
      { slug: 'joining-a-game', label: 'Join a game', href: up + 'getting-started/joining-a-game.html' },
    ]},
    { title: 'Gameplay', items: [
      { slug: 'timeline', label: 'Timeline', href: up + 'gameplay/timeline.html' },
      { slug: 'cold-season', label: 'Cold Season', href: up + 'gameplay/cold-season.html' },
      { slug: 'mass-events', label: 'Mass Events', href: up + 'gameplay/mass-events.html' },
      { slug: 'trial-chamber', label: 'Trial Chamber', href: up + 'gameplay/trial-chamber.html' },
      { slug: 'economy-and-trade', label: 'Economy & Trade', href: up + 'gameplay/economy-and-trade.html' },
      { slug: 'deposits', label: 'Deposits', href: up + 'gameplay/deposits.html' },
    ]},
    { title: 'Teams', items: [
      { slug: 'teams-index', label: 'Overview', href: up + 'teams/index.html' },
      { slug: 'ocean', label: 'Ocean', href: up + 'teams/ocean.html' },
      { slug: 'nether', label: 'Nether', href: up + 'teams/nether.html' },
      { slug: 'plains', label: 'Plains', href: up + 'teams/plains.html' },
      { slug: 'mountain', label: 'Mountain', href: up + 'teams/mountain.html' },
    ]},
    { title: 'Classes', items: [
      { slug: 'classes-index', label: 'Overview', href: up + 'classes/index.html' },
      { slug: 'ocean-classes', label: 'Ocean Classes', href: up + 'classes/ocean-classes.html' },
      { slug: 'nether-classes', label: 'Nether Classes', href: up + 'classes/nether-classes.html' },
      { slug: 'plains-classes', label: 'Plains Classes', href: up + 'classes/plains-classes.html' },
      { slug: 'mountain-classes', label: 'Mountain Classes', href: up + 'classes/mountain-classes.html' },
    ]},
    { title: 'Reference', items: [
      { slug: 'commands', label: 'Commands', href: up + 'reference/commands.html' },
      { slug: 'signs', label: 'Signs', href: up + 'reference/signs.html' },
      { slug: 'protected-areas', label: 'Protected Areas', href: up + 'reference/protected-areas.html' },
    ]},
  ];

  const active = document.body.dataset.active;
  const side = document.querySelector('.doc-sidebar');
  if (!side) return;

  side.innerHTML = sections.map(s => `
    <div class="doc-sidebar__group">
      <div class="doc-sidebar__title">${s.title}</div>
      <ul>
        ${s.items.map(i => `<li><a href="${i.href}" class="${i.slug === active ? 'is-active' : ''}">${i.label}</a></li>`).join('')}
      </ul>
    </div>
  `).join('');

  // Auto-TOC: scan headings in .md-body
  const toc = document.querySelector('.doc-toc ol');
  if (toc) {
    const headings = document.querySelectorAll('.md-body h2');
    if (headings.length < 2) {
      document.querySelector('.doc-toc')?.remove();
    } else {
      headings.forEach((h, i) => {
        const id = h.id || 'h-' + i;
        h.id = id;
        const li = document.createElement('li');
        li.innerHTML = `<a href="#${id}">${h.textContent}</a>`;
        toc.appendChild(li);
      });
    }
  }
})();
