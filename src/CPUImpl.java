public class CPUImpl implements CPU {
    private int tick;
    public final Cache cache;

    public CPUImpl(Cache cache) {
        this.tick = 0;
        this.cache = cache;
    }

    public void incrementLocal() {
        tick++;
    }

    public void createLocal() {
        tick++;
    }

    public void sum() {
        tick++;
    }

    public void nextIteration() {
        tick++;
    }

    public void exitFunction() {
        tick++;
    }

    public void multiplication() {
        tick += 5;
    }

    @Override
    public void C1_READ8(int addr) {
        tick += 1;
        tick += cache.C1_RESPONSE(addr, 1);
    }

    @Override
    public void C1_READ16(int addr) {
        tick += 1;
        tick += cache.C1_RESPONSE(addr, 2);
    }

    @Override
    public void C1_READ32(int addr) {
        tick += 2;
        tick += cache.C1_RESPONSE(addr, 4);
    }

    @Override
    public void C1_WRITE8(int addr) {
        tick += 1;
        tick += cache.write(addr);
    }

    @Override
    public void C1_WRITE16(int addr) {
        tick += 1;
        tick += cache.write(addr);
    }

    @Override
    public void C1_WRITE32(int addr) {
        tick += 2;
        tick += cache.write(addr);
    }

    @Override
    public int getTicks() {
        return tick;
    }

    @Override
    public double getPercentage() {
        return cache.getPercentage();
    }
}
