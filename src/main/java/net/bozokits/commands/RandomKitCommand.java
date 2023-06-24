package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomKitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }
        if (!player.hasPermission("bozokits.randomkit")) {
            player.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        CommandUtils.runAsConsole("kit " + CommandUtils.getRandomNumber(1, 50) + " " + player.getName());
        player.sendMessage(Component.text("You received a random kit!", NamedTextColor.GREEN));
        return false;
    }
}
