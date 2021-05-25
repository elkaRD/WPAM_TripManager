package com.elkard.tripmanager.exceptions;

public class UnexpectedException extends RuntimeException {

    public UnexpectedException(String msg)
    {
        super("UnexpectedException: " + msg);
    }

    public UnexpectedException(String msg, Throwable err)
    {
        super("UnexpectedException: " + msg, err);
    }
}
