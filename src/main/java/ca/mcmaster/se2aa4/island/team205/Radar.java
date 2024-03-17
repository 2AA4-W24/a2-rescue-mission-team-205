package ca.mcmaster.se2aa4.island.team205;

public class Radar {
    
    private final Information info;

    String direction;

    public Radar(Information information){
        info = information;

    }

    public void useRadar(Drone.Direction d){
        info.echo(d);
    }

    public void useRadarFront(Drone.Direction d) {
        info.echo(d);
    }

    public void useRadarRight(Drone.Direction d) {
        info.echo(d);
    }

    public void useRadarLeft(Drone.Direction d) {
        info.echo(d);
    }

    public int distanceToLand(){
        if("GROUND".equals(info.echoReceived())){
            return info.range();
        }
        else{
            return -1;
        }
    }


}

