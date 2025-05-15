package com.qwaecd.customcoins.data;

import net.minecraft.nbt.CompoundTag;

public class CoinData extends CompoundTag{
    private final CompoundTag coinTag;
    public static final String LOOT_TAG_NAME;
    public static final int COIN_DATA_VERSION;
    public static final String KEY_OWNER;
    public static final String KEY_DATA_VERSION;

    static {
        LOOT_TAG_NAME  = "coin";
        COIN_DATA_VERSION = 1;
        KEY_OWNER = "owner";
        KEY_DATA_VERSION = "data_version";
    }


    public CoinData(String ownerName){
        CompoundTag coinTag = new CompoundTag();
        coinTag.putString(KEY_OWNER, ownerName);
        coinTag.putInt(KEY_DATA_VERSION, COIN_DATA_VERSION);

        this.coinTag = coinTag;
    }


    public CompoundTag getCoinTag() {
        return coinTag;
    }

    /**
     * 获取硬币实例的数据版本号。
     * @return 数据版本号（int）
     */
    public int getCoinDataVersion(){
        return coinTag.getInt(KEY_DATA_VERSION);
    }
}
