package com.gossip_point.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalException {
 @ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e,WebRequest req){
		
		ErrorDetail err=new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}

 @ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException me,WebRequest req){
		
		ErrorDetail err=new ErrorDetail(me.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
 @ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException me,WebRequest req){
		
		ErrorDetail err=new ErrorDetail(me.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}

 @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetail> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException mae,WebRequest req){
		String error = mae.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetail err=new ErrorDetail("Validation error",error,LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
 @ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetail> NoHandlerFoundExceptionHandler(MessageException nhe,WebRequest req){
		
		ErrorDetail err=new ErrorDetail("Endpoint not Found",nhe.getMessage(),LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.NOT_FOUND);
	}


 @ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e,WebRequest req){
		
		ErrorDetail err=new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
}
