package com.qwaecd.customcoins;

import com.mojang.logging.LogUtils;
import com.qwaecd.customcoins.config.Config;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;
import static com.qwaecd.customcoins.item.ModItems.ITEMS;
import static com.qwaecd.customcoins.item.ModItems.TABS;

@Mod(CustomCoins.MODID)
public class CustomCoins
{
    public static final String MODID = "customcoins";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CustomCoins(IEventBus modEventBus, ModContainer modContainer)
    {
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
    }

//    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents
//    {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event)
//        {
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//        }
//    }
}
