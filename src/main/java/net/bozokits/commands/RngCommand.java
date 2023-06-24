package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RngCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("bozokits.rngtest")) {
            commandSender.sendMessage("No Permission!");
            return true;
        }
        if (commandSender instanceof Player player) {
            var list = MessageUtils.getMessageList( "rngtest-command");
            for (var line: list) {
                line = CommandUtils.setRandom(line);
                player.sendMessage(MessageUtils.getMiniMessage().deserialize(line));
            }
        }
        commandSender.sendMessage("Must be a player!");
        return true;
    }
}
