package cache;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.tokeiito.wurmunlimited.mods.statistics.cache.InMemoryCache;

public class InMemoryCacheTest {
    
    @Test
    void whenValueAddedToCacheItShouldBeThere() {
        String key = "SomeKey";
        int value = 123;

        InMemoryCache<String, Integer> cache = new InMemoryCache<>(10, 20);
        cache.put(key, value);

        Assert.assertEquals(value, (int) cache.get(key));
    }

    @Test
    void whenValueExistsInCacheItShouldBePossibleToRemoveIt() {
        String key = "SomeKey";
        int value = 123;

        InMemoryCache<String, Integer> cache = new InMemoryCache<>(10, 20);
        cache.put(key, value);

        cache.remove(key);

        Assert.assertEquals(null, cache.get(key));
    }

    @Test
    void whenThereAreTwoElementsInCacheSizeShouldReturnCorrectAmmount() {
        String key1 = "SomeKey1";
        int value1 = 123;

        String key2 = "SomeKey2";
        int value2 = 223;

        InMemoryCache<String, Integer> cache = new InMemoryCache<>(10, 20);
        cache.put(key1, value1);
        cache.put(key2, value2);

        Assert.assertEquals(2, cache.size());
    }

    @Test
    void whenItemExpiresItShouldBeRemovedFromCache() {
        String key = "SomeKey";
        int value = 123;

        InMemoryCache<String, Integer> cache = new InMemoryCache<>(1, 1);
        cache.put(key, value);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(null, cache.get(key));
    }
}
