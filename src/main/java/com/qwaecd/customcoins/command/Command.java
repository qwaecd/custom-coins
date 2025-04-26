package com.qwaecd.customcoins.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.qwaecd.customcoins.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import java.util.List;


public class Command {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){

        dispatcher.register(
                Commands.literal("customcoins")
                    .then(Commands.literal("list")
                        .requires(source -> source.hasPermission(0))
                            .executes(Command::listPlayer)
                    )
        );
    }

    private static int listPlayer(CommandContext<CommandSourceStack> context){
        StringBuilder builder = new StringBuilder();
        List<String> nameList = Config.getWhiteListPlayerName();
        for (String name : nameList){
            builder.append(name).append(",");
        }
        if(!builder.isEmpty()){
            builder.deleteCharAt(builder.length() - 1);
        }
        String playerName = builder.toString();

        context.getSource().sendSystemMessage(Component.translatable(
                "command.list_player",playerName
        ));
        return 1;
    }


}
