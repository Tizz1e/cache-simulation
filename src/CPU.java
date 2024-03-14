public interface CPU {
    void C1_READ8(int addr);
    void C1_READ16(int addr);
    void C1_READ32(int addr);
    void C1_WRITE8(int addr);
    void C1_WRITE16(int addr);
    void C1_WRITE32(int addr);
    int getTicks();
    double getPercentage();
}
