package org.tokeiito.wurmunlimited.mods.statistics.cache;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache<K, T> {
    private long timeToLive;
    private long timerInterval;
    private boolean running;
    private final ConcurrentHashMap<K, InMemoryCacheObject> cacheMap;

    private Thread cleanupThread;

    protected class InMemoryCacheObject {
        public long created = System.currentTimeMillis();
        public T value;

        protected InMemoryCacheObject(T value) {
            this.value = value;
        }
    }

    public long getTimerInterval() {
        return this.timerInterval;
    }

    /*
     * @param timeToLive expiration time in seconds
     * @param timerInternal how often to check for expiration in seconds
     */
    public InMemoryCache(long timeToLive, final long timerInterval) {
        this.timeToLive = timeToLive * 1000;
        this.timerInterval = timerInterval * 1000;
        this.running = true;

        this.cacheMap = new ConcurrentHashMap<K, InMemoryCacheObject>();

        if (this.timeToLive > 0 && this.timerInterval > 0) {
            cleanupThread = new Thread(new Runnable() {
                public void run() {
                    while(running) {
                        try {
                            Thread.sleep(getTimerInterval());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        inMemoryCacheCleanup();
                    }
                }
            });

            cleanupThread.setDaemon(true);
            cleanupThread.start();
        }
    }

    public synchronized void configure(long timeToLive, long timerInterval) {
        this.timeToLive = timeToLive * 1000;
        this.timerInterval = timerInterval * 1000;

        if(cleanupThread != null) {
            cleanupThread.interrupt();
        }
    }

    public synchronized void stopCleanupThread() {
        running = false;
        if (cleanupThread != null) {
            cleanupThread.interrupt();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new InMemoryCacheObject(value));
        }
    }

    public T get(K key) {
        synchronized (cacheMap) {
            InMemoryCacheObject c;
            c = cacheMap.get(key);

            if (c == null) {
                return null;
            } else {
                return c.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void inMemoryCacheCleanup() {
        long now = System.currentTimeMillis();
        synchronized (cacheMap) {
            cacheMap.forEach((key, value) -> {
                if (value != null && (now > (timeToLive + value.created))) {
                    cacheMap.remove(key);
                }
            });
        }
        Thread.yield();
    }
}
