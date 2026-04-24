package com.regionsmoba;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionsMOBA implements ModInitializer {
	public static final String MOD_ID = "regionsmoba";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("RegionsMOBA initialized");
	}
}
