package com.bliss;

import org.bukkit.ChatColor;
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

    // Registry for your abilities
    private final Map<String, Ability> registry = new HashMap<>();
    private final Map<UUID, String> playerAbilities = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetability").setExecutor(this);
        getCommand("abilityinfo").setExecutor(this);
        
        // Register your abilities here
        registerAbility("Fireball", new FireballAbility());
        registerAbility("SuperJump", new SuperJumpAbility());
        // Register all 30 here...
        
        getLogger().info("BlissPlugin System Initialized with " + registry.size() + " abilities.");
    }

    private void registerAbility(String name, Ability ability) {
        registry.put(name.toLowerCase(), ability);
    }

    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            String abilityName = playerAbilities.get(player.getUniqueId());
            if (abilityName != null && registry.containsKey(abilityName.toLowerCase())) {
                registry.get(abilityName.toLowerCase()).execute(player);
            }
        }
    }

    // Commands to reset and view
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("resetability")) {
            List<String> keys = new ArrayList<>(registry.keySet());
            String random = keys.get(new Random().nextInt(keys.size()));
            playerAbilities.put(player.getUniqueId(), random);
            player.sendMessage(ChatColor.GREEN + "You have been assigned: " + random);
        }
        return true;
    }
}
