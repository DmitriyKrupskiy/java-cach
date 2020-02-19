package test;

import com.cache.lfu.LFUCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LFUCacheTest {
    LFUCache<Long, Integer> cache = new LFUCache<>(5);

    @Test
    public void test() {
        cache.set(1L, 10);
        cache.set(2L, 20);
        cache.set(3L, 30);
        cache.set(4L, 40);
        cache.set(5L, 50);

        assertEquals(cache.get(1L), 10);

        cache.set(6L, 60);

        assertNull(cache.get(2L));

        cache.set(7L, 70);

        assertNull(cache.get(3L));
        assertEquals(cache.get(4L), 40);
        assertEquals(cache.get(5L), 50);
    }

}
