package ca.mcmaster.se2aa4.island.team205;


public class CommandCenter {

    private final Information info = new UsingJSON();

    private final Radar radar = new Radar(info);

    private final PhotoScanner scanner = new PhotoScanner(info);

    private final Drone drone = new Drone(info);

    private int commands = 1;

    private int range;

    private boolean landFound = false;

    private boolean landSpotted = false;

    private boolean emergencySiteFound = false;

    private boolean closestCreekFound = false;


    public CommandCenter(String s){
        info.results(s);
        drone.initialize(s);
    }

    public void updateInformation(String s){
        info.results(s);
        drone.drain(info.cost());
    }

    public void takeCommand(){

        if(drone.battery <= 15){
            drone.returnHome();
        }
        else if(!landFound){
            //find next movement from record class
            findLand();
            commands ++;
        }
        else if(!emergencySiteFound){
            findSite();
            commands ++;
        }
        else if(!closestCreekFound){
            closestCreek();
            commands ++;
        }


    }

    private void findLand(){
        //will be a nested if statement checking if the last action was fly
        if(commands % 5 == 0){
            radar.useRadarRight(drone.getDirection());
            //add action to records class
        }
        else{
            drone.fly();
        }

        //this will be if the last action was radar

        if(radar.distanceToLand() > 0){
            if(!landSpotted){
                range = radar.distanceToLand();
                //find what way we used radar
                switch(radar.directionOfLand()){
                    case "right" -> drone.turnRight();
                    case "left" -> drone.turnLeft();
                    default -> {
                        drone.fly();
                        range--;
                    }
                }
                landSpotted = true;
            }
            else{
                drone.fly();
                range--;
            }
        }
        else if(range == 0){
            scanner.scanTerrain();
            //add action
            landFound = true;
        }
        else {
            drone.fly();
        }

    }

    private void findSite(){
        //implement grid search
        //if last move was fly or radar do
        scanner.scanTerrain();
        //else check radar result and fly
        emergencySiteFound = true;
    }

    private void closestCreek(){
    //start looking radially outward
        closestCreekFound = true;
    }


    public String decision(){
        return info.decision();
    }
}
