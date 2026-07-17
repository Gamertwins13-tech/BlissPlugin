package com.bliss;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BlissPlugin extends JavaPlugin implements Listener, CommandExecutor {

    private final Map<UUID, String> playerAbilities = new HashMap<>();
    private final List<String> abilityList = Arrays.asList("Fireball", "SuperJump", "Heal", "Speed"); // Add all 30 names here

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetability").setExecutor(this);
        getCommand("abilityinfo").setExecutor(this);
        getLogger().info("BlissPlugin Enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("resetability")) {
            String randomAbility = abilityList.get(new Random().nextInt(abilityList.size()));
            playerAbilities.put(player.getUniqueId(), randomAbility);
            player.sendMessage(ChatColor.GREEN + "Your new ability is: " + randomAbility);
        } else if (command.getName().equalsIgnoreCase("abilityinfo")) {
            String ability = playerAbilities.getOrDefault(player.getUniqueId(), "None");
            player.sendMessage(ChatColor.YELLOW + "Your current ability: " + ability + "\n" + getAbilityDesc(ability));
        }
        return true;
    }

    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            String ability = playerAbilities.get(player.getUniqueId());
            if (ability != null) {
                executeAbility(player, ability);
            }
        }
    }

    private void executeAbility(Player player, String ability) {
        if (ability.equals("Fireball")) {
            player.launchProjectile(org.bukkit.entity.Fireball.class);
        } else if (ability.equals("SuperJump")) {
            player.setVelocity(player.getLocation().getDirection().multiply(2).setY(1));
        } else if (ability.equals("Heal")) {
            player.setHealth(20);
        }
        // Add more else if blocks here for the rest of your 30 abilities!
    }

    private String getAbilityDesc(String ability) {
        if (ability.equals("Fireball")) return "Shoots a giant explosive fireball.";
        if (ability.equals("SuperJump")) return "Launches you into the sky.";
        if (ability.equals("Heal")) return "Instantly restores full health.";
        return "No information available.";
    }
}
