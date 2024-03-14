public final class LRUCacheBlock extends AbstractCacheBlock {
    private int curTime;
    public LRUCacheBlock(int index, LRUCacheLine[] cacheLines) {
        super(index, cacheLines);
        curTime = 0;
    }

    @Override
    public int findForSwapping() {
        int tag = cacheLines[0].getTag();
        int currentOld = cacheLines[0].getOld();

        for (CacheLine cacheLine : cacheLines) {
            if (cacheLine.getState() == State.INVALID) {
                return cacheLine.getTag();
            }
            if (cacheLine.getOld() < currentOld) {
                tag = cacheLine.getTag();
                currentOld = cacheLine.getOld();
            }
        }

        return tag;
    }

    @Override
    public void changeLine(State newState, int newOld, int newTag, byte[] newBytes, int swapTag) {
        for (int i = 0; i < cacheLines.length; i++) {
            if (cacheLines[i].getTag() == swapTag) {
                cacheLines[i] = new LRUCacheLine(newState, newOld, newTag, newBytes);
                break;
            }
        }
    }

    public void updateOld(int saveTag) {
        for (int i = 0; i < cacheLines.length; i++) {
            if (cacheLines[i].getTag() == saveTag) {
                cacheLines[i].setOld(curTime++);
            }
        }
    }
}
