package com.bayup.ecommerce.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    
    private int statusCode;

    private String message;

    private Object data;

    public enum ResponseStatus{
        OK,
        NOT_FOUND,
        CREATED,
        SERVER_ERROR,
        UNAUTHORIZED,
        BAD_REQUEST,
        FAILED_DEPENCENCY,
        CONFLICT,
        FORBIDDEN
    }

    public Response Result(ResponseStatus status, Object data){
        this.data = data;

        switch (status) {
            case OK:
                this.message = "Succesful";
                this.statusCode = HttpStatus.OK.value();
                break;
            case NOT_FOUND:
                this.message = "Not Found";
                this.statusCode = HttpStatus.NOT_FOUND.value();
                break;
            case CREATED:
                this.message = "Succesfull Created";
                this.statusCode = HttpStatus.CREATED.value();
                break;
            case UNAUTHORIZED:
                this.message = "Unauthorized User";
                this.statusCode = HttpStatus.UNAUTHORIZED.value();
                break;
            case BAD_REQUEST:
                this.message = "Error Bad Request";
                this.statusCode = HttpStatus.BAD_REQUEST.value();
                break;
            case FAILED_DEPENCENCY:
                this.message = "Error dependent obejct";
                this.statusCode = HttpStatus.FAILED_DEPENDENCY.value();
                break;
            case CONFLICT:
                this.message = "Already Exist";
                this.statusCode = HttpStatus.CONFLICT.value();
                break;
            case FORBIDDEN:
                this.message = "Username or password incorrect";
                this.statusCode = HttpStatus.FORBIDDEN.value();
                break;
            default:
                this.data = null;
                this.message = "Internal Server Error";
                this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
                break;
        }

        Response result = new Response();
        result.data = this.data;
        result.message = this.message;
        result.statusCode = this.statusCode;
        return result;
    }
}