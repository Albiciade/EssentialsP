package me.alcibiade.essentialsp.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class Teleport extends CommandExecute implements CommandExecutor, Listener {

	public static HashMap<Player, Player> teleportMap = new HashMap<>();
	public static HashMap<Player, Long> teleportTimeMap = new HashMap<>();

	public String tpReqCmd = "tpa";
	public String tpAccCmd = "tpaccept";
	public String tpDenCmd = "tpdeny";

	int requestDuration = 30;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase(tpReqCmd)) {
				if (args.length > 0) {
					Player d = Bukkit.getServer().getPlayer(args[0]);

					if (d == null) {
						p.sendMessage(ChatColor.RED + "Le joueur sp�cifi� n'a pas pu �tre trouv�.");
						return true;
					}

					teleportMap.put(d, p);
					teleportTimeMap.put(d, System.currentTimeMillis());

					p.sendMessage(ChatColor.GREEN + "Votre requ�te a �t� envoy�e � " + ChatColor.YELLOW + d.getName());
					p.sendMessage(
							ChatColor.GREEN + "Cette requ�te sera active durant " + requestDuration + " secondes.");

					d.sendMessage(ChatColor.YELLOW + p.getName() + " vous a envoy� une demande de t�l�portation.");
					d.sendMessage(ChatColor.GREEN + "Pour l'accepter ou la refuser, utilisez les commandes "
							+ ChatColor.AQUA + "/tpaccept" + ChatColor.GREEN + " et " + ChatColor.AQUA + "/tpdeny");

					d.sendMessage(
							ChatColor.GREEN + "Cette requ�te sera active durant " + requestDuration + " secondes.");
					return true;

				} else {
					p.sendMessage(ChatColor.RED + "Usage: /tpa <joueur>");
					return true;
				}

			}

			if (cmd.getName().equalsIgnoreCase(tpAccCmd)) {
				if (teleportMap.containsKey(p)) {

					if (System.currentTimeMillis() / 1000 - teleportTimeMap.get(p) / 1000 < requestDuration) {
						p.sendMessage(ChatColor.GREEN + "Vous avez accept� la requ�te de " + ChatColor.YELLOW
								+ teleportMap.get(p).getName());
						teleportMap.get(p).sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GREEN
								+ " a accept� votre requ�te de t�l�portation. Vous avez �t� t�l�port�.");
						Location loc = p.getLocation();
						teleportMap.get(p).teleport(loc);

						teleportMap.remove(p);
						teleportTimeMap.remove(p);

						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Vous n'avez aucune requ�te de t�l�portation en attente.");
						return true;
					}

				} else {
					p.sendMessage(ChatColor.RED + "Vous n'avez aucune requ�te de t�l�portation en attente.");
					return true;
				}

			}

			if (cmd.getName().equalsIgnoreCase(tpDenCmd)) {

				p.sendMessage(teleportMap.toString());
				p.sendMessage(teleportTimeMap.toString());
				return true;
			}

		} else {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Cette commande ne peut pas �tre ex�cut�e depuis la console !");
			return true;
		}

		return false;
	}

}
