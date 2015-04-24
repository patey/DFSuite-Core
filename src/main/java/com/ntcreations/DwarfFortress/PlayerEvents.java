package com.ntcreations.DwarfFortress;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerEvents implements Listener{
	
	@EventHandler
	public void join(PlayerJoinEvent event){
		String column = "race";
		Player player = event.getPlayer();
		String name = player.getName();
		PermissionUser user = PermissionsEx.getUser(player);
		MySql sql = new MySql();
		String getrace = sql.getUser(name, column);
		utilities utils = new utilities();
		utils.setMetadata(player,"race",getrace,DwarfFortress.getInstance());
		utils.setMetadata(player,"chat",DwarfFortress.getInstance().getConfig().getString("Chat.DefaultChannel"),DwarfFortress.getInstance());
		if (! player.getMetadata("race").get(0).asString().equals("none")){
			user.addGroup(player.getMetadata("race").get(0).asString());
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event){
		int counter = 0;
		 Player p = event.getPlayer();
		 Location q = event.getFrom();
		 Location w = event.getTo();
		Player player = event.getPlayer();
		String race = player.getMetadata("race").get(0).asString();
		if (race.equals("none")){

			 if(p instanceof Player){
				 if(q.getBlockX() != w.getBlockX() || q.getBlockY() != w.getBlockY() || q.getBlockZ() != w.getBlockZ()){
				 event.setTo(q);
				if (counter == 0){
					player.sendMessage("Don't forget to choose your race! for more info type /race");
					counter = 100;
				}else{
					counter--;
				}
				 }
			 }
		}
	}
	
}
