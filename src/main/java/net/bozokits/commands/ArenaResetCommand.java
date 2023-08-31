package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArenaResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("poglords.resetarena")) {
            return true;
        }
        Bukkit.getScheduler().runTaskLater(BozoKitsUtils.getInstance(), () -> {
            CommandUtils.runCommands("arena-reset");
            CommandUtils.shout("The arena has been reset!" + System.lineSeparator() + "§r§4All players underground have been teleported", "shout");
        }, 100);
        return false;
    }
}