package com.restaurant.handler;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.exceptions.NoRecordAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalHandler extends ResponseEntityExceptionHandler {
    public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException excp){
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMsg(excp.getMessage());
        res.setData("Failure");
        return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<ResponseStructure<String>>  handleNRAE(NoRecordAvailableException excp){
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMsg(excp.getMessage());
        res.setData("Failure");
        return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
    }
}
