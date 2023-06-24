package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnvilResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("poglords.anvilreset")) {
            if (!(commandSender instanceof Player player)) {
                return true;
            }
            CommandUtils.runCommands("anvil-reset", player);
            return true;
        }
        return true;
    }
}
