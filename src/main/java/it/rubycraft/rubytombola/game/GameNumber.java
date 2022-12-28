package it.rubycraft.rubytombola.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameNumber {
    private int number;
    private boolean marked;

    public GameNumber(int number, boolean marked) {
        this.number = number;
        this.marked = marked;
    }

    private GameNumber() {
        this.number = -1;
        this.marked = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameNumber)) return false;
        GameNumber that = (GameNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public static List<GameNumber> generateRange(int max) {
        List<GameNumber> numbers = new ArrayList<>();

        for(int j = 0; j < max; j++) {
            numbers.add(new GameNumber(j, false));
        }

        return numbers;
    }

    public static GameNumber empty() {
        return new GameNumber();
    }
}
