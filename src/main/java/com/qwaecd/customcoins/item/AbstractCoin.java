package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.config.Config;
import com.qwaecd.customcoins.data.CoinData;
import com.qwaecd.customcoins.data.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCoin extends Item {
    public AbstractCoin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        //这该死的 create 机械手
        if(player instanceof FakePlayer || Objects.equals(player.getUUID().toString(), "9e2faded-cafe-4ec2-c314-dad129ae971d")){
            return InteractionResult.PASS;
        }
        String playerName = player.getName().getString();
        ItemStack itemStack = context.getItemInHand();
        if (Config.whiteListPlayerName.get().contains(playerName)) {
            CustomModelData customData = itemStack.getComponents().get(DataComponents.CUSTOM_MODEL_DATA);
            if (customData != null) return InteractionResult.PASS;
            itemStack.set(ModDataComponents.COIN_DATA_TYPE, new CoinData(playerName, 1));
            itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
        super.appendHoverText(itemStack, context, tooltipComponents, tooltipFlag);


        if (containsData(itemStack)) {
            CoinData coinData = itemStack.getComponents().get(ModDataComponents.COIN_DATA_TYPE.get());
            String owner = coinData.ownerName();
            tooltipComponents.add(
                    Component.translatable("customcoins.item.check_coin_display",owner)
                            .withStyle(ChatFormatting.AQUA)
            );
            if(tooltipFlag.isAdvanced()){
                //若打开高级提示框 F3+H
                tooltipComponents.add(
                        Component.literal("data version: " + coinData.dataVersion())
                                .withStyle(ChatFormatting.DARK_GRAY)
                );
            }
//            if(Screen.hasShiftDown()){
//
//            }

        }

    }


    @Override
    public Component getName(ItemStack stack) {
        CoinData coinData = stack.getComponents().get(ModDataComponents.COIN_DATA_TYPE.get());

        if (containsData(stack)) {
            String name = super.getName(stack).getString();
            return Component.translatable(name).withStyle(ChatFormatting.BOLD, ChatFormatting.AQUA);
        }

        return super.getName(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack){
        return containsData(stack);
    }

    private boolean containsData(ItemStack itemStack){
        CoinData coinData = itemStack.getComponents().get(ModDataComponents.COIN_DATA_TYPE.get());
        return coinData != null && !coinData.ownerName().isEmpty();
    }
//    public static void putComponents(ItemStack itemStack, Player player){
//        CustomModelData customModelData = new CustomModelData(1);
//        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, customModelData);
//
//
//        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
//        HolderLookup.Provider provider = null;
//        if (server != null) {
//            provider = server.registryAccess();
//        }
//        HolderLookup.RegistryLookup<Enchantment> reg = null;
//        if (provider != null) {
//            reg = provider.lookupOrThrow(Registries.ENCHANTMENT);
//        }
//        Holder<Enchantment> inf = reg.get(Enchantments.INFINITY).get();
//        int levelInt = 1;
//        itemStack.enchant(inf, levelInt);
//
//
//        List<Component> componentList = new ArrayList<>();
//        String playerName = player.getName().getString();
//        componentList.add(
//                Component.translatable("customcoins.item.check_coin_display", playerName).withStyle(ChatFormatting.AQUA)
//        );
//        ItemLore lore = new ItemLore(componentList);
//        itemStack.set(DataComponents.LORE,lore);
//    }
}