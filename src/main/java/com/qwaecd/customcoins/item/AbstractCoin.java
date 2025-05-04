package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
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


        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        HolderLookup.Provider provider = null;
        if (server != null) {
            provider = server.registryAccess();
        }
        HolderLookup.RegistryLookup<Enchantment> reg = null;
        if (provider != null) {
            reg = provider.lookupOrThrow(Registries.ENCHANTMENT);
        }
        Holder<Enchantment> inf = reg.get(Enchantments.INFINITY).get();
        int levelInt = 1;
        itemStack.enchant(inf, levelInt);


        List<Component> componentList = new ArrayList<>();
        String playerName = player.getName().getString();
        componentList.add(
                Component.translatable("customcoins.item.check_coin_display", playerName).withStyle(ChatFormatting.AQUA)
        );
        ItemLore lore = new ItemLore(componentList);
        itemStack.set(DataComponents.LORE,lore);
    }
}