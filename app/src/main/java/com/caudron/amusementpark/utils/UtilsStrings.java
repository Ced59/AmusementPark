package com.caudron.amusementpark.utils;

public class UtilsStrings {


    //TODO RENDRE GENERIQUE LE FORMATTAGE DES DONNEES AVEC "."


    public static String extractCountryName(String country) {
        if (country == null || country.isEmpty()) {
            return "";
        }
        int dotIndex = country.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < country.length() - 1) {
            country = country.substring(dotIndex + 1);
        }
        if (country.length() > 1) {
            return Character.toUpperCase(country.charAt(0)) + country.substring(1).toLowerCase();
        } else {
            return country.toUpperCase();
        }
    }


    public static String extractStatus(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        int firstDotIndex = input.indexOf('.');
        if (firstDotIndex == -1 || firstDotIndex == input.length() - 1) {
            throw new IllegalArgumentException("Invalid input format: " + input);
        }
        int secondDotIndex = input.indexOf('.', firstDotIndex + 1);
        if (secondDotIndex == -1) {
            return input.substring(firstDotIndex + 1);
        } else {
            return input.substring(firstDotIndex + 1, secondDotIndex);
        }
    }
}
