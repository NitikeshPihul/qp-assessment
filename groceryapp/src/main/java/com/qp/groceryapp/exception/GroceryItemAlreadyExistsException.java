package com.qp.groceryapp.exception;

public class GroceryItemAlreadyExistsException extends RuntimeException{
    public GroceryItemAlreadyExistsException(String message){
        super(message);
    }
}
