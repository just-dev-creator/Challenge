/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import java.util.Arrays;

public class ShortString {
    public static String run(String[] input, boolean uppercase, boolean removeLetters) {
        String string;
        if (input.length == 1) {
            string = Arrays.toString(input);
        } else if (input.length == 2) {
            string = input[0] + " " + input[1];
        } else if (input.length == 3) {
            string = input[0] + " " + input[1] + " " + input[2];
        } else if (input.length == 4) {
            string = input[0] + " " + input[1] + " " + input[2] + " " + input[3];
        } else if (input.length == 5) {
            string = input[0] + " " + input[1] + " " + input[2] + " " + input[3] + input[4];
        } else if (input.length >= 6) {
            string = "String too long";
        } else {
            string = null;
        }
        if (uppercase) {
            assert string != null;
            string = string.toUpperCase();
        }
        if(removeLetters) {
            assert string != null;
            string = string.replace("[", "");
            string = string.replace("]", "");
        }
        return string;
    }

}
