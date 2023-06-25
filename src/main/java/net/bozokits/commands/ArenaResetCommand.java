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
            resetArena("7-block-ores");
            resetArena("8-block-stone");
            resetArena("2-block-sandstone");
            resetArena("air-arena");
            CommandUtils.shout("The arena has been reset!\n§r§4All players underground have been teleported", "shout");
        }, 100);
        return false;
    }

    private void resetArena(String name) {
        CommandUtils.runAsConsole("mrl reset " + name + " -s");
    }
}