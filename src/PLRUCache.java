import java.util.Arrays;

public class PLRUCache extends CacheImpl {
    protected PLRUCache(int blocksAmount, int associativity, int cacheLineSize) {
        super(associativity, cacheLineSize);

        this.cacheBlocks = new PLRUCacheBlock[blocksAmount];

        PLRUCacheLine[] tmp = new PLRUCacheLine[associativity];
        Arrays.fill(tmp, new PLRUCacheLine(State.INVALID, 0, -1, new byte[cacheLineSize]));

        for (int i = 0; i < blocksAmount; i++) {
            cacheBlocks[i] = new PLRUCacheBlock(
                    i,
                    Arrays.copyOf(tmp, associativity)
            );
        }
    }
}
