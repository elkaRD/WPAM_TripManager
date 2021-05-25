package com.elkard.tripmanager.exceptions;

public class RequestParamsException extends Exception {

    public RequestParamsException()
    {
        super("RequestParamsException");
    }

    public RequestParamsException(String msg)
    {
        super("RequestParamsException: " + msg);
    }

    public RequestParamsException(String msg, Throwable err)
    {
        super("RequestParamsException: " + msg, err);
    }
}
