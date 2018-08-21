package me.alcibiade.essentialsp.Commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.alcibiade.essentialsp.EssentialsP;
import me.alcibiade.essentialsp.Configurations.PlayersConfiguration;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class AdminCheck extends CommandExecute implements CommandExecutor, Listener {

	PlayersConfiguration cfg = new PlayersConfiguration(EssentialsP.GetPlugin());

	/*
	 * private Plugin plugin;
	 * 
	 * public AdminCheck(Plugin plugin) { this.plugin = plugin; }
	 */

	public String checkCmd = "check";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase(checkCmd)) {
				Player p = (Player) sender;
				if (args.length > 0) {

					Player d = Bukkit.getServer().getPlayer(args[0]);
					UUID dUUID;

					try {
						dUUID = d.getUniqueId();
						cfg.getPlayers().getString(dUUID.toString());
					} catch (NullPointerException e) {
						p.sendMessage(ChatColor.RED + "Le joueur spécifié n'a pas pu être trouvé.");
						return true;
					}

					Inventory inv = Bukkit.getServer().createInventory(null, 27,
							ChatColor.RED + "Infos joueur : " + ChatColor.DARK_RED + "" + ChatColor.BOLD + p.getName());

					ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1);
					ItemStack itemUUID = new ItemStack(Material.NAME_TAG, 1);
					ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					SkullMeta skullMeta = (SkullMeta) playerSkull.getItemMeta();

					ArrayList<String> lore = new ArrayList<String>();
					ItemMeta meta = empty.getItemMeta();

					meta.setDisplayName(" ");
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					empty.setItemMeta(meta);

					lore.add(ChatColor.WHITE + dUUID.toString());

					meta = itemUUID.getItemMeta();
					meta.setLore(lore);
					meta.setDisplayName(ChatColor.RED + "UUID du joueur");
					itemUUID.setItemMeta(meta);

					skullMeta.setOwningPlayer((OfflinePlayer) d);
					skullMeta.setDisplayName(ChatColor.GOLD + d.getName());

					lore = new ArrayList<String>();
					if (d.isOnline()) {
						lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "En ligne");
					} else {
						lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Hors-ligne");
					}
					skullMeta.setLore(lore);
					playerSkull.setItemMeta(skullMeta);

					for (int i = 0; i < 9; i++) {
						inv.setItem(i, empty);
						inv.setItem(i + 18, empty);
					}

					inv.setItem(9, itemUUID);
					inv.setItem(13, playerSkull);

					p.openInventory(inv);
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Merci de préciser un joueur.");
					return true;
				}

			}
		} else {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Cette commande ne peut pas être exécutée depuis la console.");
			return true;
		}
		return false;
	}

}