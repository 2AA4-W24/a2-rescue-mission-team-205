package ca.mcmaster.se2aa4.island.team205;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Move> moves;

    public Map() {
        this.moves = new ArrayList<>();
    }

    public void addMove(Location location, Drone.Direction direction) {
        moves.add(new Move(location, direction));
    }

    public List<Move> getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "Map{" +
               "moves=" + moves +
               '}';
    }
    
}
