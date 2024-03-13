package ca.mcmaster.se2aa4.island.team205;

import static eu.ace_design.island.runner.Runner.run;

import java.io.File;

public class Runner {

    public static void main(String[] args) {
        String filename = args[0];
        int budget = 7000 + (int)(Math.random() * 40000);
        int x = 1 + (int)(Math.random() * 52);
        int y = 1 + (int)(Math.random() * 52);
        int odd = 1 + (int)(Math.random() * 2);
        String heading = "";
        if(x > 25) {
            if (x > 48) {
                if (y > 25) {
                    if (odd == 1) {
                        heading = "WEST";
                    } else {
                        heading = "NORTH";
                    }
                } else {
                    if (odd == 1) {
                        heading = "WEST";
                    } else {
                        heading = "SOUTH";
                    }
                }
            } else {
                if (y > 25) {
                    y = 48;
                    if (odd == 1) {
                        heading = "WEST";
                    } else {
                        heading = "NORTH";
                    }

                } else {
                    y = 2;
                    if (odd == 1) {
                        heading = "WEST";
                    } else {
                        heading = "SOUTH";
                    }
                }


            }
        }
            else {
            if (x < 5) {
                if (y > 25) {
                    if (odd == 1) {
                        heading = "EAST";
                    } else {
                        heading = "NORTH";
                    }
                } else {
                    if (odd == 1) {
                        heading = "EAST";
                    } else {
                        heading = "SOUTH";
                    }
                }
            } else {
                if (y > 25) {
                    y = 48;
                    if (odd == 1) {
                        heading = "EAST";
                    } else {
                        heading = "NORTH";
                    }

                } else {
                    y = 2;
                    if (odd == 1) {
                        heading = "EAST";
                    } else {
                        heading = "SOUTH";
                    }
                }


            }

        }

        try {
            run(Explorer.class)
                    .exploring(new File(filename))
                    .withSeed(42L)
                    .startingAt(1, 1, "EAST")
                    .backBefore(15000)
                    .withCrew(5)
                    .collecting(1000, "WOOD")
                    .storingInto("./outputs")
                    .withName("Island")
                    .fire();
        } catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
