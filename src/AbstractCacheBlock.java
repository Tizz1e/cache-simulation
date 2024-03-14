public abstract class AbstractCacheBlock implements CacheBlock {
    protected final int index;
    protected final CacheLine[] cacheLines;

    public AbstractCacheBlock(int index, CacheLine[] cacheLines) {
        this.index = index;
        this.cacheLines = cacheLines;
    }

    public CacheLine getCacheLine(int tag) {
        for (CacheLine cacheLine : cacheLines) {
            if (cacheLine.getTag() == tag) {
                return cacheLine;
            }
        }

        return null;
    }
    public abstract void updateOld(int saveIndex);

    public abstract int findForSwapping();
}
