package net.bozokits;

import net.bozokits.commands.*;
import net.bozokits.listeners.PlayerListener;
import net.bozokits.utils.MessageAnnounceTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class BozoKitsUtils extends JavaPlugin {

    public static BozoKitsUtils plugin;
    public BozoKitsUtils() {
        plugin = this;
    }
    public static BozoKitsUtils getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        registerAllCommands();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        startAnnouncements();
    }

    private void startAnnouncements() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new MessageAnnounceTask(), 0, getConfig().getInt("announcement-interval"));
    }

    private void registerAllCommands() {
        registerTextCommands();
        registerShoutCommands();
        registerAFKCommands();
        registerShopCommands();
        registerDiscordBroadcastCommands();
        registerMiscCommands();
        registerEventJoinCommands();
        registerEventTitleCommands();
    }


    private void registerMiscCommands() {
        getCommand("arena-reset").setExecutor(new ArenaResetCommand());
        getCommand("rngtest").setExecutor(new RngCommand());
        getCommand("daily").setExecutor(new DailyRewardCommand());
        getCommand("randomkit").setExecutor(new RandomKitCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("keyall").setExecutor(new KeyAllCommand());
        getCommand("shulker").setExecutor(new ShulkerCommand());
        getCommand("anvil-reset").setExecutor(new AnvilResetCommand());
        getCommand("deposit").setExecutor(new DepositCommand());
        getCommand("eventwinner").setExecutor(new EventWinnerCommand());
        getCommand("freerank").setExecutor(new FreeRankCommand());
        getCommand("luckydip").setExecutor(new LuckyDipCommand());
        getCommand("boosting").setExecutor(new BoostingCommand());
    }
    private void registerDiscordBroadcastCommands() {
        String[] keys = new String[]{"discordboughtlegendarycrate", "discordlegendarycrate", "discordbozocrate"};
        for (String key : keys) {
            getCommand(key).setExecutor(new DiscordBroadcastCommand(key));
        }
    }
    private void registerEventTitleCommands() {
        String[] keys = new String[]{"eventyoukilled", "eventwin", "eventdied"};
        for (String key : keys) {
            getCommand(key).setExecutor(new EventTitleCommand(key));
        }
    }
    private void registerEventJoinCommands() {
        String[] keys = new String[]{"eventjoinwhite", "eventjoinred", "eventjoinwhitechance", "eventjoinredchance"};
        for (String key : keys) {
            getCommand(key).setExecutor(new EventJoinCommand(key));
        }
    }
    private void registerTextCommands() {
        String[] keys = new String[]{"ip", "store", "discord", "rules", "help"};
        for (String key: keys) {
            getCommand(key).setExecutor(new TextCommand(key));
        }
    }
    private void registerShoutCommands() {
        String[] keys = new String[]{"newshout", "newshoutsmall", "newshoutsmalllol"};
        for (String key: keys) {
            getCommand(key).setExecutor(new ShoutCommand(key));
        }
    }
    private void registerAFKCommands() {
        String[] keys = new String[]{"afkpool6969join", "afkpool6969leave", "none"};
        for (String key: keys) {
            getCommand(key).setExecutor(new AfkCommand(key));
        }
    }
    private void registerShopCommands() {
        HashMap<Integer, String> map = new HashMap<>(Map.of(
                6, "xp",
                10, "book",
                11, "potion",
                12, "food",
                13, "coin",
                14, "boom",
                15, "misc",
                16, "special"
        ));
        for (int num: map.keySet()) {
            getCommand(map.get(num).toLowerCase() + "shop").setExecutor(new ShopCommand(num, map.get(num)));
        }
    }

}
