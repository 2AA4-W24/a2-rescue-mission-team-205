package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsingJSON implements Information{

    private final JSONObject decision = new JSONObject();
    private final JSONObject parameters = new JSONObject();
    private JSONObject response;

    public UsingJSON(){
    }

    @Override
    public HashMap<String, String> echo(Drone.Direction direction) {
        decision.put("action", "echo");
        parameters.put("direction", direction.toString());
        decision.put("parameters", parameters);
        HashMap<String, String> range_and_found = new HashMap<>();
        range_and_found.put("range", String.valueOf(extraInfo().getInt("range")));
        range_and_found.put("found", extraInfo().getString("found"));
        return range_and_found;
    }

    @Override
    public List<String> scan() {
        decision.put("action","scan");
        JSONArray arr = extraInfo().getJSONArray("biomes");
        List<String> list = new ArrayList<>();
        for (int i=0;i<arr.length();i++) {
            list.add(arr.getJSONObject(i).getString("biomes"));
        }
        return list;
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

    private JSONObject extraInfo(){
        return response.getJSONObject("extras");
    }

    @Override
    public String decision(){
        return decision.toString();
    }

    public String direction(){
        return response.getString("heading");
    }
}
