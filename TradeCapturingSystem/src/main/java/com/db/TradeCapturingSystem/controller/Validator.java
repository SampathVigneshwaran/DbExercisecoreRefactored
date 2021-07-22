package com.db.TradeCapturingSystem.controller;

import java.time.LocalDate; 

public interface Validator {
	
	public final LocalDate today = LocalDate.now();
	
	public boolean validateNotLowerVersion(Trade trade);
	
	public boolean validateMaturityDate(Trade trade);
	
	public boolean validate(Validator validateTrade, Trade trade);

}