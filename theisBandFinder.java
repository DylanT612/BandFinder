/*
I certify, that this computer program submitted by me is all of my own work.
Signed: Dylan Theis 9/10/2024

Author: Dylan Theis
Date: Fall 2024
Class: CSC420
Project: Band Finder
Description: Search given file for specific band name or set time of when a band is playing.
*/


import java.io.*;
import java.util.Scanner;

public class theisBandFinder {
    // Create a band array
    private static BandInfo[] bands = new BandInfo[100];
    // Create band counter
    private static int bandCount = 0;

    public static void main(String[] args) {
        // Intro quip
        System.out.println("Submitted by: Dylan Theis - theisd@csp.edu");
        System.out.println("I certify that this is my own work.");
        // Import band info
        loadBands("bandinfo.txt");
        // New scanner
        Scanner scanner = new Scanner(System.in);

        // Start loop asking user to search for band name for set time
        boolean searching = true;
        while (searching) {
            System.out.println("Search by Band Name (1) or Set List (2):");
            int choice = scanner.nextInt();
            scanner.nextLine();

            // If searching by band name
            if (choice == 1) {
                System.out.println("Enter Band Name you are looking for:");
                // Ask for band they are searching for
                String bandName = scanner.nextLine();
                // Run searchByBandName using bandName
                BandInfo result = searchByBandName(bandName);
                // If result is found or not found
                if (result != null) {
                    System.out.println("Band found is: " + result);
                } else {
                    System.out.println("Band [" + bandName + "] was not found");
                }
            // If searching by set list
            } else if (choice == 2) {
                System.out.println("Enter the Set Time you are looking for:");
                // Ask for set time
                float setTime = scanner.nextFloat();
                // Run searchBySetTime using setTime
                BandInfo result = searchBySetTime(setTime);
                // Display the closest band with selected set time
                System.out.println("Band with closest set time is: " + result);
            }
        }
    }

    // Getting the bands from the file
    private static void loadBands(String filename) {
        // Try to read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Create var
            String line;
            // While the line is not null
            while ((line = reader.readLine()) != null) {
                // Split line into 2 parts (name and set time)
                String[] parts = line.split("\\|");
                // If the line is correctly formatted and has two parts
                if (parts.length == 2) {
                    // Take the first part of the read line and trim it
                    String bandName = parts[0].trim();
                    // Take the second part of the read line and trim it
                    float setTime = Float.parseFloat(parts[1].trim());
                    // Take the trimmed parts and insert them as a new band in the bands array
                    // While increasing band count
                    bands[bandCount++] = new BandInfo(bandName, setTime);
                }
            }
        // Catch exception
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the loaded bands
        sortBySetTime();
        sortByBandName();
    }

    // Sorting algorithm(O(n^2)) since we cant use collections.sort
    private static void sortByBandName() {
        // For each band
        for (int i = 0; i < bandCount - 1; i++) {
            // Compare to next band
            for (int j = i + 1; j < bandCount; j++) {
                // If the first band is more than the second band
                if (bands[i].getBandName().compareToIgnoreCase(bands[j].getBandName()) > 0) {
                    // Assign our first band as temp
                    BandInfo temp = bands[i];
                    // J as our i band
                    bands[i] = bands[j];
                    // And tep as our J band
                    bands[j] = temp;
                }
            }
        }
    }
    // Sorting algorithm(O(n^2)) since we cant use collections.sort
    private static void sortBySetTime() {
        // For each band
        for (int i = 0; i < bandCount - 1; i++) {
            // Compare to next band
            for (int j = i + 1; j < bandCount; j++) {
                // If the first band is more than the second band
                if (bands[i].getSetTime() > (bands[j].getSetTime())) {
                    // Assign our first band as temp
                    BandInfo temp = bands[i];
                    // J as our i band
                    bands[i] = bands[j];
                    // And tep as our J band
                    bands[j] = temp;
                }
            }
        }
    }

    // Recursive binary search on bandName(O(log n))
    private static BandInfo searchByBandName(String bandName) {
        return binarySearchByBandName(bandName, 0, bandCount - 1);
    }

    // Binary searching for the name(O( log n))
    private static BandInfo binarySearchByBandName(String bandName, int left, int right) {
        // Base case(element not in array)
        if (left > right) {
            return null;
        }
        // Find the middle
        int mid = (left + right) / 2;
        // Compare searched band 0 if equal, negative if smaller, positive if bigger
        int compareResult = bands[mid].getBandName().compareToIgnoreCase(bandName);
        // If the compared result is 0(middle of array) is bandName return name
        if (compareResult == 0) {
            return bands[mid];
        // If result is less than 0(right half) refine search
        } else if (compareResult < 0) {
            return binarySearchByBandName(bandName, mid + 1, right);
        // If result is more than 0(left half) refine search
        } else {
            return binarySearchByBandName(bandName, left, mid - 1);
        }
    }

    // O(n) search for the closet band with the given set time
    private static BandInfo searchBySetTime(float setTime) {
        // Create an empty closest band
        BandInfo closestBand = null;
        // Assign the difference max to narrow it down to the lowest
        float closestDifference = Float.MAX_VALUE;

        // for each band in the array
        for (int i = 0; i < bandCount; i++) {
            // Find the difference of the bands setTime vs our searched for setTime
            float difference = Math.abs(bands[i].getSetTime() - setTime);
            // If difference is less than the closestDifference
            // Set as closest difference and closest band
            if (difference < closestDifference) {
                closestDifference = difference;
                closestBand = bands[i];
            }
        }
        // return our closest band to our searched for setTime
        return closestBand;
    }
}
