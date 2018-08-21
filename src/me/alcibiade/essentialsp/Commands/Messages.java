package me.alcibiade.essentialsp.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class Messages extends CommandExecute implements CommandExecutor, Listener {

	private Plugin plugin;

	public Messages(Plugin plugin) {
		this.plugin = plugin;
	}

	public static HashMap<Player, Player> lastMessage = new HashMap<>();

	String cmdName;
	public String msgCmd = "message";
	public String replyCmd = "reply";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase(msgCmd)) {
			if (sender instanceof Player) {

				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "Usage de la commande : /" + cmd.getName() + " <joueur> <message>");
					return false;
				} else {
					Player d = plugin.getServer().getPlayer(args[0]);
					if (d != null) {
						args[0] = "";
						String msg = String.join(" ", args);
						sendPrivateMessage(p, d, msg);
						return true;
					} else {
						return false;
					}
				}

			} else {

				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase(replyCmd)) {
			if (!(lastMessage.get(p) != null)) {
				lastMessage.put(p, p);
				
			}
			sendPrivateMessage(p, lastMessage.get(p), String.join(" ", args));
			return true;
		}

		return false;
	}

	public void sendPrivateMessage(Player p, Player d, String msg) {
		p.sendMessage(ChatColor.GREEN + "[Moi] > " + d.getName() + " : " + ChatColor.GRAY + msg);
		d.sendMessage(ChatColor.GREEN + p.getName() + " > [Moi]" + " : " + ChatColor.GRAY + msg);

		lastMessage.put(p, d);
		lastMessage.put(d, p);
	}
}
