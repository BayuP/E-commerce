package com.bayup.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    
    private int statusCode;

    private String message;

    private Object data;
}