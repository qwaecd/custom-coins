package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

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
            CompoundTag tag = (CompoundTag) itemStack.getComponents();
            if (tag.getBoolean("check")) return InteractionResult.PASS;
            putNBT(itemStack, player);

            if (player.level().isClientSide())
                player.sendSystemMessage(Component.translatable("customcoins.coin_to_success"));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void putNBT(ItemStack itemStack, Player player) {
        CompoundTag tag = (CompoundTag) itemStack.getComponents();
        tag.putBoolean("check", true);
        tag.putInt("CustomModelData", 1);
        itemStack.enchant((Holder<Enchantment>) Enchantments.INFINITY, 1);

        String playerName = player.getName().getString();
//        ListTag loreList = new ListTag();
        Component loreComponent = Component.translatable("customcoins.item.check_coin_display", playerName)
                .withStyle(ChatFormatting.AQUA);
//        loreList.add(StringTag.valueOf(Component.Serializer.toJson(loreComponent)));

        if(!tag.contains("display")){
            tag.put("display", new CompoundTag());
        }
        CompoundTag displayTag = ((CompoundTag) itemStack.getComponents()).getCompound("display");
        displayTag.put("Lore", (Tag) loreComponent);
    }
}