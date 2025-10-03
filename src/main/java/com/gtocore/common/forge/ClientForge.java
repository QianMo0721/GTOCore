package com.gtocore.common.forge;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public final class ClientForge {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        String langCode = Minecraft.getInstance().getLanguageManager().getSelected();

        var player = event.getPlayer();
        // 为英文玩家显示翻译提示，翻译贡献，discord和翻译项目
        if (!langCode.toLowerCase(Locale.ROOT).startsWith("en")) {
            player.sendSystemMessage(
                    Component.literal("If you are using the English translation. This translation is community-maintained with help from AI. Have suggestions or corrections? No Chinese required.")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
            player.sendSystemMessage(
                    Component.literal("Thanks to all the contributors who have contributed to the English translation.")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)));
            player.sendSystemMessage(
                    Component.literal("Until 2025.10.2 pp>10 : xinxinsuried (666.28), 暮心 (406.55), KatNite (294.84), Rain-Flying (122.37), Xelo (108.85), Ormakent (84.7), totallynormal-tree (47.65), Hvm (42.85), LEgenD-Leo (32.02), nebniloc (19.35)")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
            player.sendSystemMessage(
                    Component.literal("Click Here to Join the English translation project on ParaTranz")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://paratranz.cn/projects/16320"))));
            player.sendSystemMessage(
                    Component.literal("Click Here to Join the Discord for more information and updates")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/ZSVb4dgVNB"))));
        } ;
        // 为中文玩家显示QQ群加入链接 点击链接加入群聊【GTO寰宇重工集团·Alpha部门】：https://qm.qq.com/q/evWgwcXde0
        if (langCode.toLowerCase(Locale.ROOT).startsWith("zh")) {
            player.sendSystemMessage(
                    Component.literal("GTO寰宇重工集团·Alpha部门(927923997)，欢迎加入")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
            player.sendSystemMessage(
                    Component.literal("点击此处加入：https://qm.qq.com/q/evWgwcXde0")
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://qm.qq.com/q/evWgwcXde0"))));
        }
    }
}
