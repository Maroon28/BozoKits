package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCommand implements CommandExecutor {
    private final String key;

    public AfkCommand(String key) {
        this.key = key;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player))
            return true;
        if (key.equals("afk")) {
            CommandUtils.runAsConsole("warp afk " + player.getName());
            player.sendMessage(MessageUtils.getMessage("afk-pool"));
            return true;
        }
        MessageUtils.sendMessageList(player, "text-commands.afkpool" + key);
        sendTitle(player);
        return true;
    }
    private void sendTitle(Player player) {
        Component main = MessageUtils.getMiniMessage().deserialize("<gradient:#12c2e9:#C471ED:#f64f59><bold>BOZO KITS</gradient>");
        NamedTextColor subColor = key.contains("leave") ? NamedTextColor.GREEN : NamedTextColor.RED;
        String subtitleText = key.contains("leave") ? "You are no longer AFK!" : "You are now AFK!";
        Title title = Title.title(main, Component.text(subtitleText, subColor));
        player.showTitle(title);
    }
}
