package com.ntcreations.DwarfFortress;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commands implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		utilities utils = new utilities();
		
		String name = sender.getName();
		Player player = Bukkit.getPlayer(name);
		PermissionUser user = PermissionsEx.getUser(player);
		String race = player.getMetadata("race").get(0).asString();
		String channel = player.getMetadata("chat").get(0).asString();
		String prefix = null;
		language lang = new language();
		
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
		
		if (cmd.getName().equalsIgnoreCase("test")) {
			if (args.length == 0){
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("forum")) {
			if (args.length == 4){
				if (args[0].equalsIgnoreCase("register")){
					MySql sql = new MySql();
					String ip = player.getAddress().getHostName();
					if (sql.getForum(name).equals("false")){
						if (args[1].equals(args[2])){
							sql.writeForum(name, race, args[1], args[3], ip);
							sender.sendMessage("you've successfully registered on the forums!");
							return true;
						}else{
							sender.sendMessage("passwords do not match!");
							return true;
						}
					}else{
						sender.sendMessage("you're already registered on the forums!");
						return true;
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("DF")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
				if (args.length == 0){
					sender.sendMessage("DFCore V0.1.0");
					sender.sendMessage("Designed by Patey for the DFRP community");
					return true;
				}
				if (args.length == 1){
					if (args[0].equalsIgnoreCase("reload")){
						DwarfFortress.getInstance().reloadConfig();
						sender.sendMessage("DFCore reloaded");
						return true;
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("eop")){
			FileConfiguration config = DwarfFortress.getInstance().getConfig();
			if (args[1].equalsIgnoreCase("+")){
		        StringBuilder sb = new StringBuilder();
		        for(int i = 2; i < args.length; i++) {
		            sb.append(args[i]);
		            if (i < args.length-1) sb.append(" ");
		        }
		        String message = sb.toString();
		        
		        List<String> emotes = config.getStringList("Chat.Emotes");
		        List<String> outputs = new ArrayList<String>();
		        for (int i = 0; i < emotes.size(); i++) {
		        	if (args[0].toLowerCase().equals(emotes.get(i))){
		        		outputs = config.getStringList("Chat.Emotes."+args[0]+".Outputs");
						outputs.add(message);
						config.set("Chat.Emotes."+args[0]+".Outputs", outputs);
						DwarfFortress.getInstance().saveConfig();
						sender.sendMessage("output added to "+args[0]);
						return true;
		        	}
		        }
		        sender.sendMessage("Emotion not found.");
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("emotes")){
			FileConfiguration config = DwarfFortress.getInstance().getConfig();
			if (args[0].equalsIgnoreCase("add")){
				List<String> emotes = config.getStringList("Chat.Emotes");
				emotes.add(args[1]);
				config.set("Chat.Emotes", emotes);
				DwarfFortress.getInstance().saveConfig();
				sender.sendMessage("Emote added to the list!");
				sender.sendMessage("Don't forget to add some outputs.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("remove")){
				List<String> emotes = config.getStringList("Chat.Emotes");
				for (int i = 0; i < emotes.size(); i++) {
					if (args[1].toLowerCase().equals(emotes.get(i))){
						emotes.remove(emotes.get(i));
						config.set("Chat.Emotes", emotes);
						DwarfFortress.getInstance().saveConfig();
						sender.sendMessage("Emote removed.");
						return true;
					}
				}
				sender.sendMessage("Emote not found.");
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("me")){
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
				List<String> emote = DwarfFortress.getInstance().getConfig().getStringList("Chat.Emotes");
				if(channel.equals("character")||channel.equals("common")){
					if (args.length == 1){
						for (String element : emote) {
							if (args[0].toLowerCase().equals(element)){
								List<String> fmessages = DwarfFortress.getInstance().getConfig().getStringList("Chat.Emotes."+cmd.getName().toLowerCase()+".Outputs");
								String[] messages = new String[fmessages.size()];
								messages = fmessages.toArray(messages);
								int rand = (int) (Math.random() * fmessages.size());
								
								for(Player k : Bukkit.getOnlinePlayers()){
									Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.inCharacter.MaxDistance.emote");
									if (k.getLocation().distance(player.getLocation()) <=  maxDist){
										if (k.getMetadata("chat").get(0).asString().equals(player.getMetadata("chat").get(0).asString())){
												k.sendMessage(ChatColor.DARK_PURPLE + sender.getName() + messages[rand]);
										}
									}
								}
								return true;
							}else{
								sender.sendMessage("sorry that emotion isn't in our list yet :(");
								sender.sendMessage("But if you mention that to a Story Teller we can have it added asap!");
								return true;
							}
						}
					}
					if (args.length > 1){
						sender.sendMessage("Sorry, only one emote at a time");
						return true;
					}
					if (args.length == 0){
							sender.sendMessage("You need to choose an emotion!");
							return true;
					}
				}else{
					sender.sendMessage("You're not in the proper chat channel.");
					return true;
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("channel")){
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
				if (args.length == 0){
					sender.sendMessage("Which channel? (nation, occ or ic)");
					return true;
				}
				if (args.length == 1){
					if (args[0].equalsIgnoreCase("OCC")){
						utils.setMetadata(player,"chat","common",DwarfFortress.getInstance());
						sender.sendMessage("channel now set to out of character!");
						return true;
					}
					if (args[0].equalsIgnoreCase("Nation")){
						utils.setMetadata(player,"chat","race",DwarfFortress.getInstance());
						sender.sendMessage("channel now set to National");
						return true;
					}
					if (args[0].equalsIgnoreCase("ICC")){
						utils.setMetadata(player,"chat","character",DwarfFortress.getInstance());
						sender.sendMessage("channel now set to in-character!");
						return true;
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("global")){
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
					if (args.length == 0){
						sender.sendMessage("Global chat on or off?");
						sender.sendMessage("usage: /global [on/off]");
						return true;
					}
					if (args.length == 1){
						if (args[0].equalsIgnoreCase("on")){
							 DwarfFortress.getInstance().getConfig().set("Chat.Global", "on");
							 sender.sendMessage("Global chat is now on.");
							 return true;
						}
						if (args[0].equalsIgnoreCase("off")){
							 DwarfFortress.getInstance().getConfig().set("Chat.Global", "off");
							 sender.sendMessage("Global chat is now off.");
							 return true;
						}
					}
				}
		}
		
		if (cmd.getName().equalsIgnoreCase("g")){
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < args.length; i++) {
	            sb.append(args[i]);
	            if (i < args.length-1) sb.append(" ");
	        }
	        String message = sb.toString();
	        
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
			if (DwarfFortress.getInstance().getConfig().getString("Chat.Global").equals("on")){
				if (args.length > 0){
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						p.sendMessage(ChatColor.RED + "[G]" + prefix + name + " : " + message);
					}
					return true;
				}
			}else{
				sender.sendMessage("Global is currently disabled");
				return true;
			}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("y")){
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < args.length; i++) {
	            sb.append(args[i]);
	            if (i < args.length-1) sb.append(" ");
	        }
	        String message = sb.toString();
	        
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
			if (channel.equals("character")){
				if (args.length > 0){
					String translate = lang.translate(message,race);
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getMetadata("chat").get(0).asString().equals(channel)){
							Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.inCharacter.MaxDistance.yell");
							if (p.getLocation().distance(player.getLocation()) <=  maxDist){
								if (p.getMetadata("race").get(0).asString().equals(race)){
									p.sendMessage(prefix + name + " : " + ChatColor.BOLD + message);
								}else{
									p.sendMessage(prefix + name + " : " + ChatColor.BOLD + translate);
								}
							}
						}
					}
					return true;
				}else{
					player.sendMessage("You need to enter something to yell");
					return true;
				}
			}
			if (channel.equals("common")){
				if (args.length > 0){
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getMetadata("chat").get(0).asString().equals(channel)){
							Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.commonLanguage.MaxDistance.yell");
							if (p.getLocation().distance(player.getLocation()) <=  maxDist){
								p.sendMessage(prefix + name + " : " + ChatColor.BOLD + message);
							}
						}
					}
					return true;
				}else{
					player.sendMessage("You need to enter something to yell");
					return true;
				}
			}
			if (channel.equals("race")){
				player.sendMessage("You don't need to yell, your people can hear you!");
				return true;
			}
		}
		}
		
		if (cmd.getName().equalsIgnoreCase("w")){
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < args.length; i++) {
	            sb.append(args[i]);
	            if (i < args.length-1) sb.append(" ");
	        }
	        String message = sb.toString();
	        
			if (channel.equals("character")){
				if (args.length > 0){
					String translate = lang.translate(message,race);
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getMetadata("chat").get(0).asString().equals(channel)){
							Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.inCharacter.MaxDistance.whisper");
							if (p.getLocation().distance(player.getLocation()) <=  maxDist){
								if (p.getMetadata("race").get(0).asString().equals(race)){
									p.sendMessage(prefix + name + " : " + ChatColor.ITALIC + message);
								}else{
									p.sendMessage(prefix + name + " : " + ChatColor.ITALIC + translate);
								}
							}
						}
					}
					return true;
				}else{
					player.sendMessage("You need to enter something to whisper");
					return true;
				}
			}
			if (channel.equals("common")){
				if (args.length > 0){
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getMetadata("chat").get(0).asString().equals(channel)){
							Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.commonLanguage.MaxDistance.whisper");
							if (p.getLocation().distance(player.getLocation()) <=  maxDist){
								if (p.getMetadata("race").get(0).asString().equals("kobold")){
									
								}else{
								p.sendMessage(prefix + name + " : " + ChatColor.ITALIC + message);
								}
							}
						}
					}
					return true;
				}else{
					player.sendMessage("You need to enter something to whisper");
					return true;
				}
			}
			if (channel.equals("race")){
				Double maxDist = DwarfFortress.getInstance().getConfig().getDouble("Chat.Race.MaxDistance.whisper");
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.getLocation().distance(player.getLocation()) <=  maxDist){
						if (p.getMetadata("race").get(0).asString().equals(race)){
							p.sendMessage(prefix + name + " : " + ChatColor.ITALIC + message);
						}
					}
				}
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("newrace")) {
			MySql sql = new MySql();
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
					if (args.length == 0){
						sender.sendMessage("Change a player's race");
						sender.sendMessage("usage /newrace [user] [race]");
						return true;
					}
					
					if (args.length == 1){
						sender.sendMessage("Change "+args[0]+" to which race?");
						sender.sendMessage("usage /newrace [user] [race]");
						return true;
					}
					
					if (args.length == 2){
						Player target = Bukkit.getPlayer(args[0]);
						if (args[1].equalsIgnoreCase("dwarf")){
							utils.setMetadata(target,"race","dwarf",DwarfFortress.getInstance());
							sql.editUser("race", "dwarf", target.getName());
							sender.sendMessage(target+"'s race changed to Dwarf.");
							return true;
						}
						if (args[1].equalsIgnoreCase("elf")){
							utils.setMetadata(target,"race","elf",DwarfFortress.getInstance());
							sql.editUser("race", "elf", target.getName());
							sender.sendMessage(target+"'s race changed to Elf.");
							return true;
						}
						if (args[1].equalsIgnoreCase("human")){
							utils.setMetadata(target,"race","human",DwarfFortress.getInstance());
							sql.editUser("race", "human", target.getName());
							sender.sendMessage(target+"'s race changed to Human.");
							return true;
						}
						if (args[1].equalsIgnoreCase("kobold")){
							utils.setMetadata(target,"race","kobold",DwarfFortress.getInstance());
							sql.editUser("race", "kobold", target.getName());
							sender.sendMessage(target+"'s race changed to Kobold.");
							return true;
						}
						sender.sendMessage(target+"'s race couldn't be changed to "+args[1]+".");
						return true;
					}
					
				}
		}
		
		if (cmd.getName().equalsIgnoreCase("Race")) {
			MySql sql = new MySql();
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
				return true;
			} else {
				if (args.length == 0){
					if (race.equals("dwarf")){
							 sender.sendMessage("You're a Dwarf, how could you forget!?");
							 return true;
					}
					if (race.equals("elf")){
							 sender.sendMessage("You're an Elf, how could you forget!?");
							 return true;
					}
					if (race.equals("human")){
							 sender.sendMessage("You're a Human, how could you forget!?");
							 return true;
					}
					if (race.equals("kobold")){
							 sender.sendMessage("You're a Koboldian, how could you forget!?");
							 return true;
					}
					if (race.equals("none")) {
						sender.sendMessage("We need to know what you are.");
						sender.sendMessage("Be you Dwarven,Elven,Human or Koboldian?");
						sender.sendMessage("~/race selection~");
						return true;
					}
				}
			if (args.length == 1){
			if (race.equals("none")){
				if (args[0].equalsIgnoreCase("Dwarven")){
						sender.sendMessage("Enjoy your life as a Dwarf!");
						String newRace = "dwarf";
						sql.writeUser(name,newRace);
						utils.setMetadata(player,"race",newRace,DwarfFortress.getInstance());
						user.addGroup("dwarf");
						return true;
				}
				if (args[0].equalsIgnoreCase("Elven")){
						sender.sendMessage("Enjoy your life as an Elf!");
						String newRace = "elf";
						sql.writeUser(name,newRace);
						utils.setMetadata(player,"race",newRace,DwarfFortress.getInstance());
						user.addGroup("elf");
						return true;
				}
				if (args[0].equalsIgnoreCase("Human")){
						sender.sendMessage("Enjoy your life as a Human!");
						String newRace = "human";
						sql.writeUser(name,newRace);
						utils.setMetadata(player,"race",newRace,DwarfFortress.getInstance());
						user.addGroup("human");
						return true;
				}
				if (args[0].equalsIgnoreCase("Koboldian")){
						sender.sendMessage("Enjoy your life as a Kobold!");
						String newRace = "kobold";
						sql.writeUser(name,newRace);
						utils.setMetadata(player,"race",newRace,DwarfFortress.getInstance());
						user.addGroup("kobold");
						return true;
				}
			}else{
				sender.sendMessage("You can't change who you are, be yourself :P");
				return true;
			}
			}
			}
		}
		return false;
	}
}
