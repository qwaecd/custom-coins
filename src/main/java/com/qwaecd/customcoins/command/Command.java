package com.qwaecd.customcoins.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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
                    .then(Commands.literal("add")
                        .requires(source -> source.hasPermission(4))
                            .then(Commands.argument("player-name", StringArgumentType.string())
                                    .executes((context)->
                                            addPlayer(context,StringArgumentType.getString(context,"player-name"))))
                    )
                    .then(Commands.literal("remove")
                            .requires(source -> source.hasPermission(4))
                            .then(Commands.argument("player-name", StringArgumentType.string())
                                    .executes((context)->
                                            removePlayer(context,StringArgumentType.getString(context,"player-name"))))
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
                "customcoins.command.list_player",playerName
        ));
        return 1;
    }

    private static int addPlayer(CommandContext<CommandSourceStack> context, String playerName){
        List<String> list = Config.getWhiteListPlayerName();
        if(!list.contains(playerName)){
            list.add(playerName);
            context.getSource().sendSystemMessage(Component.translatable(
                    "customcoins.command.temp_add_player",playerName
            ));
            return 1;
        }
        context.getSource().sendSystemMessage(Component.translatable(
                "customcoins.command.temp_add_player_fail",playerName
        ));
        return 0;
    }

    private static int removePlayer(CommandContext<CommandSourceStack> context, String playerName){
        List<String> list = Config.getWhiteListPlayerName();
        if(list.contains(playerName)){
            list.remove(playerName);
            context.getSource().sendSystemMessage(Component.translatable(
                    "customcoins.command.temp_remove_player",playerName
            ));
            return 1;
        }
        context.getSource().sendSystemMessage(Component.translatable(
                "customcoins.command.temp_remove_player_fail",playerName
        ));
        return 0;
    }
}
