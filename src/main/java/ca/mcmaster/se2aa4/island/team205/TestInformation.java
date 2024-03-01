package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONArray;

public class TestInformation implements Information {

    private JSONArray terrainData;

    public TestInformation() {
        // Initialize fields
    }

    // implementation of all methods of the Information interface
    @Override
    public void echo(Drone.Direction direction) {

    }

    @Override
    public void scan() {

    }

    @Override
    public Integer batteryLevel() {
        return 100;
    }

    @Override
    public String direction() {
        return "North"; 
    }

    @Override
    public void goHome() {

    }

    @Override
    public Integer cost() {
        return 10; // example val
    }

    @Override
    public void turnDrone(Drone.Direction direction) {

    }

    @Override
    public void results(String s) {

    }

    @Override
    public Integer budget() {
        return 1000; 
    }

    @Override
    public String status() {
        return "OK"; // example val
    }

    @Override
    public void fly() {

    }

    @Override
    public String decision() {
        return "Continue"; // example val
    }

    @Override
    public JSONArray terrain() {
        return terrainData;
    }

    @Override
    public String echoReceived() {
        return "Echo"; 
    }

    @Override
    public int range() {
        return 5; // example val
    }

    public void setTerrainData(JSONArray data) {
        terrainData = data;
    }
}
