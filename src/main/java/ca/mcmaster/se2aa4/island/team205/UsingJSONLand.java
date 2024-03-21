package ca.mcmaster.se2aa4.island.team205;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class UsingJSONLand implements LandActions {


    private final JSONObject decision = new JSONObject();
    private final JSONObject parameters = new JSONObject();
    
    private final String actionCommand;
    
    private final String parameterCommand;

    private JSONObject response;

    public UsingJSONLand() {
        actionCommand = "action";
        parameterCommand = "parameters";
        
    }
    @Override
    public void land(String creekId, int people){
        decision.put(actionCommand, "land");
        parameters.put("creek", creekId);
        parameters.put("people", people);
        decision.put(parameterCommand, parameters);
    }

    @Override
    public void scout(String direction){
        decision.put(actionCommand, "scout");
        parameters.put("direction", direction);
        decision.put(parameterCommand, parameters);
    }

    @Override
    public void moveTo(String direction){
        decision.put(actionCommand, "move_to");
        parameters.put("direction", direction);
        decision.put(parameterCommand, parameters);
    }

    @Override
    public void explore(){
        decision.put(actionCommand, "explore");
        // no params
    }

    public void results(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
    }

    public String decision() {
        return decision.toString();
    }
}
