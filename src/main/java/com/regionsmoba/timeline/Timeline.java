package com.regionsmoba.timeline;

import com.regionsmoba.RegionsMOBA;

/**
 * Match timeline ticker. Per docs/src-md/gameplay/timeline.md:
 *   0:00–15:00  Warm 1   PVP Off
 *   15:00–30:00 Cold 1   PVP On
 *   30:00–45:00 Warm 2   PVP Off
 *   45:00–60:00 Cold 2   PVP On
 *   60:00+      Alternating, PVP Permanent On
 *
 * 15 minutes = 18000 ticks @ 20 TPS. PVP is on whenever the phase is COLD or the
 * total elapsed time has crossed 60:00. The phase keeps alternating after that
 * for environmental effects.
 *
 * Singleton, in-memory only. Cleared on match start / end.
 */
public final class Timeline {

    public static final int TICKS_PER_SECOND = 20;
    public static final int PHASE_SECONDS = 15 * 60;
    public static final int PHASE_TICKS = PHASE_SECONDS * TICKS_PER_SECOND;
    public static final int PERMANENT_PVP_TICKS = 4 * PHASE_TICKS; // 60:00

    private static final Timeline INSTANCE = new Timeline();

    public static Timeline get() {
        return INSTANCE;
    }

    private boolean running;
    private MatchPhase phase = MatchPhase.WARM;
    private int tickInPhase;
    private int totalTicks;

    private Timeline() {}

    public boolean isRunning() {
        return running;
    }

    public MatchPhase phase() {
        return phase;
    }

    public int totalTicks() {
        return totalTicks;
    }

    public int totalSeconds() {
        return totalTicks / TICKS_PER_SECOND;
    }

    public int secondsRemainingInPhase() {
        return Math.max(0, (PHASE_TICKS - tickInPhase) / TICKS_PER_SECOND);
    }

    public boolean isPvpPermanent() {
        return totalTicks >= PERMANENT_PVP_TICKS;
    }

    public void start() {
        running = true;
        phase = MatchPhase.WARM;
        tickInPhase = 0;
        totalTicks = 0;
        RegionsMOBA.LOGGER.info("Timeline started — Warm 1");
    }

    public void stop() {
        running = false;
        phase = MatchPhase.WARM;
        tickInPhase = 0;
        totalTicks = 0;
    }

    /** Called every server tick. Returns the phase change side-effect, or null. */
    public PhaseChange tick() {
        if (!running) return null;
        tickInPhase++;
        totalTicks++;
        if (tickInPhase < PHASE_TICKS) return null;
        return flipPhase();
    }

    /** Force-jump to the next phase boundary now. Used by /regions skip-season. */
    public PhaseChange skip() {
        if (!running) return null;
        // Pretend the phase is full — flipPhase resets the in-phase counter and updates totalTicks accordingly.
        int remaining = PHASE_TICKS - tickInPhase;
        if (remaining > 0) {
            tickInPhase = PHASE_TICKS;
            totalTicks += remaining;
        }
        return flipPhase();
    }

    /** /regions debug season — set the current phase without changing the in-phase timer. */
    public PhaseChange forcePhase(MatchPhase target) {
        if (!running || phase == target) return null;
        MatchPhase prev = phase;
        phase = target;
        boolean nowPermanent = isPvpPermanent();
        RegionsMOBA.LOGGER.info("Timeline forced: {} -> {} (permanentPvp={})", prev, phase, nowPermanent);
        return new PhaseChange(prev, phase, nowPermanent);
    }

    /** /regions debug seasontimer — set the seconds remaining in the current phase. */
    public void setSecondsRemainingInPhase(int seconds) {
        if (!running) return;
        int newRemaining = Math.max(0, Math.min(PHASE_SECONDS, seconds));
        tickInPhase = PHASE_TICKS - newRemaining * TICKS_PER_SECOND;
    }

    private PhaseChange flipPhase() {
        MatchPhase prev = phase;
        phase = (phase == MatchPhase.WARM) ? MatchPhase.COLD : MatchPhase.WARM;
        tickInPhase = 0;
        boolean nowPermanent = isPvpPermanent();
        RegionsMOBA.LOGGER.info("Timeline: {} -> {} (total {}s, permanentPvp={})",
                prev, phase, totalSeconds(), nowPermanent);
        return new PhaseChange(prev, phase, nowPermanent);
    }

    public record PhaseChange(MatchPhase from, MatchPhase to, boolean pvpPermanent) {}
}
