package com.farmeco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	//RuntimeException
	@ExceptionHandler(RuntimeException.class)
	public ProblemDetail handleRuntimeException(RuntimeException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	
	//IllegalArgumentException
	@ExceptionHandler(IllegalArgumentException.class)
	public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	//ResourseNotFoundException
	@ExceptionHandler(ResourseNotFoundExceptionClass.class)
	public ProblemDetail handleResourseNotFoundExceptionClass(ResourseNotFoundExceptionClass e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	// BadCredentialsException
	@ExceptionHandler(BadCredentialsException.class)
	public ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	// UsernameNotFoundException
	@ExceptionHandler(UsernameNotFoundException.class)
	public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	// AccountInactiveException
		@ExceptionHandler(AccountInactiveException.class)
		public ProblemDetail handleAccountInactiveException(AccountInactiveException e) {
			return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	
	//AuthorizationDeniedException
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ProblemDetail handleAuthorizationDeniedException(AuthorizationDeniedException e) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
		
	
	
}
