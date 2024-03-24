package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class UsingJSON implements Information{

    private final JSONObject decision = new JSONObject();

    private final JSONObject parameters = new JSONObject();

    private JSONObject response;

    private final String actionCommand;

    private final String getExtras;

    public UsingJSON(){
        actionCommand = "action";
        getExtras = "extras";
    }

    @Override
    public void echo(Drone.Direction direction) {
        decision.put(actionCommand, "echo");
        parameters.put("direction", direction.toString());
        decision.put("parameters", parameters);
    }

    @Override
    public void scan() {
        decision.put(actionCommand,"scan");
    }

    @Override
    public void turnDrone(Drone.Direction direction) {
        decision.put(actionCommand, "heading");
        parameters.put("direction", direction.toString());
        decision.put("parameters", parameters);
    }

    @Override
    public void goHome() {
        decision.put(actionCommand, "stop");
    }

    @Override
    public void fly() {
        decision.put(actionCommand, "fly");
    }

    @Override
    public Integer cost(){
        return response.getInt("cost");
    }

    @Override
    public Integer budget(){
        return response.getInt("budget");
    }

    @Override
    public void results(String s) {
        response = new JSONObject(new JSONTokener(new StringReader(s)));
    }

    @Override
    public String status(){
        return response.getString("status");
    }

    @Override
    public String decision(){
        return decision.toString();
    }

    public String direction(){
        return response.getString("heading");
    }

    @Override
    public List<String> terrain(){
        List<Object> list = response.getJSONObject(getExtras).getJSONArray("biomes").toList();
        return toArrayList(list);
    }

    @Override
    public List<String> creek(){
        List<Object> list = response.getJSONObject(getExtras).getJSONArray("creeks").toList();
        return toArrayList(list);
    }

    @Override
    public List<String> site(){
        List<Object> list = response.getJSONObject(getExtras).getJSONArray("sites").toList();
        return toArrayList(list);
    }

    @Override
    public String echoReceived(){
        return response.getJSONObject(getExtras).getString("found");
    }

    @Override
    public int range(){
        return response.getJSONObject(getExtras).getInt("range");
    }

    @Override
    public List<String> toArrayList(List<Object> list) {
        List<String> result = new ArrayList<>();
        for (Object obj: list) {
            result.add(obj.toString());
        }
        return result;
    }
    
}
