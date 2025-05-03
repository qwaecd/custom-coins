package com.qwaecd.customcoins.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.Collections;
import java.util.List;
import static com.qwaecd.customcoins.CustomCoins.LOGGER;

public class Config {
    public static ModConfigSpec SPEC;

    public static ModConfigSpec.ConfigValue<List<? extends String>> whiteListPlayerName;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        setupConfig(BUILDER);
        SPEC = BUILDER.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("General");
        whiteListPlayerName = builder
                .comment("This is a whitelist, used to store allowed player names.")
                .comment("这是一个白名单列表，用于存储允许的玩家名称。")
                .comment("示例: [\"qwaecd\",\"JohnXue\"]")
                .defineList("whiteListPlayerName", Collections.emptyList(), entry -> entry instanceof String);
        builder.pop();
    }

    public static List<String> getWhiteListPlayerName() {
        try {
            return (List<String>) whiteListPlayerName.get();
        } catch (Exception e) {
            LOGGER.error("将名称列表转换为String失败！");
            return Collections.emptyList();
        }
    }
}