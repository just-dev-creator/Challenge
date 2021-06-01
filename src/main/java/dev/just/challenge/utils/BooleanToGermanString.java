package dev.just.challenge.utils;

public class BooleanToGermanString {
    public static String main(boolean b) {
        if (b) {
            return "ja";
        } else {
            return "nein";
        }
    }

    public static String main(boolean b, boolean uppercase) {
        if (uppercase) {
            if (b) {
                return "Ja";
            } else {
                return "Nein";
            }
        } else {
            return main(b);
        }
    }
}