package org.murpol.restcar.auxiliary;

import java.util.Random;

public class VINGenerator {

    public static String generateVIN(){
        String numbers = "0123456789";
        String capitalLetters = "ABCDEFGHJKLMNPRSTUVWZ";

        String characterString = numbers+capitalLetters;

        Random random = new Random();
        String newVin = "";
        for(int i=0; i<17; i++){
            int positionInString = random.nextInt(characterString.length());
            newVin += String.valueOf(characterString.charAt(positionInString));
        }
        return newVin;
    }

}
