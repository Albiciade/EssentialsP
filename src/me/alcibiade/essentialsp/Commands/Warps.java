package me.alcibiade.essentialsp.Commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.alcibiade.essentialsp.EssentialsP;
import me.alcibiade.essentialsp.Configurations.PlayersConfiguration;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class Warps extends CommandExecute implements CommandExecutor, Listener {

	private Plugin plugin;

	public Warps(Plugin plugin) {
		this.plugin = plugin;
	}

	PlayersConfiguration cfg = new PlayersConfiguration(EssentialsP.GetPlugin());

	public String warpCmd = "warp";
	public String warpsCmd = "warps";
	public String setWarpCmd = "setwarp";
	public String delWarpCmd = "delwarp";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase(warpCmd)) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.GRAY + "Liste des warps : " + ChatColor.YELLOW + "/warps");
					return true;
				} else {

					try {
						if (plugin.getConfig().getString("Warps." + args[0]).equals(null)) {
						}
					} catch (NullPointerException e) {
						p.sendMessage(ChatColor.RED + "Le warp spécifié n'existe pas.");
						return true;
					}

					World world = plugin.getServer()
							.getWorld(plugin.getConfig().getString("Warps." + args[0] + ".world"));
					int x = plugin.getConfig().getInt("Warps." + args[0] + ".x");
					int y = plugin.getConfig().getInt("Warps." + args[0] + ".y");
					int z = plugin.getConfig().getInt("Warps." + args[0] + ".z");
					Location loc = new Location(world, x, y, z);
					p.teleport(loc);
					p.sendMessage(ChatColor.GREEN + "Vous avez été téléporté au warp " + ChatColor.YELLOW + args[0]);
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase(warpsCmd)) {
				
				int count = 0;
				ArrayList<String> warps = new ArrayList<String>();

				p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "--------- Liste des Warps ---------");
				p.sendMessage(" ");
				for (String key : plugin.getConfig().getConfigurationSection("Warps").getKeys(false)) {
					warps.add(key);
					p.sendMessage(ChatColor.GRAY + "- " + key);
					count++;
				}
				if(count == 0) {
					p.sendMessage(ChatColor.RED + "Aucun warp n'est défini.");
				}
				return true;
			}

			if (cmd.getName().equalsIgnoreCase(setWarpCmd)) {

				if (args.length > 0) {
					Location loc = p.getLocation();

					plugin.getConfig().set("Warps." + args[0] + ".world", loc.getWorld().getName());
					plugin.getConfig().set("Warps." + args[0] + ".x", loc.getBlockX());
					plugin.getConfig().set("Warps." + args[0] + ".y", loc.getBlockY());
					plugin.getConfig().set("Warps." + args[0] + ".z", loc.getBlockZ());
					plugin.saveConfig();

					p.sendMessage(ChatColor.GREEN + "Le warp " + ChatColor.YELLOW + args[0] + ChatColor.GREEN
							+ " a été défini à votre emplacement.");
					p.sendMessage(ChatColor.GRAY + "Coordonnées : " + ChatColor.YELLOW + "x = " + loc.getBlockX() + ", "
							+ "y = " + loc.getBlockY() + ", " + "z = " + loc.getBlockZ());
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /setwarp <warp>");
					return true;
				}
			}

			if (cmd.getName().equalsIgnoreCase(delWarpCmd)) {
				if (args.length > 0) {

					try {
						if (plugin.getConfig().getString("Warps." + args[0]).equals(null)) {
						}
					} catch (NullPointerException e) {
						p.sendMessage(ChatColor.RED + "Le warp spécifié n'existe pas.");
						return true;
					}

					plugin.getConfig().set("Warps." + args[0], null);
					p.sendMessage(
							ChatColor.RED + "Le warp " + ChatColor.AQUA + args[0] + ChatColor.RED + " a été supprimé.");

					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /delwarp <warp>");
					return true;
				}
			}

		}
		return false;
	}

}
