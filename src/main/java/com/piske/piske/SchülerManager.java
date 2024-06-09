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

    public int length() {
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

            double distance = Math.sqrt(Math.pow(schülerX - x * 72, 2) + Math.pow(schülerY - y * 72, 2));

            if (distance <= radius) {
                schülerInRadius.add(schüler);
            }
        }

        Schüler highestPositionSchüler = null;
        double highestPosition = Double.NEGATIVE_INFINITY;

        for (Schüler schüler : schülerInRadius) {
            double schülerPosition = schüler.position;
            if (schülerPosition > highestPosition) {
                highestPosition = schülerPosition;
                highestPositionSchüler = schüler;
            }
        }

        return highestPositionSchüler;
    }

    // public void checkColistion(Projectile projectile) {
    // System.out.println("check colision");
    // for (Schüler schüler : schülerList) {
    // double distance = Math.sqrt(
    // Math.pow(schüler.getX() - projectile.getX(), 2) + Math.pow(schüler.getY() -
    // projectile.getY(), 2));
    // System.out.println(distance);
    // if (distance <= 51) {
    // schüler.hit(projectile.getDamage());
    // projectile.hit();
    // }
    // }
    // }

    public void deleteSchüler(Schüler schüler) {
        schülerList.remove(schüler);
    }

}
