public enum Config {
    MEM_SIZE(1 << 20),
    ADDR_LEN(20),
    CACHE_WAY(4),
    CACHE_TAG_LEN(9),
    CACHE_IDX_LEN(4),
    CACHE_OFFSET_LEN(7),
    CACHE_SIZE(8192),
    CACHE_LINE_SIZE(128),
    CACHE_LINE_COUNT(64),
    CACHE_SETS_COUNT(16),
    ADDR1_BUS_LEN(20),
    ADDR2_BUS_LEN(13),
    DATA1_BUS_LEN(16),
    DATA2_BUS_LEN(16),
    CTR1_BUS_LEN(3),
    CTR2_BUS_LEN(2);

    public final int value;

    Config(int value) {
        this.value = value;
    }
}
