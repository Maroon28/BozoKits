package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class EventJoinCommand implements CommandExecutor {
    private final String key;
    private final Random random = new Random();
    public EventJoinCommand(String key) {
        this.key = key;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!sender.hasPermission("poglords." + key)) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        boolean hasChance = key.contains("chance");

        if (hasChance) {
            double randomValue = random.nextDouble();
            if (randomValue <= 0.2) {
                broadcastList();
            }
        } else {
            broadcastList();
        }
        return true;
    }

    private void broadcastList() {
        String color = key.contains("red") ? "<color:#12c2e9>" : "<white>";
        String message = BozoKitsUtils.getInstance().getConfig().getString("event-join");
        message = message.replace("<color>", color);
        List<String> lines = List.of(" ", " ", message, " ", " ");
        for (String line: lines) {
            Bukkit.broadcast(MessageUtils.getMiniMessage().deserialize(line));
        }
    }
}
