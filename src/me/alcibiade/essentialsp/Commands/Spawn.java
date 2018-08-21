package me.alcibiade.essentialsp.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class Spawn extends CommandExecute implements CommandExecutor, Listener {

	private Plugin plugin;

	public Spawn(Plugin plugin) {
		this.plugin = plugin;
	}

	public String spawnCmd = "spawn";
	public String setSpawnCmd = "setspawn";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase(spawnCmd)) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				World world = plugin.getServer().getWorld(plugin.getConfig().getString("Location.spawn.world"));
				int x = plugin.getConfig().getInt("Location.spawn.x");
				int y = plugin.getConfig().getInt("Location.spawn.y");
				int z = plugin.getConfig().getInt("Location.spawn.z");

				if (world != null) {
					Location loc = new Location(world, x, y, z);

					p.teleport(loc);
					p.sendMessage(ChatColor.GREEN + "Vous avez été téléporté au spawn.");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Erreur ! Merci de contacter un administrateur.");
					return true;
				}
			} else {
				plugin.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Cette commande ne peut pas être exécutée depuis la console !");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase(setSpawnCmd)) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("essentials.setspawn")) {
					Location loc = p.getLocation();
					String world = loc.getWorld().getName();
					int x = loc.getBlockX();
					int y = loc.getBlockY();
					int z = loc.getBlockZ();

					plugin.getConfig().set("Location.spawn.world", world);
					plugin.getConfig().set("Location.spawn.x", x);
					plugin.getConfig().set("Location.spawn.y", y);
					plugin.getConfig().set("Location.spawn.z", z);

					plugin.saveConfig();
					plugin.reloadConfig();

					p.sendMessage(ChatColor.GREEN + "Le spawn a été défini à votre emplacement.");
					p.sendMessage(ChatColor.GRAY + "Coordonnées : " + ChatColor.YELLOW + "x = " + x + ", " + "y = " + y
							+ ", " + "z = " + z);

					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de faire ceci !");
					return true;
				}
			} else {
				plugin.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Cette commande ne peut pas être exécutée depuis la console !");
				return true;
			}

		}
		return false;
	}

}
