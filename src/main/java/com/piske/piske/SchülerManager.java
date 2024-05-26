package com.piske.piske;

import java.util.ArrayList;

public class SchülerManager {
    private ArrayList<Schüler> schülerList;

    public SchülerManager() {
        schülerList = new ArrayList<>();
    }

    public void addSchüler(Schüler schüler) {
        schülerList.add(schüler);
    }

    public void removeSchüler(Schüler schüler) {
        schülerList.remove(schüler);
    }

    public int getSchülerCount() {
        return schülerList.size();
    }

    public Schüler getSchülerAtIndex(int index) {
        return schülerList.get(index);
    }

    public Schüler getSchüler(int x, int y, int radius) {
        ArrayList<Schüler> schülerInRadius = new ArrayList<>();

        for (Schüler schüler : schülerList) {
            int schülerX = (int) schüler.getX();
            int schülerY = (int) schüler.getY();

            double distance = Math.sqrt(Math.pow(schülerX - x, 2) + Math.pow(schülerY - y, 2));

            if (distance <= radius) {
                schülerInRadius.add(schüler);
            }
        }

        if (!schülerInRadius.isEmpty()) {
            int randomIndex = (int) (Math.random() * schülerInRadius.size());
            return schülerInRadius.get(randomIndex);
        } else {
            return null;
        }
    }

}
