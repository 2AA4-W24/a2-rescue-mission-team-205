package ca.mcmaster.se2aa4.island.team205;

public class Radar {

    private final Information info;


    public Radar(Information information){
        info = information;
    }

    public void useRadar(Drone.Direction d){
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

