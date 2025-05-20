package com.qwaecd.customcoins.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CoinData(String ownerName, int dataVersion) {
    public static final Codec<CoinData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("owner").forGetter(CoinData::ownerName),
                    Codec.INT.fieldOf("data_version").forGetter(CoinData::dataVersion)
            ).apply(instance, CoinData::new)
    );
}
