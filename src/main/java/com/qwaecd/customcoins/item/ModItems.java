package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.item.coin.DiamondCoin;
import com.qwaecd.customcoins.item.coin.GoldCoin;
import com.qwaecd.customcoins.item.coin.IronCoin;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import static com.qwaecd.customcoins.CustomCoins.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<Item, GoldCoin> GOLD_COIN = ITEMS.register(
            "gold_coin",
            () -> new GoldCoin(new Item.Properties())
    );

    public static final DeferredHolder<Item, IronCoin> IRON_COIN = ITEMS.register(
            "iron_coin",
            () -> new IronCoin(new Item.Properties())
    );

    public static final DeferredHolder<Item, DiamondCoin> DIAMOND_COIN = ITEMS.register(
            "diamond_coin",
            () -> new DiamondCoin(new Item.Properties())
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register(
            "main_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(MODID + ".main_tab"))
                    .icon(() -> GOLD_COIN.get().getDefaultInstance())
                    .displayItems((params, output) ->
                            ITEMS.getEntries().forEach(holder ->
                                    output.accept(
                                            holder.get().getDefaultInstance(),
                                            CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                                    )
                            )
                    )
                    .build()
    );
}
