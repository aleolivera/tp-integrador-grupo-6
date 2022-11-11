package com.example.comedores.util;

import java.util.regex.Pattern;

public class Validacion {
    public static final String EMAIL="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String LETRAS_ESPACIOS="[a-zA-Z ]*$";
    public static final String NUMEROS="^[0-9]*$";
    public static final String LETRAS_NUMEROS_ESPACIOS="^[A-Za-z0-9\\s]+$";
    public static final String SIN_ESPACIOS="^\\S*$";
    public Validacion() {}

    public static boolean validarString(String text, String pattern) {
        return Pattern.compile(pattern).matcher(text).matches();
    }
}
