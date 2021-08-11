/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

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