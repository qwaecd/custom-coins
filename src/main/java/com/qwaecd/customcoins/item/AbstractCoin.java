package com.qwaecd.customcoins.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
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
            tag.putInt("CustomModelData",1);
            itemStack.enchant(Enchantments.INFINITY_ARROWS,1);
            if(player.level().isClientSide())
                player.sendSystemMessage(Component.translatable(
                        "customcoins.coin_to_success"
                ));
            return  InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
