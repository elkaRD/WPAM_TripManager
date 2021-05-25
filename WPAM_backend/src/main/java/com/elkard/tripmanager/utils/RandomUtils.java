package com.elkard.tripmanager.utils;

public class RandomUtils {

    public static String alphanumericString(int length)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++)
        {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
