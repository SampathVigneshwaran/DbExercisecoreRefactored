package com.db.TradeCapturingSystem.controller;

import java.time.LocalDate; 

/*Having a validator as an interface so that we can use this validator for any other instruments 
*/


public interface Validator {
	
	public final LocalDate today = LocalDate.now();
	
	public boolean validateNotLowerVersion(Trade trade);
	
	public boolean validateMaturityDate(Trade trade);
	
	public boolean validate(Validator validateTrade, Trade trade);

}