package org.tokeiito.wurmunlimited.mods.statistics;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpServerLauncher {
	
	private static Logger logger = Logger.getLogger(StatisticsMod.class.getName());
	private static HttpServerLauncher instance;
	 	
	private int port = 0;
	private String serverAddress = null;
	private int maxThreads = 1;
	private StatisticsHttpServer statisticsHttpServer;

	public static synchronized HttpServerLauncher getInstance() {
		if (instance == null) {
			instance = new HttpServerLauncher();
		}
		return instance;
	}
	
	public void configure(String serverAddress, int port) {
		this.port = port;
		this.serverAddress = serverAddress;
	}

	public boolean isRunning() {
		return  this.statisticsHttpServer != null;
	}

	public void start() throws IOException {
		logger.info("Listening on: "+serverAddress+":"+port);
		this.statisticsHttpServer = new StatisticsHttpServer(port, serverAddress, maxThreads);
	}

	public void stop() throws IOException {
		if (this.statisticsHttpServer != null) {
			this.statisticsHttpServer.stop();
			this.statisticsHttpServer = null;
		}		
	}
}
