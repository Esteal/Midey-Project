package fr.midey.pvpbox.interaction;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.pvpbox.PvpBox;
import fr.midey.pvpbox.commands.Commands;
import fr.midey.pvpbox.stuff.Stuff;

public class Interaction implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Stuff.clearALL(player);
		player.teleport(Commands.arene);
		PvpBox.cooldown.put(player.getUniqueId(), Integer.valueOf(1));
		player.setHealthScale(20.0);
		player.setHealth(20.0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true));
		player.getInventory().setItem(4,Stuff.EnchantedFeed(new ItemStack(Material.COMPASS), "�4S�l�ction du kit"));
	}
	
	//Action effectu� au respawn du joueur
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Stuff.clearALL(player);
		player.teleport(Commands.arene);
		PvpBox.cooldown.put(player.getUniqueId(), Integer.valueOf(1));
		player.setHealthScale(20.0);
		player.setHealth(20.0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true));
		player.getInventory().setItem(4,Stuff.EnchantedFeed(new ItemStack(Material.COMPASS), "�4S�l�ction du kit"));
	}
	
	//nerf de force
	@EventHandler
	public void strNerf(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				for (PotionEffect effect: player.getActivePotionEffects()) {
					if(effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
						event.setDamage(event.getDamage()/2);
						player.sendMessage("Force nerf");
						return;
					}
				}
				return;
			}
			return;
		}
		return;
	}
	
	//force 3 au berserker
	
	@EventHandler
	public void berserkphase23(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack it = player.getItemInHand();
				if (it.getType() == null) return;
				if(it.getType() == Material.STONE_AXE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�2Hache du bourrin")) {
					if (player.getHealth() <= 8.0) {
						Stuff.berserker2(player);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 1));
					}
				}
				if(it.getType() == Material.DIAMOND_AXE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�2Hache du �4GROS �2bourrin")) {
					if (player.getHealth() <= 20.0) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 2, 1));
					}
				}
			}				
		}
	}
	@EventHandler
	public void berserkphase2(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if(event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				ItemStack it = player.getItemInHand();
				if (it.getType() == null) return;
				if(it.getType() == Material.STONE_AXE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�2Hache du bourrin")) {
					if (player.getHealth() <= 8.0) {
						Stuff.berserker2(player);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 1));
					}
				}
				if(it.getType() == Material.DIAMOND_AXE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�2Hache du �4GROS �2bourrin")) {
					if (player.getHealth() <= 20.0) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 2, 1));
					}
				}
			}				
		}
	}
	
	//coup d'�p�e de l'assassin
	@EventHandler
	public void invi(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if (event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				ItemStack it = player.getItemInHand();
				if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�7Coup en douce")) {
					for (PotionEffect effect : player.getActivePotionEffects()) {
						effect.getType();
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					Stuff.assassin(player);
					PvpBox.cooldown.put(player.getUniqueId(), Integer.valueOf(20));
					return;
				}
			}
		}
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) return;
		
		//Items sp�ciaux des kits !
		
		//Item de l'archer
		
		else if(it.getType() == Material.SUGAR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�9Sniffff moi �a mon rheuf")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 15, 3));
				it.setAmount(it.getAmount() -1);
				player.getInventory().setItemInHand(it);
				player.setHealth(20.0);
				return ;
			}
		}
		
		//Item du berserker
		
		else if(it.getType() == Material.BLAZE_POWDER && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�2C'est d'la bonne mec !")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if(player.getHealth() <= 5.0) {
					for (PotionEffect effect : player.getActivePotionEffects()) {
						effect.getType();
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					}
					Stuff.berserker2(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 1, 3));
					return ;
				}
			}
			return;
		}
		
		//Item de monk
		
		else if(it.getType() == Material.IRON_INGOT && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�1Deviens un racailloux !!!!")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if(!PvpBox.cooldown.containsKey(player.getUniqueId())) {
					for (PotionEffect effect : player.getActivePotionEffects()) {
							effect.getType();
							player.removePotionEffect(PotionEffectType.SLOW);
							player.removePotionEffect(PotionEffectType.SPEED);
							player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.REGENERATION);
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
						player.getInventory().setItemInHand(Stuff.EnchantedFeed(new ItemStack(Material.BLAZE_ROD), "�1Pikacheat"));
						return ;
					}
					else {
						player.sendMessage("Veuillez attendre !");
						return;
					}
			}
		}
		
		//item du monk 2
		
		else if(it.getType() == Material.BLAZE_ROD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�1Pikacheat")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.SLOW);
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.REGENERATION);
				}
				PvpBox.cooldown.put(player.getUniqueId(), Integer.valueOf(2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*2, 254));
				player.getInventory().setItemInHand(Stuff.EnchantedFeed(new ItemStack(Material.IRON_INGOT), "�1Deviens un racailloux !!!!"));
				return;
			}
		}
		else if(it.getType() == Material.EYE_OF_ENDER && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�7Furtivit�")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if(!PvpBox.cooldown.containsKey(player.getUniqueId())) {
					for (PotionEffect effect : player.getActivePotionEffects()) {
						effect.getType();
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					Stuff.assassin2(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*30, 0));
					return;
				}
				else {
					player.sendMessage("Veuillez attendre");
				}
			}
		}
			
		//item de l'assassin
		
		else if(it.getType() == Material.CHEST && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�7Ton stuff est l� dedans !")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
				}
				Stuff.assassin(player);
				PvpBox.cooldown.put(player.getUniqueId(), Integer.valueOf(20));
				return;
			}
			else {
				player.sendMessage("Veuillez attendre");
			}
		}
		
		//item de paladin
		
		else if(it.getType() == Material.IRON_BLOCK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("R�siste plus !")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.SLOW);
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				}
				return;
				//passe en r�si 2, doit passer en resi 1 avec le lingot (paladin2)
			}
		}
		
		else if(it.getType() == Material.COMPASS && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("�4S�l�ction du kit")) {
			
			Inventory selec = Bukkit.createInventory(null, 45, "�8Menu de s�l�ction du Kit");
			
			selec.setItem(20, Stuff.EnchantedFeed(new ItemStack(Material.DIAMOND_AXE), "�2Kit Berserker"));
			selec.setItem(21, Stuff.EnchantedFeed(new ItemStack(Material.BLAZE_ROD), "�1Kit Monk"));
			selec.setItem(23, Stuff.EnchantedFeed(new ItemStack(Material.GOLD_SWORD), "�7Kit Assassin"));
			selec.setItem(24, Stuff.EnchantedFeed(new ItemStack(Material.BOW), "�9Kit Archer"));
			player.openInventory(selec);
		}
	}
	
	@EventHandler
	public void onInventory(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack it = event.getCurrentItem();
		
		if (it == null) return;
		
		if (inv.getName().equalsIgnoreCase("�8Menu de s�l�ction du Kit")) {
			event.setCancelled(true);
			player.closeInventory();
			
			switch(it.getType()) {
			
				case DIAMOND_AXE:
					Stuff.berserker(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*2, 254));
					player.teleport(Commands.arene);
					player.setHealth(20.0);
					break;
				case BOW:
					Stuff.archer(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*2, 254));
					player.teleport(Commands.arene);
					player.setHealth(20.0);
					break;
				case GOLD_SWORD:
					Stuff.assassin(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*2, 254));
					player.teleport(Commands.arene);
					player.setHealth(20.0);
					break;
				case BLAZE_ROD:
					Stuff.monk(player);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*2, 254));
					player.teleport(Commands.arene);
					player.setHealth(20.0);
					break;
				default:
					break;
				
			}
		}
	}
}
