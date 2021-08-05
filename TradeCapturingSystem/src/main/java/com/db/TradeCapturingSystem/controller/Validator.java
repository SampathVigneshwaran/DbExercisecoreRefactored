package com.db.TradeCapturingSystem.controller;

import java.time.LocalDate; 

/*Having a validator as an interface so that we can use this validator for any other instruments 
*/


public interface Validator<T> {
	
	public final LocalDate today = LocalDate.now();
	
	public boolean validateNotLowerVersion(T t) throws LowerVersionException;
	
	public boolean validateMaturityDate(T t);
	
	public boolean validate(T t) throws LowerVersionException;
	
}