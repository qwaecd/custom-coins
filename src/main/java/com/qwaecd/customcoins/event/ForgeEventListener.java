package com.qwaecd.customcoins.event;


import com.qwaecd.customcoins.command.Command;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import static com.qwaecd.customcoins.CustomCoins.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeEventListener {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}
