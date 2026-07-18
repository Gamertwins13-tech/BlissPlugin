package com.bliss;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum AbilityType {
    FIREBALL("Fireball", "Shoots a fireball.") { @Override public void execute(Player p) { p.launchProjectile(Fireball.class); p.getWorld().spawnParticle(Particle.FLAME, p.getLocation(), 5); }},
    SUPERJUMP("SuperJump", "Launch high.") { @Override public void execute(Player p) { p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(1.5)); }},
    SPEED("Speed", "Go fast.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2)); }},
    HEAL("Heal", "Restores health.") { @Override public void execute(Player p) { p.setHealth(20); }},
    INVISIBILITY("Invisibility", "Hide yourself.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1)); }},
    STRENGTH("Strength", "Hit harder.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 200, 2)); }},
    TELEPORT("Teleport", "Jump forward.") { @Override public void execute(Player p) { p.teleport(p.getLocation().add(p.getLocation().getDirection().multiply(5))); }},
    LIGHTNING("Lightning", "Strike foes.") { @Override public void execute(Player p) { p.getWorld().strikeLightning(p.getTargetBlock(null, 50).getLocation()); }},
    EXPLOSION("Explosion", "Boom.") { @Override public void execute(Player p) { p.getWorld().createExplosion(p.getTargetBlock(null, 10).getLocation(), 2F); }},
    LEVITATE("Levitate", "Float up.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1)); }},
    WATERBREATH("WaterBreathing", "Swim forever.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1)); }},
    FIRERES("FireResistance", "No burning.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1)); }},
    NIGHTVIS("NightVision", "See in dark.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1)); }},
    POISON("Poison", "Use wisely.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1)); }},
    FREEZE("Freeze", "Slows target.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2)); }},
    KNOCKBACK("Knockback", "Push back.") { @Override public void execute(Player p) { p.setVelocity(p.getLocation().getDirection().multiply(-2)); }},
    DIAMOND("Diamond", "Free shiny.") { @Override public void execute(Player p) { p.getInventory().addItem(new ItemStack(Material.DIAMOND)); }},
    ENDERPEARL("EnderPearl", "Throw pearl.") { @Override public void execute(Player p) { p.launchProjectile(org.bukkit.entity.EnderPearl.class); }},
    BLINDNESS("Blindness", "Blinds target.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)); }},
    NAUSEA("Nausea", "Dizzy.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 1)); }},
    SLOWNESS("Slowness", "Slow down.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2)); }},
    HASTE("Haste", "Mine fast.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 200, 2)); }},
    REGEN("Regen", "Heal over time.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2)); }},
    ABSORPTION("Absorption", "Extra hearts.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2)); }},
    SATURATION("Saturation", "Full hunger.") { @Override public void execute(Player p) { p.setFoodLevel(20); }},
    JUMPBOOST("JumpBoost", "Leap high.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 2)); }},
    DOLPHIN("Dolphin", "Swim fast.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 200, 1)); }},
    CONDUIT("Conduit", "Water power.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 200, 1)); }},
    RESISTANCE("Resistance", "Take less dmg.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 200, 1)); }},
    WITHER("Wither", "Wither effect.") { @Override public void execute(Player p) { p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1)); }};

    private final String name;
    private final String description;

    AbilityType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(Player player);
    public String getName() { return name; }
    public String getDescription() { return description; }
}
