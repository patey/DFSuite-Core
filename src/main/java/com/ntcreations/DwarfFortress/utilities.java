package com.ntcreations.DwarfFortress;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class utilities {
	
	public String[] playersOn(){
		ArrayList<String> count = new ArrayList<String>();
		for(Player online : Bukkit.getServer().getOnlinePlayers()) {
		count.add(online.getName());
		}
		String[] online = count.toArray(new String[count.size()]);
		
		return online;
	}
	public void setMetadata(Entity player, String key, Object value, Plugin plugin){
		 player.setMetadata(key,new FixedMetadataValue(plugin,value));
		}
}
