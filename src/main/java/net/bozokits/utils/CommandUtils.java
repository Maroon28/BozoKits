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
    public static void runCommands(String key) {
        List<String> commands = BozoKitsUtils.getInstance().getConfig().getStringList("commands-" + key);
        for (String cmd: commands) {
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
        int max = Integer.parseInt(randomArgs[2].replace(">", ""));
        int randomNumber = getRandomNumber(min, max);
        placeholder = placeholder.replace("<random-" + min + "-" + max + ">", String.valueOf(randomNumber));
        return placeholder;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static void shout(String content, String key) {
        var lines = MessageUtils.getMessageList(key + "-command", Placeholder.component("content", Component.text(content)));
        for (Component line : lines) {
            Bukkit.broadcast(line);
        }
    }
    public static void shout(Component content, String key) {
        var lines = MessageUtils.getMessageList(key + "-command", Placeholder.component("content", content));
        for (Component line : lines) {
            Bukkit.broadcast(line);
        }
    }
    public static void shout(List<Component> contents, String key) {
        var lines = MessageUtils.getMessageList(key + "-command", Placeholder.component("content", contents.get(0)));
        contents.remove(0);

        int insertIndex = getInsertIndex(key) + 1;
        lines.addAll(insertIndex, contents);

        for (Component line : lines) {
            Bukkit.broadcast(line);
        }
    }

    private static int getInsertIndex(String key) {
        if (key.contains("small"))
            return 0;
        else
            return 2;
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

    public static String formatTime(long milliseconds) {
        if (milliseconds < 0) {
            return "0s";
        }

        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days);
            sb.append("d ");
            hours %= 24;
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append("hr ");
            minutes %= 60;
        }
        if (minutes > 0) {
            sb.append(minutes);
            sb.append("min ");
            seconds %= 60;
        }
        if (seconds > 0 || sb.length() == 0) {
            sb.append(seconds);
            sb.append("s");
        }

        return sb.toString().trim();
    }
    public static boolean hasCooldown(Player player, String command) {
        return getTimeLeft(player, command) > 0;
    }

    private static NamespacedKey getCooldownKey(String command) {
        return new NamespacedKey(BozoKitsUtils.getInstance(), COOLDOWN_KEY + "." + command);
    }

}
