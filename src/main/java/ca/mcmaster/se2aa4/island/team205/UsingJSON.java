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

    public UsingJSON(){
        // empty constructor
    }

    @Override
    public void echo(Drone.Direction direction) {
        decision.put("action", "echo");
        parameters.put("direction", direction.toString());
        decision.put("parameters", parameters);
    }

    @Override
    public void scan() {
        decision.put("action","scan");
    }

    @Override
    public Integer batteryLevel() {
        return null;
    }

    @Override
    public void turnDrone(Drone.Direction direction) {
        decision.put("action", "heading");
        parameters.put("direction", direction.toString());
        decision.put("parameters", parameters);
    }

    @Override
    public void goHome() {
        decision.put("action", "stop");
    }

    @Override
    public void fly() {
        decision.put("action", "fly");
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

    //private JSONObject extraInfo(){
        //return response.getJSONObject("extras");
    //}

    @Override
    public String decision(){
        return decision.toString();
    }

    public String direction(){
        return response.getString("heading");
    }

    @Override
    public List<String> terrain(){
        List<Object> list = response.getJSONObject("extras").getJSONArray("biomes").toList();
        return toArrayList(list);
    }

    @Override
    public List<String> creek(){
        List<Object> list = response.getJSONObject("extras").getJSONArray("creeks").toList();
        return toArrayList(list);
    }

    @Override
    public List<String> site(){
        List<Object> list = response.getJSONObject("extras").getJSONArray("sites").toList();
        return toArrayList(list);
    }

    @Override
    public String echoReceived(){
        return response.getJSONObject("extras").getString("found");
    }

    @Override
    public int range(){
        return response.getJSONObject("extras").getInt("range");
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
