package com.qwaecd.customcoins.data;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.qwaecd.customcoins.CustomCoins.MODID;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS_REGISTRAR =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

    public static final Supplier<DataComponentType<CoinData>> COIN_DATA_TYPE =
            DATA_COMPONENTS_REGISTRAR.registerComponentType("coin_data",
                    builder -> builder.persistent(CoinData.CODEC)
            );
}
