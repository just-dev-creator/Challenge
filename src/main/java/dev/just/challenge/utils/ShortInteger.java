package dev.just.challenge.utils;

public class ShortInteger {
    public static String run(int duration) {
        String string = "";
        int seconds = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        if (duration / 60 / 60 / 24 >= 1) {
            days = duration / 60 / 60 / 24;
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1)
            seconds = duration;
        if (days >= 1)
            if (days == 1) {
                string = days + " Tag";
            } else {
                string = days + " Tage";
            }
        if (hours != 0)
            if (hours <= 9) {
                string = string + "0" + hours + ":";
            } else {
                string = string + hours + ":";
            }
        if (minutes <= 9) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds <= 9) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }
}
