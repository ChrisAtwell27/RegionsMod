package com.regionsmoba.config;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/**
 * Cuboid bounds. high is the corner with max X/Y/Z (pos1 in commands);
 * low is the corner with min X/Y/Z (pos2 in commands). Both corners share a
 * dimension — the area cannot straddle dimensions.
 */
public record Area(BlockPosData high, BlockPosData low) {

    public static Area of(ServerLevel level, BlockPos a, BlockPos b) {
        String dim = BlockPosData.dimensionId(level);
        BlockPosData high = new BlockPosData(dim,
                Math.max(a.getX(), b.getX()),
                Math.max(a.getY(), b.getY()),
                Math.max(a.getZ(), b.getZ()));
        BlockPosData low = new BlockPosData(dim,
                Math.min(a.getX(), b.getX()),
                Math.min(a.getY(), b.getY()),
                Math.min(a.getZ(), b.getZ()));
        return new Area(high, low);
    }

    public boolean contains(BlockPos pos) {
        return pos.getX() >= low.x() && pos.getX() <= high.x()
                && pos.getY() >= low.y() && pos.getY() <= high.y()
                && pos.getZ() >= low.z() && pos.getZ() <= high.z();
    }

    public boolean contains(double x, double y, double z) {
        return x >= low.x() && x <= high.x() + 1
                && y >= low.y() && y <= high.y() + 1
                && z >= low.z() && z <= high.z() + 1;
    }

    public static Area onlyHigh(ServerLevel level, BlockPos pos) {
        return new Area(BlockPosData.of(level, pos), null);
    }

    public static Area onlyLow(ServerLevel level, BlockPos pos) {
        return new Area(null, BlockPosData.of(level, pos));
    }

    public boolean isComplete() {
        return high != null && low != null;
    }

    /** Whichever corner is set carries the dimension. */
    public String dimension() {
        if (high != null) return high.dimensionOrDefault();
        if (low != null) return low.dimensionOrDefault();
        return BlockPosData.DEFAULT_DIMENSION;
    }
}
