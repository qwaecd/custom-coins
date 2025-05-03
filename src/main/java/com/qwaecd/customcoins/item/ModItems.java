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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.qwaecd.customcoins.CustomCoins.MODID;
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static final Map<String, DeferredHolder<Item, Item>> ITEM_REGISTRIES = new LinkedHashMap<>();

    public static final DeferredHolder<Item, Item> GoldCoin = registerItem("gold_coin",
            () -> new GoldCoin(new Item.Properties()));
    public static final DeferredHolder<Item, Item> IronCoin = registerItem("iron_coin",
            () -> new IronCoin(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DiamondCoin = registerItem("diamond_coin",
            () -> new DiamondCoin(new Item.Properties()));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(MODID + ".main_tab"))
                    .icon(() -> GoldCoin.get().getDefaultInstance())
                    .displayItems((params, output) -> ITEM_REGISTRIES.values().forEach(holder -> output.accept(holder.get())))
                    .build());

    public static DeferredHolder<Item, Item> registerItem(String name, Supplier<Item> supplier) {
        DeferredHolder<Item, Item> item = ITEMS.register(name, supplier);
        ITEM_REGISTRIES.put(name, item);
        return item;
    }
}
