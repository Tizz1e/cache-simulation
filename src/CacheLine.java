public interface CacheLine {
    State getState();
    int getTag();
    int getOld();
    void decOld();
    void incOld();
    void setOld(int newOld);
    void setState(State state);
    boolean fits(int addr);
}
