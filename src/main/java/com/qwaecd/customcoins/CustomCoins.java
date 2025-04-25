package com.qwaecd.customcoins;

import com.mojang.logging.LogUtils;
import com.qwaecd.customcoins.config.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.qwaecd.customcoins.item.ModItems.ITEMS;
import static com.qwaecd.customcoins.item.ModItems.TABS;

@Mod(CustomCoins.MODID)
public class CustomCoins
{
    public static final String MODID = "customcoins";
    private static final Logger LOGGER = LogUtils.getLogger();
    public CustomCoins(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
