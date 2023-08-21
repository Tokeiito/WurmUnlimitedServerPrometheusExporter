package org.tokeiito.wurmunlimited.mods.statistics;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerShutdownListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

public class StatisticsMod implements WurmServerMod, Initable, Configurable, ServerStartedListener, ServerShutdownListener {

	private Logger logger = Logger.getLogger(StatisticsMod.class.getName());
	
	private int serverPort = 5315;
	private String serverAddress = "127.0.0.1";
	
	@Override
	public void onServerStarted() {
		try {
			HttpServerLauncher.getInstance().start();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}		
	}

	@Override
	public void onServerShutdown() {
		try {
			HttpServerLauncher.getInstance().stop();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}		
	}

	@Override
	public void configure(Properties properties) {
		this.serverPort = Integer.parseInt(properties.getProperty("serverPort", Integer.toString(serverPort)));
		this.serverAddress = properties.getProperty("serverAddress", serverAddress);
		
		logger.info("serverPort: " + serverPort);
		logger.info("serverAddress: " + serverAddress);
		
		HttpServerLauncher.getInstance().configure(this.serverAddress, this.serverPort);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
