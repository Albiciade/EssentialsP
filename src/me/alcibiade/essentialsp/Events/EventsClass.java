package me.alcibiade.essentialsp.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.alcibiade.essentialsp.EssentialsP;
import me.alcibiade.essentialsp.Commands.Messages;
import me.alcibiade.essentialsp.Configurations.PlayersConfiguration;
import net.md_5.bungee.api.ChatColor;

public class EventsClass implements Listener {

	PlayersConfiguration cfg = new PlayersConfiguration(EssentialsP.GetPlugin());
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(ChatColor.GREEN + "> " + ChatColor.GRAY + p.getName() + " a rejoint le serveur.");
		cfg.getPlayers().set(p.getUniqueId() + ".name", p.getName());
		cfg.savePlayers();
		
		Messages.lastMessage.put(p, p);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.RED + "> " + ChatColor.GRAY + p.getName() + " a quitté le serveur.");

		Location loc = p.getLocation();
		String world = loc.getWorld().getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		cfg.getPlayers().set(p.getUniqueId() + ".logout.world", world);
		cfg.getPlayers().set(p.getUniqueId() + ".logout.x", x);
		cfg.getPlayers().set(p.getUniqueId() + ".logout.y", y);
		cfg.getPlayers().set(p.getUniqueId() + ".logout.z", z);
		cfg.savePlayers();
		Messages.lastMessage.put(Messages.lastMessage.get(p), Messages.lastMessage.get(p));
		Messages.lastMessage.remove(p);
	}
}
