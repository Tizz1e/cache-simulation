public final class LRUCacheLine extends CacheLineImpl {
    private int old;
    public LRUCacheLine(int tag, byte[] data) {
        super(tag, data);
        old = -1;
    }
    public LRUCacheLine(State state, int old, int tag, byte[] data) {
        super(state, tag, data);
        this.old = old;
    }
    public void decOld() {
        old = Math.max(old - 1, 0);
    }

    @Override
    public void incOld() {
        old++;
    }

    @Override
    public void setOld(int newOld) {
        old = newOld;
    }

    @Override
    public int getOld() {
        return old;
    }
}
