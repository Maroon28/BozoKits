package net.bozokits.utils;

import net.bozokits.BozoKitsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.time.Instant;
import java.util.List;

public class CommandUtils {
    private static final String COOLDOWN_KEY = "cooldowns";
    public static void runCommands(String key, Player player) {
        List<String> commands = BozoKitsUtils.getInstance().getConfig().getStringList("commands-" + key);
        for (String cmd: commands) {
            cmd = cmd.replace("<player>", player.getName());
            cmd = setRandom(cmd);
            runAsConsole(cmd);
        }
    }

    public static String setRandom(String cmd) {
        if (!cmd.contains("random")) {
            return cmd;
        }
        var args = cmd.split(" ");
        for (String arg : args) {
            if (arg.contains("random")) {
                cmd = cmd.replace(arg, getRandom(arg));
            }
        }
        return cmd;
    }
    public static String getRandom(String placeholder) {
        var randomArgs = placeholder.split("-");
        int min = Integer.parseInt(randomArgs[1]);
        int max = Integer.parseInt(randomArgs[2]);
        int randomNumber = getRandomNumber(min, max);
        placeholder = placeholder.replace("<random-" + min + "-" + max + ">", String.valueOf(randomNumber));
        return placeholder;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static void shout(String content, String key) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessageList(player, key + "-command", Placeholder.component("content", Component.text(content)));
        }
    }

    public static void runAsConsole(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void setCooldown(Player player, String command, long cooldownSeconds) {
        player.getPersistentDataContainer().set(getCooldownKey(command), PersistentDataType.LONG, Instant.now().plusSeconds(cooldownSeconds).toEpochMilli());
    }

    public static long getTimeLeft(Player player, String command) {
        Long cooldownEnd = player.getPersistentDataContainer().get(getCooldownKey(command), PersistentDataType.LONG);
        if (cooldownEnd == null) {
            return 0; // No cooldown set
        }
        long currentTime = Instant.now().toEpochMilli();
        long timeLeft = cooldownEnd - currentTime;
        return Math.max(0, timeLeft);
    }

    public static boolean hasCooldown(Player player, String command) {
        return getTimeLeft(player, command) > 0;
    }

    private static NamespacedKey getCooldownKey(String command) {
        return new NamespacedKey(BozoKitsUtils.getInstance(), COOLDOWN_KEY + "." + command);
    }

}
