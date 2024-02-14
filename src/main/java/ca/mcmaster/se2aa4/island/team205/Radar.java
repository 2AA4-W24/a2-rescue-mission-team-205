package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;

public class Radar {
    // instance variables
    private int cost = 0;
    private int direction = 0;

    //uses the radar to scan for POI or biomes
    public int useRadar() {
        switch(direction){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        cost ++;
        return cost;
    }
    // method to add up cost when scanning the radar
    public int getCost() {
        return cost;
    }
}

