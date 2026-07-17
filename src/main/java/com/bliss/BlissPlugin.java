package com.bliss;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BlissPlugin extends JavaPlugin implements Listener, CommandExecutor {

    private final Map<UUID, String> playerAbilities = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Register events and commands
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("resetability").setExecutor(this);
        getCommand("myability").setExecutor(this);
        getLogger().info("Bliss System: Loaded successfully!");
    }

    private void assignAbility(Player p) {
        // Simple assignment logic for demonstration
        String[] abilities = {"Speed", "Strength", "ArrowProof", "MeteorStrike"};
        String chosen = abilities[random.nextInt(abilities.length)];
        
        playerAbilities.put(p.getUniqueId(), chosen);
        p.sendTitle(ChatColor.AQUA + "New Ability!", ChatColor.WHITE + chosen, 10, 70, 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!playerAbilities.containsKey(e.getPlayer().getUniqueId())) {
            assignAbility(e.getPlayer());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("resetability") && sender instanceof Player p) {
            assignAbility(p);
            p.sendMessage(ChatColor.GREEN + "Ability reset!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.isSneaking() || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        String ability = playerAbilities.get(p.getUniqueId());
        if (ability == null) return;

        // Cooldown
        if (cooldowns.containsKey(p.getUniqueId()) && (System.currentTimeMillis() - cooldowns.get(p.getUniqueId()) < 15000)) {
            p.sendMessage(ChatColor.RED + "Cooldown!");
            return;
        }
        cooldowns.put(p.getUniqueId(), System.currentTimeMillis());

        // Logic
        switch (ability) {
            case "Speed" -> p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
            case "ArrowProof" -> p.sendMessage(ChatColor.AQUA + "You feel protected from arrows!");
            // Add your other 28 abilities here
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player p && "ArrowProof".equals(playerAbilities.get(p.getUniqueId())) && e.getDamager() instanceof Projectile) {
            e.setCancelled(true);
        }
    }
}
