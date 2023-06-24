package net.bozokits.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class DepositCommand implements TabExecutor {
    private final List<String> OPTIONS = List.of("all", "hand");
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player))
            return true;
        TextComponent errorText = Component.text("Unknown Argument. Please pick either hand or all", NamedTextColor.RED);
        if (args.length < 1) {
            player.sendMessage(errorText);
            return true;
        }
        if (args[0].equals("all") || args[0].equals("hand")) {
            player.performCommand("sell " + args[0]);
        } else {
            player.sendMessage(errorText);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            return OPTIONS;
        }
        return Collections.emptyList();
    }
}
