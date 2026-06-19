package com.pfe.backend.exceptions;

public class ReservationNotFoundException extends Exception{
    public ReservationNotFoundException(String message){
        super(message);
    }
}
