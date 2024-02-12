package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;

public class Radar {
    // instance variables
    private int cost = 0;
    private int direction = 0;

    //uses the radar to scan for POI or biomes
    public String useRadar() {
        JSONObject decision = new JSONObject();
        return decision.toString();
    }
    // method to add up cost when scanning the radar
    public int getCost() {
        return cost;
    }
}

