package me.alcibiade.essentialsp;

import java.io.File;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import me.alcibiade.essentialsp.Commands.AdminCheck;
import me.alcibiade.essentialsp.Commands.Messages;
import me.alcibiade.essentialsp.Commands.Spawn;
import me.alcibiade.essentialsp.Commands.Teleport;
import me.alcibiade.essentialsp.Commands.Warps;
import me.alcibiade.essentialsp.Configurations.PlayersConfiguration;
import me.alcibiade.essentialsp.Events.EventsClass;
import net.md_5.bungee.api.ChatColor;

public class EssentialsP extends JavaPlugin {

	private static EssentialsP instance;
	
	public EventsClass eventClass = new EventsClass();
	public Spawn spawnCmds = new Spawn(this);
	public Messages messagesCmds = new Messages(this);
	public Warps warpsCmds = new Warps(this);

	public void onEnable() {
		// saveDefaultConfig();
		loadConfig();
		loadPlayers();
		getServer().getPluginManager().registerEvents(new EventsClass(), this);
		setCommandsExecutors();
		instance = this;
		Messages.lastMessage = new HashMap<>();
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "EssentialsP > Plugin activé !");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "EssentialsP > Plugin désactivé !");
		saveConfig();
	}

	public void loadConfig() {
		File cfg = new File(getDataFolder() + File.separator + "config.yml");
		if (!cfg.exists()) {
			getConfig().addDefault("Location.spawn.world", "default");
			getConfig().addDefault("Location.spawn.x", 0);
			getConfig().addDefault("Location.spawn.y", 0);
			getConfig().addDefault("Location.spawn.z", 0);
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
		}
	}
	
	public void setCommandsExecutors() {
		getCommand(spawnCmds.spawnCmd).setExecutor(spawnCmds);
		getCommand(spawnCmds.setSpawnCmd).setExecutor(spawnCmds);
		getCommand(messagesCmds.msgCmd).setExecutor(messagesCmds);
		getCommand(messagesCmds.replyCmd).setExecutor(messagesCmds);
		getCommand("check").setExecutor(new AdminCheck());
		getCommand(warpsCmds.warpCmd).setExecutor(warpsCmds);
		getCommand(warpsCmds.warpsCmd).setExecutor(warpsCmds);
		getCommand(warpsCmds.setWarpCmd).setExecutor(warpsCmds);
		getCommand(warpsCmds.delWarpCmd).setExecutor(warpsCmds);
		getCommand(new Teleport().tpReqCmd).setExecutor(new Teleport());
		getCommand(new Teleport().tpAccCmd).setExecutor(new Teleport());
		getCommand(new Teleport().tpDenCmd).setExecutor(new Teleport());
		
	}
	
	public void loadPlayers() {
		PlayersConfiguration pConfig = new PlayersConfiguration(this);
		
		pConfig.setup();
		pConfig.savePlayers();
	}
	
	public static EssentialsP GetPlugin() {
		return instance;
	}

}
