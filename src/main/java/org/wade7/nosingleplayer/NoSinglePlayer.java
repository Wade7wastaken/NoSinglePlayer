package org.wade7.nosingleplayer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoSinglePlayer extends JavaPlugin implements Listener {

	String version = "1.0.0";
	int grace_period_length = 5 * 60 * 20;
	int grace_period_timer = grace_period_length;

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Starting NoSinglePlayer" + version);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			if (Bukkit.getOnlinePlayers().size() > 1) {
				grace_period_timer = grace_period_length;
			} else {
				if (grace_period_timer > 0) grace_period_timer--;
			}
		}, 0L, 1L);

	}

	@Override
	public void onDisable() {
		getLogger().info("Stopping NoSinglePlayer");
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Bukkit.getOnlinePlayers().size() == 1 && player.getGameMode() == GameMode.SURVIVAL && grace_period_timer == 0) {
			player.sendMessage("You shouldn't be playing alone.");
			event.setCancelled(true);
		}
	}
}
