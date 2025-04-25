package com.qwaecd.customcoins.config;

import net.minecraftforge.common.ForgeConfigSpec;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<List<String>> whiteListPlayerName;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(BUILDER);
        SPEC = BUILDER.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder builder){
        builder.push("General");
        //[General]:
        whiteListPlayerName = builder
                .comment("这是一个白名单列表，用于存储允许的玩家名称。")
                .define("whiteListPlayerName",new ArrayList<String>(),(Object o) -> o instanceof String);

        builder.pop();
    }

    public static List<String> getWhiteListPlayerName(){
        return whiteListPlayerName.get();
    }
}
