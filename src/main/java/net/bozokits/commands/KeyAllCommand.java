package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class KeyAllCommand implements TabExecutor {
    List<String> OPTION = List.of("daily");
    private int timeLeft = 14 * 60; // 14 minutes (14 * 60 seconds)
    private final DecimalFormat decimalFormat = new DecimalFormat("#0.0");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!sender.hasPermission("poglords.keyall.daily")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Please do /keyall daily!");
            return true;
        }
        Bukkit.getServer().getScheduler().runTaskTimer(BozoKitsUtils.getInstance(), task -> {
                if (timeLeft > 0) {
                    if (timeLeft >= 60) {
                        double minutes = timeLeft / 120.0;
                        if (minutes == (int) minutes || minutes % 0.5 == 0) {
                            if (minutes >= 1.0)
                                CommandUtils.shout("KEY ALL IN §f§l" + decimalFormat.format(minutes) + " MINUTES!", "shout");
                        }
                    } else if (timeLeft == 30 || timeLeft == 10 || timeLeft <= 3) {
                        CommandUtils.shout("KEY ALL IN §f§l" + timeLeft + " SECONDS!", "shout");
                    }
                    timeLeft--;
                } else {
                    timeLeft = 14 * 60; // Set the time back.
                    task.cancel();
                    CommandUtils.runAsConsole("cc giveall p daily 1");
                }
            }, 0, 20L);

        sender.sendMessage(MessageUtils.getMessage("keyall-start"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            return OPTION;
        }
        return Collections.emptyList();
    }
}
