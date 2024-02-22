package ca.mcmaster.se2aa4.island.team205;


public class Drone {

    private Integer battery;

    private Location location;

    // private final Radar radar = new Radar();

    private final PhotoScanner scanner = new PhotoScanner();

    private final Information info = new UsingJSON();

    private Direction direction;

    public Drone(Integer batteryLevel, String initial_direction){
        battery = batteryLevel;
        direction = initialDirection(initial_direction);
    }

    public void takeCommand(){
        int count = 0;
        Movement move = new Movement(this, info);
        while(!batteryTooLow()){
            if(true){
                move.returnHome();
                return;
            }
            else{
                if(count % 3 ==0){
                    chooseDirection();
                }
                else{
                    move.fly();
                    count++;
                }
            }
        }
        move.returnHome();
    }

    private void chooseDirection(){

    }

    private boolean batteryTooLow(){
        return info.batteryLevel() <= 15;
    }


    public Location getLocation(){
        return location;
    }

    private Direction initialDirection(String new_direction){
        switch(new_direction){
            case "North" -> {
                return Direction.NORTH;
            }
            case "South" -> {
                return Direction.SOUTH;
            }
            case "West" -> {
                return Direction.WEST;
            }
            default -> {
                return Direction.EAST;
            }
        }
    }

    public void setDirection(Direction d){
        direction = d;
        info.turnDrone(direction);
    }

    public Direction getDirection(){
        return direction;
    }

    public void updateBattery(Integer newBattery){
        battery = newBattery;
    }

    public Integer getBattery(){
        return battery;
    }
    public enum Direction{
        NORTH,
        SOUTH,
        WEST,
        EAST
    }
}

