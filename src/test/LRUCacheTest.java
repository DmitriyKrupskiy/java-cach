package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.cache.lru.LRUCache;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class LRUCacheTest {

    LRUCache<Integer, Integer> cache = new LRUCache<>(3);


    @Test
    public void testEmpty() {
        assertNull(cache.get(1));
    }

    @Test
    public void testCache() {
        LinkedList<Integer> values1 = new LinkedList<>();
        values1.add(1);
        cache.set(1, 1);
        cache.set(2, 4);
        cache.set(3, 9);
        assertEquals(cache.get(1), 1);
        assertEquals(cache.get(2), 4);
        assertEquals(cache.get(3), 9);

        cache.set(4, 4);

        assertEquals(cache.get(1), null);
        assertEquals(cache.get(2), 4);
        assertEquals(cache.get(3), 9);
        assertEquals(cache.get(4), 4);
    }
}
