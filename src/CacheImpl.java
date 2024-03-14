import java.util.Arrays;

public abstract class CacheImpl implements Cache {
    protected CacheBlock[] cacheBlocks;
    protected final int cacheLineSize;
    protected final int associativity;
    protected final Mem memory;
    protected int hits;
    protected int total;

    protected CacheImpl(int associativity, int cacheLineSize) {
        this.cacheLineSize = cacheLineSize;
        this.associativity = associativity;
        this.memory = new MemImpl(Config.MEM_SIZE.value);
        this.hits = 0;
        this.total = 0;

    }
    @Override
    public int write(int addr) {
        int time = 0;

        total++;

        int blockIndex = (addr >> Config.CACHE_OFFSET_LEN.value) & ((1 << Config.CACHE_IDX_LEN.value) - 1);
        int tag = addr >> (Config.ADDR_LEN.value - Config.CACHE_TAG_LEN.value);

        if (cacheBlocks[blockIndex].getCacheLine(tag) == null) {
            time += C2_READ_LINE(addr >> Config.CACHE_OFFSET_LEN.value);
        } else {
            hits++;
        }

        CacheLine line = cacheBlocks[blockIndex].getCacheLine(tag);
        line.setState(State.MODIFIED);

        cacheBlocks[blockIndex].updateOld(line.getTag());

        return time;
    }

    @Override
    public int C2_READ_LINE(int addr) {
        int time = 1;

        time += 100;
        time += cacheLineSize / Config.CTR2_BUS_LEN.value;

        int blockIndex = addr & ((1 << Config.CACHE_IDX_LEN.value) - 1);

        int swapTag = cacheBlocks[blockIndex].findForSwapping();

        if (cacheBlocks[blockIndex].getCacheLine(swapTag).getState() == State.MODIFIED) {
            time += C2_WRITE_LINE();
        }

        cacheBlocks[blockIndex].changeLine(
                State.SYNCHRONIZED,
                0,
                addr >> Config.CACHE_IDX_LEN.value,
                new byte[cacheLineSize],
                swapTag
        );

        return time;
    }

    @Override
    public int C2_WRITE_LINE() {
        int time = 1;

        time += 100;
        time += cacheLineSize / Config.CTR2_BUS_LEN.value;

        return time;
    }

    @Override
    public int C1_RESPONSE(int addr, int amount) {
        int time = 0;

        total++;
        int blockIndex = (addr >> Config.CACHE_OFFSET_LEN.value) & ((1 << Config.CACHE_IDX_LEN.value) - 1);
        int tag = addr >> (Config.ADDR_LEN.value - Config.CACHE_TAG_LEN.value);

        CacheLine line = cacheBlocks[blockIndex].getCacheLine(tag);

        if (line == null || line.getState() == State.INVALID) {
            time += 4;
            time += C2_READ_LINE(addr >> Config.CACHE_OFFSET_LEN.value);
        } else {
            time += 6;
            hits++;
        }

        cacheBlocks[blockIndex].updateOld(tag);

        time += amount / 2 + amount % 2;

        return time;
    }

    @Override
    public double getPercentage() {
        return (double)hits / total;
    }

    @Override
    public int getHits() {
        return hits;
    }

    @Override
    public int getTotal() {
        return total;
    }
}
