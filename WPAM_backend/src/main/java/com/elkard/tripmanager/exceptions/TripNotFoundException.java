package com.elkard.tripmanager.exceptions;

public class TripNotFoundException extends Exception {

    public TripNotFoundException(String msg, Throwable err)
    {
        super(msg, err);
    }

    public TripNotFoundException(String msg)
    {
        super(msg);
    }

    public TripNotFoundException()
    {
        super("Trip Not Found");
    }
}
