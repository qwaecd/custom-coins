package com.qwaecd.customcoins.item;

import com.qwaecd.customcoins.config.Config;
import com.qwaecd.customcoins.data.CoinData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;


public abstract class AbstractCoin extends Item {
    public AbstractCoin(Properties properties) {
        super(properties.stacksTo(64));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context){
        Player player = context.getPlayer();
        if(player == null) return InteractionResult.PASS;
        //这该死的 create 机械手
        if(player instanceof FakePlayer || Objects.equals(player.getUUID().toString(), "9e2faded-cafe-4ec2-c314-dad129ae971d")){
            return InteractionResult.PASS;
        }

        String playerName = player.getName().getString();
        ItemStack itemStack = context.getItemInHand();

        if (Config.whiteListPlayerName.get().contains(playerName)){
            CompoundTag tag = itemStack.getOrCreateTag();
            if(tag.contains(CoinData.LOOT_TAG_NAME)) return InteractionResult.PASS;

            CoinData coinData = new CoinData(playerName);
            tag.put(CoinData.LOOT_TAG_NAME, coinData.getCoinTag());
            tag.putInt("CustomModelData",1);


            return  InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag flag){
        super.appendHoverText(itemStack, level, tooltipComponents, flag);

        CompoundTag coinTag = itemStack.getTagElement(CoinData.LOOT_TAG_NAME);

        if (coinTag != null && coinTag.contains(CoinData.KEY_OWNER, Tag.TAG_STRING)) {
            String owner = coinTag.getString(CoinData.KEY_OWNER);
            tooltipComponents.add(
                    Component.translatable("customcoins.item.check_coin_display",owner)
                    .withStyle(ChatFormatting.AQUA)
            );
            if(flag.isAdvanced()){
                //若打开高级提示框 F3+H
                tooltipComponents.add(
                        Component.literal("data version: " + coinTag.get(CoinData.KEY_DATA_VERSION))
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
        CompoundTag coinTag = stack.getTagElement("coin");

        if (coinTag != null && coinTag.contains("owner", Tag.TAG_STRING)) {
            String name = super.getName(stack).getString();
            return Component.translatable(name).withStyle(ChatFormatting.BOLD, ChatFormatting.AQUA);
        }

        return super.getName(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack){
        CompoundTag coinTag = stack.getTagElement("coin");
        return coinTag != null && coinTag.contains("owner", Tag.TAG_STRING);
    }

}
