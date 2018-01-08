package com.demo.oauth2.exception;

public class ApplicationException extends Exception {

    public ApplicationException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ApplicationException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ApplicationException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
