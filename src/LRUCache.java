import java.util.Arrays;

public class LRUCache extends CacheImpl {
    public LRUCache(int blocksAmount, int associativity, int cacheLineSize) {
        super(associativity, cacheLineSize);

        this.cacheBlocks = new LRUCacheBlock[blocksAmount];

        LRUCacheLine[] tmp = new LRUCacheLine[associativity];
        Arrays.fill(tmp, new LRUCacheLine(State.INVALID, -1, -1, new byte[cacheLineSize]));

        for (int i = 0; i < blocksAmount; i++) {
            cacheBlocks[i] = new LRUCacheBlock(
                    i,
                    Arrays.copyOf(tmp, associativity)
            );
        }
    }
}
