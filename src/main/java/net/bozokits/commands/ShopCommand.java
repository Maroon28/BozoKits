package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {
    private final int shopNumber;
    private final String shopName;
    private Player player;

    public ShopCommand(int shopNumber, String shopName) {
        this.shopNumber = shopNumber;
        this.shopName = shopName;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            this.player = player;
            Bukkit.getScheduler().runTaskLater(BozoKitsUtils.getInstance(), this::openShop, 20);
        }
        return true;
    }

    private void openShop() {
        String cmd = "shopkeepers remote " + shopNumber + " " + player.getName();
        CommandUtils.runAsConsole(cmd);
        player.sendMessage(Component.text("You opened the " + shopName + " Shop!", NamedTextColor.GREEN));
    }
}
