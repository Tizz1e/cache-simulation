public interface CacheBlock {
    CacheLine getCacheLine(int tag);
    void updateOld(int tag);
    int findForSwapping();
    void changeLine(State newState, int newOld, int newTag, byte[] newBytes, int swapTag);
}
