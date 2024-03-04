package ca.mcmaster.se2aa4.island.team205;

public class GridSearch implements SearchAlgorithm{

    CreekLocations creeks = new CreekLocations();

    private PointOfInterest site;

    @Override
    public void findEmergencySite() {
        //when site is found
        site = new PointOfInterest("identifier", new int[]{4,6});
    }

    @Override
    public void findCreeks() {
        //if found a creek
        creeks.addCreek(new PointOfInterest("Identifier from json", new int[]{1,3}));

    }

    @Override
    public PointOfInterest closestCreek(){
        return creeks.closestCreak(site);
    }

    @Override
    public boolean isSiteFound() {
        return false;
    }
}
