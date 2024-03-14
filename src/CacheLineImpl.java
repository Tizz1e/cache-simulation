public abstract class CacheLineImpl implements CacheLine {
    private State state;
    private final int tag;
    private final byte[] data;

    public CacheLineImpl(int tag, final byte[] data) {
        this.state = State.INVALID;
        this.tag = tag;
        this.data = data;
    }

    public CacheLineImpl(State state, int tag, final byte[] data) {
        this.tag = tag;
        this.state = state;
        this.data = data;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }
    @Override
    public State getState() {
        return state;
    }

    @Override
    public abstract int getOld();

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public boolean fits(int addr) {
        return (addr >> (Config.ADDR_LEN.value - Config.CACHE_TAG_LEN.value)) == tag;
    }
}
