package me.alcibiade.essentialsp.Configurations;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class PlayersConfiguration {
	
	private Plugin plugin;
	
	public PlayersConfiguration(Plugin plugin) {
		this.plugin = plugin;
	} 

	
	public static FileConfiguration playersCfg;
	public static File playersFile;
	
	
	public void setup() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		playersFile = new File(plugin.getDataFolder(), "players.yml");
		
		if(!playersFile.exists()) {
			try {
				playersFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le fichier players.yml a été créé.");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Le fichier players.yml n'a pas pu être créé.");
			}
		}
		
		playersCfg = YamlConfiguration.loadConfiguration(playersFile);
	}
	
	public FileConfiguration getPlayers() {
		return playersCfg;
	}
	
	public void savePlayers() {
		try {
			playersCfg.save(playersFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le fichier players.yml a été sauvegardé.");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Le fichier players.yml n'a pas pu être sauvegardé.");
		}
	}
	
	public void reloadPlayers() {
		playersCfg = YamlConfiguration.loadConfiguration(playersFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le fichier players.yml a été rechargé.");
	}
	
}
