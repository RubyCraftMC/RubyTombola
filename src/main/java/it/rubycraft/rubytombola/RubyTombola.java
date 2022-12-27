package it.rubycraft.rubytombola;

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

    @Override
    public void onEnable() {
        getCommand("mappa").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        MapView map = Bukkit.createMap(player.getWorld());
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> rm = new ArrayList<>();

            while (rm.size() < 5) {
                int randomNumber = new Random().nextInt(10);
                if (!rm.contains(randomNumber)) {
                    rm.add(randomNumber);
                }
            }
            for (int j = 0; j < 9; j++) {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(j * 14, i * 14, 14, 14);
                if (!rm.contains(j)) continue;
                int number;
                do {
                    number = j * 10 + new Random().nextInt(9) + 1;
                } while (numbers.contains(number));
                numbers.add(number);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString(Integer.toString(number), j * 14 + 2, i * 14 + 12);
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Arial", Font.PLAIN, 15));
                graphics.drawString("X", j * 14 + 2, i * 14 + 13);
            }
        }
        MapRenderer renderer = new MapRenderer() {
            @Override
            public void render(MapView map, MapCanvas canvas, Player player) {
                canvas.drawImage(0, 40, image);
            }
        };
        map.getRenderers().forEach(map::removeRenderer);
        map.addRenderer(renderer);
        int mapId = map.getId();
        ItemStack mapItem = new ItemStack(Material.MAP, 1, (short) mapId);
        player.getInventory().
                addItem(mapItem);
        return true;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
