package ca.mcmaster.se2aa4.island.team205;

import java.util.ArrayList;
import java.util.List;

public class CommandCenter {

    private final List<String> actions = new ArrayList<>();

    private final Information info = new UsingJSON();

    private final Radar radar = new Radar(info);

    private final PhotoScanner scanner = new PhotoScanner(info);

    private final Drone drone = new Drone(info);

    private int commands = 1;

    private int range;

    private boolean landFound = false;


    public CommandCenter(String s){
        info.results(s);
        drone.initialize(s);
    }
    public void updateInformation(String s){
        info.results(s);
        drone.drain(info.cost());
    }

    public void takeCommand(){
        commands ++;

        if(drone.battery <= 15){
            drone.returnHome();
        }
        if(landFound){
            if(range > 0){
                drone.fly();
                range--;
                actions.add("fly");
            }
            else{
                landFound = false;
                scanner.scanTerrain();
                actions.add("scan");
            }
        }

        if(!landFound && commands % 5 == 0){
            radar.useRadarRight(drone.getDirection());
            actions.add("radar");
        }
        else if(commands %21 == 0){
            if(!actions.isEmpty()){
                if(actions.get(actions.size() -1).equals("fly") || actions.get(actions.size() -1).equals("turn")){
                    drone.fly();
                    actions.add("fly");
                }
                else{
                    if(scanner.scanResults()){
                        drone.returnHome();
                    }
                    else {
                        drone.fly();
                        actions.add("fly");
                    }
                }
            }

        }
        else{
            if(!actions.isEmpty()){

                if(actions.get(actions.size() -1).equals("scan")){
                    if(scanner.scanResults()){
                        drone.returnHome();
                    }
                    else {
                        drone.fly();
                        actions.add("fly");
                    }
                }
                else if(actions.get(actions.size() -1).equals("radar")){
                    if(radar.distanceToLand() >= 0){
                        landFound = true;
                        range = radar.distanceToLand();
                        switch(radar.directionOfLand()){
                            case "right" -> drone.turnRight();
                            case "left" -> drone.turnLeft();
                            default -> {
                                drone.fly();
                                range--;
                                actions.add("fly");
                            }
                        }
                    }
                    else {
                        drone.fly();
                        actions.add("fly");
                    }
                }
                else{
                    drone.fly();
                    actions.add("fly");
                }
            }
            else{
                drone.fly();
                actions.add("fly");
            }

        }

    }

    public String decision(){
        return info.decision();
    }
}
