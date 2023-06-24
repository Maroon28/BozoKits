package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LuckyDipCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("bozokits.luckydip")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Please provide a player!");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("Invalid Player!");
            return true;
        }
        CommandUtils.runAsConsole("kit herorank1d " + player);
        MessageUtils.sendMessageList(player, "lucky-dip");
        return false;
    }
}
