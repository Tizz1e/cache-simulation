public class PLRUCacheLine extends CacheLineImpl {
    private int bit;

    public PLRUCacheLine(int tag, byte[] data) {
        super(tag, data);
        this.bit = 0;
    }

    public PLRUCacheLine(State state, int bit, int tag, byte[] data) {
        super(state, tag, data);
        this.bit = bit;
    }

    @Override
    public void decOld() {
        bit = 0;
    }

    @Override
    public void incOld() {
        bit = 1;
    }

    @Override
    public void setOld(int newOld) {
        bit = newOld;
    }

    @Override
    public int getOld() {
        return bit;
    }
}
