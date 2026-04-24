// Shared site header + footer injected on every page.
(function () {
  const currentPath = location.pathname.replace(/\/$/, '').split('/').pop() || 'index.html';
  const currentDir = (() => {
    const m = location.pathname.match(/\/site\/([^/]+)\//);
    return m ? m[1] : null;
  })();

  // Resolve relative paths depending on depth.
  // Landing lives at /site/index.html; docs live at /site/<section>/<page>.html.
  const depth = currentDir ? 1 : 0;
  const up = depth === 1 ? '../' : '';

  const navLinks = [
    { label: 'Home', href: up + 'index.html', section: 'home' },
    { label: 'Install', href: up + 'getting-started/installation.html', section: 'getting-started' },
    { label: 'Gameplay', href: up + 'gameplay/timeline.html', section: 'gameplay' },
    { label: 'Teams', href: up + 'teams/index.html', section: 'teams' },
    { label: 'Classes', href: up + 'classes/index.html', section: 'classes' },
    { label: 'Reference', href: up + 'reference/commands.html', section: 'reference' },
  ];

  const activeSection = document.body.dataset.section || (currentDir ?? 'home');

  // Pixel logo: a 4x4 grid with the four team colors.
  const logoHtml = `
    <span class="site-nav__logo" aria-hidden="true">
      <span style="background:var(--ocean)"></span><span style="background:var(--ocean)"></span><span style="background:var(--nether)"></span><span style="background:var(--nether)"></span>
      <span style="background:var(--ocean)"></span><span style="background:var(--paper)"></span><span style="background:var(--paper)"></span><span style="background:var(--nether)"></span>
      <span style="background:var(--plains)"></span><span style="background:var(--paper)"></span><span style="background:var(--paper)"></span><span style="background:var(--mountain)"></span>
      <span style="background:var(--plains)"></span><span style="background:var(--plains)"></span><span style="background:var(--mountain)"></span><span style="background:var(--mountain)"></span>
    </span>`;

  const nav = document.createElement('nav');
  nav.className = 'site-nav';
  nav.innerHTML = `
    <div class="site-nav__inner">
      <a class="site-nav__brand" href="${up}index.html">
        ${logoHtml}
        <span>RegionsMOBA</span>
      </a>
      <div class="site-nav__links">
        ${navLinks.map(l => `<a href="${l.href}" class="${l.section === activeSection ? 'is-active' : ''}">${l.label}</a>`).join('')}
        <a class="site-nav__ext" href="https://github.com/ChrisAtwell27/RegionsMOBA" target="_blank" rel="noopener">GitHub ↗</a>
        <a class="site-nav__ext" href="https://discord.gg/yuJcaUNPpu" target="_blank" rel="noopener">Discord ↗</a>
      </div>
    </div>`;
  document.body.insertBefore(nav, document.body.firstChild);

  // Footer
  const footer = document.createElement('footer');
  footer.className = 'site-footer';
  footer.innerHTML = `
    <div class="site-footer__inner">
      <div>
        <div style="display:flex;gap:10px;align-items:center;margin-bottom:8px;">
          ${logoHtml}
          <strong style="font-family:var(--pixel);font-size:11px;">RegionsMOBA</strong>
        </div>
        <small>Fabric minigame mod for Minecraft 1.21.11.<br/>Four asymmetric nations compete on a shared map.</small>
      </div>
      <div>
        <div class="chip no-dot" style="margin-bottom:10px;">Community</div>
        <div style="display:flex;gap:10px;flex-wrap:wrap;">
          <a class="btn" href="https://github.com/ChrisAtwell27/RegionsMOBA" target="_blank" rel="noopener">GitHub ↗</a>
          <a class="btn btn--ocean" href="https://discord.gg/yuJcaUNPpu" target="_blank" rel="noopener">Discord ↗</a>
        </div>
      </div>
      <div>
        <div class="chip no-dot" style="margin-bottom:10px;">Not affiliated with Mojang</div>
        <small>Minecraft is a trademark of Mojang Studios.<br/>Fan project. Not endorsed.</small>
      </div>
    </div>`;
  document.body.appendChild(footer);
})();
