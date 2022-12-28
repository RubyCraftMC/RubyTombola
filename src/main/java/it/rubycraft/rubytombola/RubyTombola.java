package it.rubycraft.rubytombola;

import it.rubycraft.rubytombola.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RubyTombola extends JavaPlugin implements CommandExecutor {

    private static RubyTombola INSTANCE = null;

    private Config config;
    private TombolaManager tombolaManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.config = new Config(this, "config");
        this.tombolaManager = new TombolaManager();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Config getConfigYml() {
        return config;
    }

    public TombolaManager getTombolaManager() {
        return tombolaManager;
    }

    public static RubyTombola getInstance() {
        return INSTANCE;
    }
}
