package org.tokeiito.wurmunlimited.mods.statistics;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creatures;

@SuppressWarnings("restriction")
public class StatisticsHttpServer {
private final Logger logger = Logger.getLogger(StatisticsHttpServer.class.getName());
	
	private final HttpServer httpServer;
	private final ExecutorService executor;
	
	public StatisticsHttpServer(int port, String serverIpAddress, int maxThreads) throws IOException {
		
		Server.getInstance().getExternalIp();

		InetAddress addr = InetAddress.getByAddress(this.IPToBytes(serverIpAddress));
		
		executor = new ThreadPoolExecutor(0, maxThreads, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		InetSocketAddress address = new InetSocketAddress(addr, port);
		httpServer = HttpServer.create(address, 0);
		httpServer.setExecutor(executor);
		httpServer.createContext("/metrics", new HttpHandler() {
			
			@Override
			public void handle(HttpExchange httpExchange) throws IOException {
				logger.info("Got request " + httpExchange.getRequestURI().toString());

				String response = "# HELP wu_number_of_creatures_total The total number of creatures.\n"
						+ "# TYPE wu_number_of_creatures_total counter\n"
						+ "wu_number_of_creatures_total{type=\"agro\"} " + Creatures.getInstance().getNumberOfAgg() + "\n"
						+ "wu_number_of_creatures_total{type=\"nice\"} " + Creatures.getInstance().getNumberOfNice()+"\n";
				
				//Setting Headers for the response message
				Headers responseHeaders = httpExchange.getResponseHeaders();
				responseHeaders.add("Access-Control-Allow-Origin", "*");
				responseHeaders.add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
				responseHeaders.add("Access-Control-Allow-Credentials", "true");
				responseHeaders.add("Access-Control-Allow-Methods", "GET");
				
				//Sending back response to the client
				httpExchange.sendResponseHeaders(200, response.length());
				OutputStream outStream = httpExchange.getResponseBody();
				outStream.write(response.getBytes());
				outStream.close();
			}
		});
		
		httpServer.start();
	}
	
	public void stop() throws IOException {
		httpServer.stop(0);
		executor.shutdown();
		try {
			executor.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
	}
	
	private byte[] IPToBytes(String ipAddress) {
		byte[] value = new byte[4];
		StringTokenizer tokens = new StringTokenizer(ipAddress, ".");

		for (int x = 0; tokens.hasMoreTokens(); ++x) {
			String next = tokens.nextToken();
			value[x] = Integer.valueOf(next).byteValue();
		}
		
		return value;
	}
}
