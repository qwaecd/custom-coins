package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCoin extends Item {
    public AbstractCoin(Properties properties) {
        super(properties.stacksTo(64));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        String playerName = player.getName().getString();
        ItemStack itemStack = context.getItemInHand();
        if (Config.whiteListPlayerName.get().contains(playerName)) {
            CustomModelData customData = itemStack.getComponents().get(DataComponents.CUSTOM_MODEL_DATA);
            if (customData != null) return InteractionResult.PASS;
            putComponents(itemStack, player);
            if (player.level().isClientSide())
                player.sendSystemMessage(Component.translatable("customcoins.coin_to_success"));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void putComponents(ItemStack itemStack, Player player){
        CustomModelData customModelData = new CustomModelData(1);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, customModelData);

        ItemEnchantments.Mutable mutableEnchants = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

//        Holder<Enchantment> enchantment = Holder.direct();
//        mutableEnchants.set(enchantment, 1);
//        ItemEnchantments finalEnchants = mutableEnchants.toImmutable();
//        itemStack.set(DataComponents.ENCHANTMENTS, finalEnchants);

        List<Component> componentList = new ArrayList<>();
        String playerName = player.getName().getString();
        componentList.add(
                Component.translatable("customcoins.item.check_coin_display", playerName).withStyle(ChatFormatting.AQUA)
        );
        ItemLore lore = new ItemLore(componentList);
        itemStack.set(DataComponents.LORE,lore);
    }
}