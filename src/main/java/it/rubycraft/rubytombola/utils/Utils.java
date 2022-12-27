package it.rubycraft.rubytombola.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Utils {

    public static String[][] createMatrix(){

        List<Integer> numbers = new ArrayList<>();
        String[][] m = new String[3][9];
        for(int y = 0; y < 3; y++) {
            HashSet<Integer> rm = new HashSet<>();
            while(rm.size() < 5) {
                rm.add(new Random().nextInt(10));
            }
            for(int x = 0; x < 9; x++){
                if(!rm.contains(x)) {
                    m[x][y] = "empty";
                    continue;
                }
                int number;
                do {
                    number = x * 10 + new Random().nextInt(9) + 1;
                } while (numbers.contains(number));
                numbers.add(number);
                m[x][y] = String.valueOf(number);
            }
        }
        return m;
    }

    public static void update(Player player, ItemStack itemStack, String[][] cardMatrix, Predicate<String> filter) {
        MapView map = Bukkit.createMap(player.getWorld());
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(y * 14, y * 14, 14, 14);
                if(cardMatrix[x][y].equalsIgnoreCase("empty")) continue;
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString(cardMatrix[x][y], x * 14 + 2, y * 14 + 12);
                if(filter.test(cardMatrix[x][y])) {
                    graphics.setColor(Color.RED);
                    graphics.setFont(new Font("Arial", Font.PLAIN, 15));
                    graphics.drawString("X", x * 14 + 2, y * 14 + 13);
                }
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
       //ItemStack mapItem = new ItemStack(Material.MAP, 1, (short) mapId);
       //player.getInventory().addItem(mapItem);
    }
}
