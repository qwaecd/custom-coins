package com.qwaecd.customcoins.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import static com.qwaecd.customcoins.config.Config.getWhiteListPlayerName;

public abstract class AbstractCoin extends Item {
    public AbstractCoin(Properties properties) {
        super(properties.stacksTo(64));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context){
        Player player = context.getPlayer();
        if(player == null) return InteractionResult.PASS;
        ItemStack itemStack = context.getItemInHand();
        if (getWhiteListPlayerName().contains(player.getName().getString())){
            CompoundTag tag = itemStack.getOrCreateTag();
            if(tag.getBoolean("check")) return InteractionResult.PASS;
            tag.putBoolean("check",true);
            player.sendSystemMessage(Component.literal("转换成功！"));
            return  InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
