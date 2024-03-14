public interface Cache {
    int write(int addr);
    int C2_READ_LINE(int addr);
    int C2_WRITE_LINE();
    int C1_RESPONSE(int addr, int amount);
    double getPercentage();
    int getHits();
    int getTotal();
}
