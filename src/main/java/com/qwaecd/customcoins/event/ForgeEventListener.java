package com.qwaecd.customcoins.event;


import com.qwaecd.customcoins.command.Command;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.qwaecd.customcoins.CustomCoins.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }

}
