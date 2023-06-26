package net.bozokits.utils;

import net.bozokits.BozoKitsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    private static final FileConfiguration CONFIG = BozoKitsUtils.getInstance().getConfig();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component getMessage(String main, String key) {
        String configuredMessage = CONFIG.getString(main + "." + key);
        if (configuredMessage == null)
            return MINI_MESSAGE.deserialize("<red>Message <dark_red>" + key + "</dark_red> not found in config");
        return MINI_MESSAGE.deserialize(configuredMessage);
    }
    public static Component getMessage(String key) {
        return getMessage("messages", key);
    }
    public static String getMessageRaw(String key) {
        return CONFIG.getString("messages." + key);
    }
    public static Component getMessage(String key, TagResolver.Single... placeholders) {
        String configuredMessage = CONFIG.getString("messages." + key);
        if (configuredMessage == null)
            return MINI_MESSAGE.deserialize("<red>Message <dark_red>" + key + "</dark_red> not found in config");
        return MINI_MESSAGE.deserialize(configuredMessage, placeholders);
    }

    public static void sendMessageList(Player player, String listKey) {
        for (String msg : getMessageList(listKey)) {
            player.sendMessage(MINI_MESSAGE.deserialize(msg));
        }
    }

    public static void sendMessageList(Player player, String listKey, TagResolver.Single... placeholders) {
        for (String msg : getMessageList(listKey)) {
            player.sendMessage(MINI_MESSAGE.deserialize(msg, placeholders));
        }
    }
    public static List<Component> getMessageList(String listKey, TagResolver.Single... placeholders) {
        List<Component> components = new ArrayList<>();
        for (String msg : getMessageList(listKey)) {
            components.add(MINI_MESSAGE.deserialize(msg, placeholders));
        }
        return components;
    }
    public static @NotNull List<String> getMessageList(String listKey) {
        return CONFIG.getStringList(listKey);
    }

    public static MiniMessage getMiniMessage() {
        return MINI_MESSAGE;
    }
}
