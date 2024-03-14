public class PLRUCacheBlock extends AbstractCacheBlock {
    private int linesWithOne;
    public PLRUCacheBlock(int index, CacheLine[] cacheLines) {
        super(index, cacheLines);
        linesWithOne = 0;
    }

    @Override
    public void updateOld(int curTag) {
        boolean hasBit = false;

        for (CacheLine line : cacheLines) {
            if (line.getTag() == curTag && line.getOld() == 1) {
                hasBit = true;
                break;
            }
        }

        if (!hasBit) {
            linesWithOne++;
            for (CacheLine line : cacheLines) {
                if (line.getTag() == curTag) {
                    line.setOld(1);
                } else if (linesWithOne == cacheLines.length) {
                    line.setOld(0);
                }
            }

            if (linesWithOne == cacheLines.length) {
                linesWithOne = 1;
            }
        }
    }

    @Override
    public int findForSwapping() {
        for (CacheLine line : cacheLines) {
            if (line.getState() == State.INVALID) {
                return line.getTag();
            }

            if (line.getOld() == 0) {
                return line.getTag();
            }
        }

        throw new IllegalStateException("Incorrect state, all lines have pLRU bit 1");
    }

    @Override
    public void changeLine(State newState, int newOld, int newTag, byte[] newBytes, int swapTag) {
        for (int i = 0; i < cacheLines.length; i++) {
            if (cacheLines[i].getTag() == swapTag) {
                cacheLines[i] = new PLRUCacheLine(newState, newOld, newTag, newBytes);
                break;
            }
        }
    }
}
