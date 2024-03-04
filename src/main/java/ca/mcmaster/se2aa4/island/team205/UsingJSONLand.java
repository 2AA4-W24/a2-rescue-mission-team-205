package ca.mcmaster.se2aa4.island.team205;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class UsingJSONLand implements LandActions {
    

    private final JSONObject decision = new JSONObject();
    private final JSONObject parameters = new JSONObject();
    private JSONObject response;

    public UsingJSONLand() {
        // empty constructor
    }
    @Override
    public void land(String creekId, int people){
        decision.put("action", "land");
        parameters.put("creek", creekId);
        parameters.put("people", people);
        decision.put("parameters", parameters);
    }

    @Override
    public void scout(String direction){
        decision.put("action", "scout");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
    }

    @Override
    public void moveTo(String direction){
        decision.put("action", "move_to");
        parameters.put("direction", direction);
        decision.put("parameters", parameters);
    }

    @Override
    public void explore(){
        decision.put("action", "explore");
        // no params
    }

    public void results(String s) {
        response = new JSONObject(new JSONTokener(new StringReader(s)));
    }

    public String decision() {
        return decision.toString();
    }
}