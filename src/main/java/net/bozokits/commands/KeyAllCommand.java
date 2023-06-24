package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class KeyAllCommand implements TabExecutor {
    List<String> OPTION = List.of("daily");
    private int timeLeft = 420; // 7 minutes (7 * 60 seconds)
    private BukkitRunnable countdownTask;
    private final DecimalFormat decimalFormat = new DecimalFormat("#0.0");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (countdownTask != null) {
            sender.sendMessage("Countdown is already running.");
            return true;
        }
        if (!sender.hasPermission("poglords.keyall.daily")) {
            sender.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Please do /keyall daily!");
            return true;
        }
        countdownTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    if (timeLeft >= 60) {
                        double minutes = timeLeft / 60.0;
                        CommandUtils.shout("KEY ALL IN " + formatMinutes(minutes), "shout");
                    } else if (timeLeft == 30 || timeLeft == 10 || timeLeft <= 3) {
                        CommandUtils.shout("KEY ALL IN " + timeLeft + " SECONDS", "shout");
                    }
                    timeLeft--;
                } else {
                    CommandUtils.runAsConsole("crazycrate giveall p Daily 1");
                    countdownTask.cancel();
                    countdownTask = null;
                }
            }
        };

        countdownTask.runTaskTimer(BozoKitsUtils.getInstance(), 0L, 20L); // Run every second (20 ticks)
        sender.sendMessage(MessageUtils.getMessage("keyall-start"));
        return true;
    }

    private String formatMinutes(double minutes) {
        return decimalFormat.format(minutes) + " MINUTES";
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            return OPTION;
        }
        return Collections.emptyList();
    }
}
