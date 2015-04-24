package com.ntcreations.DwarfFortress;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DwarfFortress extends JavaPlugin {
	 private static DwarfFortress instance = null;
	
	@Override
	public void onEnable() {
		instance = this;
		 registerEvents(this,new PlayerEvents(),new ChatEvents());
		 
	    getCommand("DF").setExecutor(new Commands());
	    getCommand("Race").setExecutor(new Commands());
	    getCommand("newrace").setExecutor(new Commands());
	    getCommand("global").setExecutor(new Commands());
	    getCommand("g").setExecutor(new Commands());
	    getCommand("y").setExecutor(new Commands());
	    getCommand("w").setExecutor(new Commands());
	    getCommand("channel").setExecutor(new Commands());
	    getCommand("forum").setExecutor(new Commands());
	    getCommand("me").setExecutor(new Commands());
	    getCommand("emotes").setExecutor(new Commands());
	    getCommand("eop").setExecutor(new Commands());
	    initconfig();
	    if (getConfig().getString("Chat.Global").equals("on")){
	    	getConfig().set("Chat.Global", "off");
	    	getLogger().info("Global chat was left on,it's off now");
	    }
	    if (getConfig().getBoolean("MySql.Enabled") == true){
	    	getLogger().info("MySql connecting...");
	    	MySql sql = new MySql();
	    	sql.sqlInit();
	    	getLogger().info("MySql connected!");
	    }else{
	    	getLogger().info("sorry, no file storage yet. please enable mysql in the config.");
	    }
	    getLogger().info("DFCore Initialized");
	}
 
	@Override
	public void onDisable() {
		getLogger().info("DFCore Unloaded");
	}
	 public static DwarfFortress getInstance() {
		 return instance;
		 }

	 public static String[] getConnection(){
				String[] xsettings = new String[4];
					xsettings[0] = getInstance().getConfig().getString("MySql.Host");
					xsettings[1] = getInstance().getConfig().getString("MySql.DataBase");
					xsettings[2] = getInstance().getConfig().getString("MySql.User");
					xsettings[3] = getInstance().getConfig().getString("MySql.Password");
					return xsettings;
	 }
	 public static String[] getForumCon(){
			String[] fsettings = new String[4];
				fsettings[0] = getInstance().getConfig().getString("MySql.Forum.Host");
				fsettings[1] = getInstance().getConfig().getString("MySql.Forum.DataBase");
				fsettings[2] = getInstance().getConfig().getString("MySql.Forum.User");
				fsettings[3] = getInstance().getConfig().getString("MySql.Forum.Password");
				return fsettings;
	}
	 
	private void initconfig(){
	    FileConfiguration config = getConfig();
	    
	    List<String> emotes = config.getStringList("Chat.Emotes");
	    emotes.add("tired");
	    List<String> outputs = config.getStringList("Chat.Emotes.tired.Outputs");
	    outputs.add("yawns");
	    outputs.add("drinks coffee");
	    
	    config.addDefault("Chat.Global","off");
	    config.addDefault("Chat.DefaultChannel","common");
	    config.addDefault("Chat.inCharacter.MaxDistance.whisper",5);
	    config.addDefault("Chat.inCharacter.MaxDistance.talk",10);
	    config.addDefault("Chat.inCharacter.MaxDistance.emote",15);
	    config.addDefault("Chat.inCharacter.MaxDistance.yell",20);
	    config.addDefault("Chat.commonLanguage.MaxDistance.whisper",5);
	    config.addDefault("Chat.commonLanguage.MaxDistance.talk",10);
	    config.addDefault("Chat.commonLanguage.MaxDistance.emote",15);
	    config.addDefault("Chat.commonLanguage.MaxDistance.yell",20);
	    config.addDefault("Chat.Emotes", emotes);
	    config.addDefault("Chat.Emotes.tired.Outputs", outputs);
	    
	    config.addDefault("MySql.Enabled", true);
	    config.addDefault("MySql.Host", "ntcreations.com");
	    config.addDefault("MySql.DataBase", "DwarfFortress");
	    config.addDefault("MySql.Port", "3306");
	    config.addDefault("MySql.User", "root");
	    config.addDefault("MySql.Password", "password");
	    config.addDefault("MySql.Forum.Host", "ntcreations.com");
	    config.addDefault("MySql.Forum.DataBase", "DwarfFortress");
	    config.addDefault("MySql.Forum.User", "root");
	    config.addDefault("MySql.Forum.Password", "password");
	    
	    config.options().copyDefaults(true);
	    saveConfig();
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
		}
	
}
