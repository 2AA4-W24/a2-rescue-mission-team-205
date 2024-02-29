package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class UsingJSON implements Information{

    private final JSONObject decision = new JSONObject();
    private final JSONObject parameters = new JSONObject();
    private JSONObject response;

    public UsingJSON(){
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

    @Override
    public JSONArray terrain(){
        return response.getJSONObject("extras").getJSONArray("biomes");
    }

    @Override
    public String echoReceived(){
        return response.getJSONObject("extras").getString("found");
    }

    @Override
    public int range(){
        return response.getJSONObject("extras").getInt("range");
    }
}
