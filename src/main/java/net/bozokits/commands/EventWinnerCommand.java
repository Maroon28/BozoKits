package net.bozokits.commands;

import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.bozokits.utils.CommandUtils.*;

public class EventWinnerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("poglords.winner"))
            return true;
        if (args.length < 1) {
            sender.sendMessage("Please provide a winner!");
            return true;
        }
        Player winner = Bukkit.getPlayer(args[0]);
        if (winner == null) {
            sender.sendMessage("Invalid player!");
            return true;
        }
        runAsConsole("eventwin " + args[0]);
        runAsConsole("eco give " + args[0] + " 32");
        runAsConsole("cc give p Ultra 1 " + args[0]);
        for (Player player: Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessageList(player, "event-win", Placeholder.component("player", player.name()));
        }
        return true;
    }
}
