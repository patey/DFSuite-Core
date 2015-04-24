package com.ntcreations.DwarfFortress;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvents implements Listener{
    
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		String race = sender.getMetadata("race").get(0).asString();
		String prefix = null;
		String message = event.getMessage();
		language lang = new language();
		String remessage = lang.translate(message,race);
		String cchat = sender.getMetadata("chat").get(0).asString();
		
		if (race.equals("dwarf")){
			prefix = ChatColor.RED+"[Dw]";
		}
		if (race.equals("elf")){
			prefix = ChatColor.GREEN+"[El]";
		}
		if (race.equals("human")){
			prefix = ChatColor.BLUE+"[Hu]";
		}
		if (race.equals("kobold")){
			prefix = ChatColor.DARK_PURPLE+"[Ko]";
		}
		if (race.equals("none")){
			prefix = ChatColor.GRAY+"[N/A]";
		}
		
		if (cchat.equals("race")){
			for(Player k : event.getRecipients()){
				if (race.equals(k.getMetadata("race").get(0).asString())){
					k.sendMessage("[Nation]" + prefix + ChatColor.RESET + sender.getName() +" : "+message);
				}
			}
			event.getRecipients().clear();
			event.setCancelled(true);
		}
		
		if (cchat.equals("character")){
			Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.inCharacter.MaxDistance.talk");
			for(Player k : event.getRecipients()){
				if (k.getLocation().distance(sender.getLocation()) <=  maxDist){
					if (k.getMetadata("chat").get(0).asString().equals(cchat)){
						if (race.equals(k.getMetadata("race").get(0).asString())){
							k.sendMessage("[IC]"+prefix + ChatColor.RESET + sender.getName() +" : "+message);
						}
						if (! race.equals(k.getMetadata("race").get(0).asString())){
							k.sendMessage("[IC]" + prefix + ChatColor.RESET + sender.getName() +" : "+remessage);
						}
					}
				}
			}
			event.getRecipients().clear();
			event.setCancelled(true);
		}
		
		if (cchat.equals("common")){
			Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.commonLanguage.MaxDistance.talk");
			for(Player k : event.getRecipients()){
				if (k.getLocation().distance(sender.getLocation()) <=  maxDist){
					if (k.getMetadata("chat").get(0).asString().equals(cchat)){
						if (race.equals("kobold")){
							
						}else{
							k.sendMessage("[OCC]" + prefix + ChatColor.RESET + sender.getName() +" : "+message);
						}
					}
				}
			}
			event.getRecipients().clear();
			event.setCancelled(true);
		}
		
	}
}
