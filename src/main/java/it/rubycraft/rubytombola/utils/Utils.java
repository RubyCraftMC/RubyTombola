package it.rubycraft.rubytombola.utils;

import it.rubycraft.rubytombola.RubyTombola;
import it.rubycraft.rubytombola.game.GameCard;
import it.rubycraft.rubytombola.game.GameNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Utils {
    public static String[][] createMatrix() {
        List<Integer> numbers = new ArrayList<>();
        String[][] m = new String[3][9];

        for (int y = 0; y < 3; y++) {
            HashSet<Integer> rm = new HashSet<>();
            while (rm.size() < 5) {
                rm.add(new Random().nextInt(10));
            }
            for (int x = 0; x < 9; x++) {
                if (!rm.contains(x)) {
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

    public static GameCard giveNewCard(Player player) {

        String[][] matrice = createMatrix();
        GameNumber[][] gameNumbers = new GameNumber[3][9];
        MapView map = Bukkit.createMap(player.getWorld());
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        // Background bianco
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 128, 128);

        // immagine di sfondo
        Image bg = null;
        try {
            bg = ImageIO.read(new URL("https://i.imgur.com/Uw6XUy9.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.drawImage(bg, 0, 0, 128, 128, null);

        // righe
        for (int y = 0; y < 3; y++) {

            // colonne
            for (int x = 0; x < 9; x++) {
                //Creazione della scheda di base ( i quadrati)
                graphics.setColor(Color.WHITE);
                graphics.fillRect(x * 14, 45 + y * 14, 14, 14);
                graphics.setColor(Color.BLACK);
                graphics.drawRect(x * 14, 45 + y * 14, 14, 14);

                if (matrice[x][y].equalsIgnoreCase("empty")) {
                    gameNumbers[x][y] = GameNumber.empty();
                    continue;
                }

                // Scrive i numeri
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString(matrice[x][y], x * 14 + 2, 45 + y * 14 + 12);
                gameNumbers[x][y] = new GameNumber(Integer.parseInt(matrice[x][y]), false);
            }
        }

        //Crea il render custom
        MapRenderer renderer = new MapRenderer() {
            @Override
            public void render(MapView map, MapCanvas canvas, Player player) {
                canvas.drawImage(0, 0, image);
            }
        };

        //Rimuove i render e aggiunge quello custom
        map.getRenderers().forEach(map::removeRenderer);
        map.addRenderer(renderer);
        //Givva l'item al player
        int mapId = map.getId();
        ItemStack mapItem = new ItemStack(Material.MAP, 1, (short) mapId);
        player.getInventory().addItem(mapItem);

        return new GameCard(gameNumbers);
    }

    public static void update(Player player, ItemStack itemStack, String[][] cardMatrix, Predicate<String> filter) {
        MapView map = Bukkit.createMap(player.getWorld());
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        // Background bianco
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 128, 128);

        // immagine di sfondo
        Image bg = null;
        try {
            bg = ImageIO.read(new URL("https://i.imgur.com/Uw6XUy9.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.drawImage(bg, 0, 0, 128, 128, null);

        // righe
        for (int y = 0; y < 3; y++) {

            // colonne
            for (int x = 0; x < 9; x++) {
                //Creazione della scheda di base ( i quadrati)
                graphics.setColor(Color.WHITE);
                graphics.fillRect(x * 14, 45 + y * 14, 14, 14);
                graphics.setColor(Color.BLACK);
                graphics.drawRect(x * 14, 45 + y * 14, 14, 14);

                if (cardMatrix[x][y].equalsIgnoreCase("empty")) {
                    continue;
                }

                // Scrive i numeri
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString(cardMatrix[x][y], x * 14 + 2, 45 + y * 14 + 12);
                if(filter.test(cardMatrix[x][y])) {
                    graphics.setColor(Color.RED);
                    graphics.drawLine(x * 14 + 2, 45 + y * 14 + 2, (x + 1) * 14 - 2, (y + 1) * 14 + 45 - 2);
                    graphics.drawLine((x + 1) * 14 - 2, 45 + y * 14 + 2, x * 14 + 2, (y + 1) * 14 + 45 - 2);
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
    }


    public static String colora(String str){
        if(str == null){
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String prefix(){
        return colora(RubyTombola.getInstance().getConfigYml().getString("prefix"));
    }

    public static List<String> coloraLista(List<String> colors) {
        List<String> a = new ArrayList<>();
        colors.forEach(b -> a.add(colora(b)));
        return a;
    }

    public static String getTimeFormatted(long countdown){
        long hours = countdown / 3600;
        long minutes = (countdown % 3600) / 60;
        long seconds = countdown % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void sendMessage(Player player, String message){
        player.sendMessage(colora(prefix() + message));
    }

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(colora(prefix() + message));
    }

    public static String getCurrentHour(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static boolean isValidInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
