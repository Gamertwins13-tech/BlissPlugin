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
    private final Map<UUID, AbilityType> playerAbilities = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetability").setExecutor(this);
        getCommand("abilityinfo").setExecutor(this);
    }

    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            AbilityType ability = playerAbilities.get(player.getUniqueId());
            if (ability != null) ability.execute(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("resetability")) {
            AbilityType[] values = AbilityType.values();
            AbilityType random = values[new Random().nextInt(values.length)];
            playerAbilities.put(p.getUniqueId(), random);
            p.sendMessage(ChatColor.GREEN + "New ability: " + random.getName());
        } else if (command.getName().equalsIgnoreCase("abilityinfo")) {
            AbilityType ability = playerAbilities.get(p.getUniqueId());
            p.sendMessage(ability != null ? ChatColor.YELLOW + ability.getName() + ": " + ChatColor.WHITE + ability.getDescription() : "No ability.");
        }
        return true;
    }
}
