package com.bliss;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BlissPlugin extends JavaPlugin implements Listener, CommandExecutor {

    private final Map<UUID, String> playerAbilities = new HashMap<>();
    private final List<String> abilityList = Arrays.asList(
        "Fireball", "SuperJump", "Heal", "Speed", "Invisibility", "Strength", "Teleport", 
        "Lightning", "Explosion", "Levitate", "WaterBreathing", "FireResistance", "NightVision", 
        "Poison", "Freeze", "Knockback", "Diamond", "EnderPearl", "Blindness", "Nausea", 
        "Slowness", "Haste", "Regen", "Absorption", "Saturation", "JumpBoost", "Dolphin", 
        "Conduit", "Resistance", "Wither"
    );

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetability").setExecutor(this);
        getCommand("abilityinfo").setExecutor(this);
        getLogger().info("BlissPlugin 30-Ability System Enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("resetability")) {
            String randomAbility = abilityList.get(new Random().nextInt(abilityList.size()));
            playerAbilities.put(player.getUniqueId(), randomAbility);
            player.sendMessage(ChatColor.GREEN + "Your new ability is: " + ChatColor.GOLD + randomAbility);
        } else if (command.getName().equalsIgnoreCase("abilityinfo")) {
            String ability = playerAbilities.getOrDefault(player.getUniqueId(), "None");
            player.sendMessage(ChatColor.YELLOW + "Ability: " + ChatColor.AQUA + ability + "\n" + ChatColor.GRAY + getAbilityDesc(ability));
        }
        return true;
    }

    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            String ability = playerAbilities.get(player.getUniqueId());
            if (ability != null) executeAbility(player, ability);
        }
    }

    private void executeAbility(Player player, String ability) {
        switch (ability) {
            case "Fireball": player.launchProjectile(Fireball.class); break;
            case "SuperJump": player.setVelocity(player.getLocation().getDirection().multiply(2).setY(1)); break;
            case "Heal": player.setHealth(20); player.sendMessage("Healed!"); break;
            case "Speed": player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2)); break;
            case "Invisibility": player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1)); break;
            case "Strength": player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 200, 2)); break;
            case "Teleport": player.teleport(player.getTargetBlock(null, 50).getLocation().add(0, 1, 0)); break;
            case "Lightning": player.getWorld().strikeLightning(player.getTargetBlock(null, 50).getLocation()); break;
            case "Explosion": player.getWorld().createExplosion(player.getTargetBlock(null, 10).getLocation(), 2F); break;
            case "Levitate": player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1)); break;
            case "WaterBreathing": player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1)); break;
            case "FireResistance": player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1)); break;
            case "NightVision": player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1)); break;
            case "Poison": player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1)); break;
            case "Freeze": player.getTargetBlock(null, 10).setType(Material.ICE); break;
            case "Knockback": player.setVelocity(player.getLocation().getDirection().multiply(-2)); break;
            case "Diamond": player.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.DIAMOND)); break;
            case "EnderPearl": player.launchProjectile(EnderPearl.class); break;
            case "Blindness": player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)); break;
            case "Nausea": player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 1)); break;
            case "Slowness": player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2)); break;
            case "Haste": player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 200, 2)); break;
            case "Regen": player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2)); break;
            case "Absorption": player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2)); break;
            case "Saturation": player.setFoodLevel(20); break;
            case "JumpBoost": player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 2)); break;
            case "Dolphin": player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 200, 1)); break;
            case "Conduit": player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 200, 1)); break;
            case "Resistance": player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 200, 1)); break;
            case "Wither": player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1)); break;
        }
    }

    private String getAbilityDesc(String ability) {
        switch (ability) {
            case "Fireball": return "Shoots an explosive fireball.";
            case "SuperJump": return "Launches you into the air.";
            case "Heal": return "Instantly restores your health.";
            case "Speed": return "Gives you a speed boost.";
            case "Invisibility": return "Makes you invisible.";
            case "Strength": return "Increases your attack damage.";
            case "Teleport": return "Teleports you to the block you are looking at.";
            case "Lightning": return "Strikes lightning where you look.";
            case "Explosion": return "Creates an explosion at your target.";
            case "Levitate": return "Makes you float.";
            case "WaterBreathing": return "Allows you to breathe underwater.";
            case "FireResistance": return "Makes you immune to fire.";
            case "NightVision": return "Lets you see in the dark.";
            case "Poison": return "Poisons yourself (be careful!).";
            case "Freeze": return "Turns the block you look at into ice.";
            case "Knockback": return "Pushes you backwards.";
            case "Diamond": return "Gives you a single diamond.";
            case "EnderPearl": return "Throws an ender pearl.";
            case "Blindness": return "Blinds your vision.";
            case "Nausea": return "Makes your vision dizzy.";
            case "Slowness": return "Slows your movement.";
            case "Haste": return "Increases mining speed.";
            case "Regen": return "Rapidly restores health.";
            case "Absorption": return "Gives you extra hearts.";
            case "Saturation": return "Fills your hunger bar.";
            case "JumpBoost": return "Allows you to jump higher.";
            case "Dolphin": return "Makes you swim faster.";
            case "Conduit": return "Gives you water powers.";
            case "Resistance": return "Reduces incoming damage.";
            case "Wither": return "Inflicts wither effect.";
            default: return "No description available.";
        }
    }
}
