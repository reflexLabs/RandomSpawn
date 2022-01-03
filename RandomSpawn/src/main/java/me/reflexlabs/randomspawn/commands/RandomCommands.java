package me.reflexlabs.randomspawn.commands;

import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;
import me.reflexlabs.randomspawn.utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class RandomCommands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length <= 0) {
            RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().sendHelp(sender);
        } else {
        	if(args[0].equalsIgnoreCase("setpoint") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("createpoint") || args[0].equalsIgnoreCase("setspawn")) {
        		if(sender.hasPermission("randomspawn.create") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
	        		if (args.length >= 2) {
	                    try {
	                        boolean exists = false;
	                        for (Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
	                            if (p.getId().equals(args[1])) {
	                                sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointAlreadyExists));
	                                exists = true;
	                            }
	                        }
	                        if (exists) {
	                            return true;
	                        }
	                        if (RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().createPoint((Player)sender, args[1])) {
	                            sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointCreatedMessage.replace("{point}", Functions.formatMessage(args[1]))));
	                            return true;
	                        }
	                        return true;
	                    }
	                    catch (Exception wrong) {
	                        sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [id]"));
	                        return true;
	                    }
	                } else {
	                	sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [id]"));
	                }
        		}
            } else if(args[0].equalsIgnoreCase("removepoint") || args[0].equalsIgnoreCase("deletepoint") || args[0].equalsIgnoreCase("remove")) {
            	if(sender.hasPermission("randomspawn.remove") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
            		if (args.length >= 2) {
                        try {
                            boolean exists = false;
                            for (Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
                                if (p.getId().equals(args[1])) {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                            	sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists));
                                return true;
                            }
                            if (RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().removePoint((Player)sender, args[1])) {
                                sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointRemoveMessage.replace("{point}", Functions.formatMessage(args[1]))));
                                return true;
                            }
                            return true;
                        }
                        catch (Exception wrong) {
                            sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [id]"));
                            return true;
                        }
                    } else {
                    	sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [id]"));
                    }
            	}
            } else if(args[0].equalsIgnoreCase("teleport")) {
            	if(sender.hasPermission("randomspawn.teleport") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
            		try {
            			String pointID;
            			String target;

            			try {
            				pointID = args[1];
            			} catch(Exception noPoint) {
            				sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [pointID] &7[Target]"));
            				//noPoint.printStackTrace();
            				return true;
        				}

            			try {
            				target = args[2];
            			} catch(Exception noPlayerSpecified) {
            				if(sender instanceof Player) {
        						RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport((Player) sender,RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
        						return true;
    						} else {
    							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
    						}
            				//noPlayerSpecified.printStackTrace();
            				return true;
        				}
            			
            			if(target == null) {
            				// Self use
            				if(sender instanceof Player) {
        						RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport((Player) sender,RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
        						return true;
    						} else {
    							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
    						}
            				return true;
            			} else {
            				if(target.equalsIgnoreCase("@a") || target.equalsIgnoreCase("@e") || target.equalsIgnoreCase("@all")  || target.equalsIgnoreCase("@everyone")) {
            					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            						Point randomPoint = RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID);
        							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(p, randomPoint);
        						}
            				} else if(target.equalsIgnoreCase("@me") || target.equalsIgnoreCase("@m")) {
            					// Self use
            					if(sender instanceof Player) {
            						RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport((Player) sender,RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
            						return true;
        						} else {
        							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
        						}
                			} else {
                				try {
                					if(Bukkit.getServer().getPlayer(target).isOnline()){
                						if(!Bukkit.getServer().getPlayer(target).isDead()) {
                							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(Bukkit.getServer().getPlayer(target),RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
                    						return true;
                						} else {
                							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
                						}
                					} else {
                						sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().isOffline));
                					}
                				} catch(Exception offlinePlayer) {
            						sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().isOffline));
                				}
                			}
            			}
            		} catch(Exception noPlayerSpecified) {
            			noPlayerSpecified.printStackTrace();
    					// No player specified.
    				}
            	}
        	}/* else if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("goto") || args[0].equalsIgnoreCase("tele")) {
            	if(sender.hasPermission("randomspawn.teleport") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
            		try {
                		String pointID = args[1];
                		if(RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().isPointExists(pointID)) {
                			if(sender instanceof Player) {
                				try {
                					String playerName = args[2];
                					if(playerName.equalsIgnoreCase("@a") || playerName.equalsIgnoreCase("@e") || playerName.equalsIgnoreCase("@all")  || playerName.equalsIgnoreCase("@everyone")) {
                						for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(p,RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
                						}
                					} else {
                						if(Bukkit.getServer().getPlayer(playerName).isOnline()){
                    						if(!Bukkit.getServer().getPlayer(playerName).isDead()) {
                    							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(Bukkit.getServer().getPlayer(playerName),RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));
                        						return true;
                    						} else {
                    							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
                    						}
                    					} else {
                    						sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().isOffline));
                    					}
                					}
                				} catch(Exception noPlayerSpecified) {
                					// No player specified.
                				}
                				RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(((Player) sender),RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));	
                        	} else {
                        		sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
                        	}
                		} else {
                    		sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists));
                		}
                	} catch(Exception wrong) {
                		sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [pointID] &7[Player] (optional)"));
                	}
            	}
            } else if(args[0].equalsIgnoreCase("force")) {
            	if(sender.hasPermission("randomspawn.force") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
            		try {
                		String playerName = args[1];
    					if(playerName.equalsIgnoreCase("@a") || playerName.equalsIgnoreCase("@e") || playerName.equalsIgnoreCase("@all")  || playerName.equalsIgnoreCase("@everyone")) {
    						for(Player p : Bukkit.getServer().getOnlinePlayers()) {
    							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(p,RandomSpawn.getInstance().getRandomManager().getDataManager().getRandomSpawnPoint());
    						}
    					} else {
    						if(Bukkit.getServer().getPlayer(playerName).isOnline()){
        						if(!Bukkit.getServer().getPlayer(playerName).isDead()) {
        							RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(Bukkit.getServer().getPlayer(playerName),RandomSpawn.getInstance().getRandomManager().getDataManager().getRandomSpawnPoint());
            						return true;
        						} else {
        							sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
        						}
        					} else {
        						sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().isOffline));
        					}
    					}
    				} catch(Exception noSpecified) {
    					sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " &7[Player] (optional: @a/@e/@all/@everyone)"));
    				}
            	}
            	
            }*/ else if(args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("change") || args[0].equalsIgnoreCase("modify")) {
            	if(sender.hasPermission("randomspawn.edit") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
            		try {
                		String pointID = args[1];
                		if(RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().isPointExists(pointID)) {
                			if(sender instanceof Player) {
                            	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().openGUI(((Player) sender),RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(pointID));	
                        	} else {
                        		sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable));
                        	}
                		} else {
                    		sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists));
                		}
                	} catch(Exception wrong) {
                		sender.sendMessage(Functions.formatMessage("&7&lUsage: &f/RandomSpawn " + args[0] + " [pointID]"));
                	}
            	}
            } else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("points") || args[0].equalsIgnoreCase("spawns")) {
            	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().sendList(sender);
            } else if(args[0].equalsIgnoreCase("disable")) {
            	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().disableAffect(sender);
            } else if(args[0].equalsIgnoreCase("help")) {
            	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().sendHelp(sender);
            } else if(args[0].equalsIgnoreCase("info")) {
            	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().sendInfo(sender);
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("randomspawn.reload") || sender.isOp()) {
                	Bukkit.getPluginManager().getPlugin("RandomSpawn").reloadConfig();
                    RandomSpawn.getInstance().getRandomManager().getDataManager().getConfig().loadConfig();
                    RandomSpawn.getInstance().getRandomManager().getDataManager().getDatabase().reloadData();
                    sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().reloadMessage));
 
                } else {
                	sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().noPermission));
                }
            }
            else if (args[0].equalsIgnoreCase("save")) {
                if (sender.hasPermission("banking.save") || sender.isOp()) {
                    RandomSpawn.getInstance().getRandomManager().getDataManager().saveData();
                    sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().saveMessage));             
                } else {
                	sender.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().noPermission));
                }
            }
        }
        return true;
    }
}
