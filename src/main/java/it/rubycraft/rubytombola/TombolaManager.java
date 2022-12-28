package it.rubycraft.rubytombola;

import it.rubycraft.rubytombola.game.GameCard;
import it.rubycraft.rubytombola.game.GameNumber;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TombolaManager {
    private List<GameNumber> availableNumbers;
    private final List<GameNumber> chosenNumbers;

    private final Map<String, GameCard> playerCards;

    public TombolaManager() {
        this.availableNumbers = new ArrayList<>();
        this.chosenNumbers = new ArrayList<>();
        this.playerCards = new HashMap<>();

        reset();
    }

    public void reset() {
        availableNumbers.clear();
        chosenNumbers.clear();
        playerCards.clear();
        availableNumbers = GameNumber.generateRange(90);
    }

    public void giveCard(Player player) {
        //ha già una carta
        if(playerCards.containsKey(player.getName())) {
            return;
        }
    }

    public void pickNumber() {
        Random random = new Random();
        GameNumber current = availableNumbers.get(random.nextInt(availableNumbers.size() - 1));
        availableNumbers.remove(current);
        chosenNumbers.add(current);

        //titolo che fa hero che non ho voglia

    }

    public void validate(GameNumber number) {
        if(!chosenNumbers.contains(number)) return;
        number.setMarked(true);
    }
}
