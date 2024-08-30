package cangkIR;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    private static List<String> cupIDs = new ArrayList<>(); 

    private String CupID;
    private String CupName;
    private int CupPrice;

    public Cup(String cupID, String cupName, int cupPrice) {
        CupID = cupID;
        CupName = cupName;
        CupPrice = cupPrice;
        cupIDs.add(cupID); 
    }

    public String getCupID() {
        return CupID;
    }

    public void setCupID(String cupID) {
        CupID = cupID;
    }

    public String getCupName() {
        return CupName;
    }

    public void setCupName(String cupName) {
        CupName = cupName;
    }

    public int getCupPrice() {
        return CupPrice;
    }

    public void setCupPrice(int cupPrice) {
        CupPrice = cupPrice;
    }

    
    public static String generateCupID() {
        int nextID = cupIDs.size() + 1;
        String newCupID = String.format("CU%03d", nextID);
        while (cupIDs.contains(newCupID)) {
            nextID++;
            newCupID = String.format("CU%03d", nextID);
        }
        return newCupID;
    }


    public static boolean isCupNameExists(List<Cup> cups, String name) {
        for (Cup cup : cups) {
            if (cup.getCupName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

   

}
