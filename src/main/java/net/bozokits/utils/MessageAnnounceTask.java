package net.bozokits.utils;

import net.bozokits.BozoKitsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MessageAnnounceTask implements Runnable {
    private int index = 1;
    private final @NotNull Set<String> ANNOUNCEMENTS = BozoKitsUtils.getInstance().getConfig().getConfigurationSection("announcements").getKeys(false);
    @Override
    public void run() {
        for (Player p: Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessageList(p, getListAnnouncementKey());
        }
        if (index >= ANNOUNCEMENTS.size()) {
            index = 1;
            return;
        }
        index++;
    }

    private String getListAnnouncementKey() {
        return "announcements." + index;
    }

}
