/*
I certify, that this computer program submitted by me is all of my own work.
Signed: Dylan Theis 9/10/2024

Author: Dylan Theis
Date: Fall 2024
Class: CSC420
Project: Band Finder
Description: Band characteristics, with appropriate properties and methods.
*/

// For each band it has its own name and set time
class BandInfo {
    private String bandName;
    private float setTime;

    // Constructor
    public BandInfo(String bandName, float setTime) {
        this.bandName = bandName;
        this.setTime = setTime;
    }

    // Get band name
    public String getBandName() {
        return bandName;
    }
    // Set band name
    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    // Get set time
    public float getSetTime() {
        return setTime;
    }

    // Set set time
    public void setSetTime(float setTime) {
        this.setTime = setTime;
    }

    // Band toString
    @Override
    public String toString() {
        return bandName + " has a set time of " + setTime + " minutes";
    }
}



