package net.bozokits.commands;

import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EventTitleCommand implements CommandExecutor {
    private final String key;

    public EventTitleCommand(String key) {
        this.key = key;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("bozokits.eventadmin")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Please provide a player!");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("Player does not exist or is offline!");
            return true;
        }
        if (args.length == 2) {
            sendTitle(player, Bukkit.getPlayer(args[1]));
        } else {
            sendTitle(player, null);
        }
        return true;
    }
    private void sendTitle(Player player, Player victim) {
        Component main = MessageUtils.getMessage(key);
        Component subtitle;
        if (victim != null) {
            subtitle = MessageUtils.getMiniMessage().deserialize(MessageUtils.getMessageRaw(key + "sub"), Placeholder.component("victim", victim.name()));
        } else {
            subtitle = MessageUtils.getMiniMessage().deserialize(MessageUtils.getMessageRaw(key + "sub"));
        }
        Title title = Title.title(main, subtitle);
        player.showTitle(title);
    }
}
