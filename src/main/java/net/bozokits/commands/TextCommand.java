package net.bozokits.commands;

import net.bozokits.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TextCommand implements CommandExecutor {
    private final String key;
    public TextCommand(String key) {
        this.key = key;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player))
            return true;
        MessageUtils.sendMessageList(player, "text-commands." + key);
        return true;
    }

}
