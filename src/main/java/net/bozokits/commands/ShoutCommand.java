package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoutCommand implements CommandExecutor {
    private final String key;

    public ShoutCommand(String key) {
        this.key = key;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("bozokits." + key)) {
            commandSender.sendMessage(Component.text("No permission to use /shout!", NamedTextColor.RED));
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage(Component.text("Must provide content to broadcast!", NamedTextColor.RED));
            return true;
        }
        String content = parseHexColors(String.join(" ", args));
        CommandUtils.shout(content, key.replace("new", ""));
        return true;
    }

    public static String parseHexColors(String message) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start() + 1, matcher.end());
            message = message.replace("&" + color, String.valueOf(ChatColor.of(color)));
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
