package com.example.demo.services;

public class TestService {
	
	public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true; // La conversion a réussi, la chaîne est un entier valide.
	    } catch (NumberFormatException e) {
	        return false; // La chaîne n'est pas un entier valide.
	    }
	}
	
	public static boolean intPositifNegatif(int number) {
	    if (number > 0) {
	        return true;
	    } else if (number < 0) {
	        return false;
	    } else {
	        return false; // Le nombre est égal à zéro.
	    }
	}
	
	public static boolean doublePositifNegatif(double number) {
	    if (number > 0) {
	        return true;
	    } else if (number < 0) {
	        return false;
	    } else {
	        return false; // Le nombre est égal à zéro.
	    }
	}
	
	public static boolean isNumeric(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}


}
