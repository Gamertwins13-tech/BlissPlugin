package com.bliss;

import org.bukkit.entity.Player;

public interface Ability {
    void execute(Player player);
    String getDescription();
}
