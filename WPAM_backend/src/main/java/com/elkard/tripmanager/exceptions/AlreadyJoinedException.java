package com.elkard.tripmanager.exceptions;

public class AlreadyJoinedException extends Exception {

    public AlreadyJoinedException(String tripName)
    {
        super("Already joined trip " + tripName);
    }
}
