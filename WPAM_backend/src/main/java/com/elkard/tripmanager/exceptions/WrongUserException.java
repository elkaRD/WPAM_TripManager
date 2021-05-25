package com.elkard.tripmanager.exceptions;

public class WrongUserException extends Exception {

    public WrongUserException()
    {
        super("WrongUserWxception");
    }

    public WrongUserException(String msg)
    {
        super("WrongUserException: " + msg);
    }

    public WrongUserException(String msg, Throwable err)
    {
        super("WrongUserException: " + msg, err);
    }
}