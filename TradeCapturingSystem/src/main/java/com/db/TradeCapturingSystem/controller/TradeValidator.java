package com.db.TradeCapturingSystem.controller;

import java.time.Period;
import java.time.LocalDate;
/*
 * Implemented the validator class
 * */
public class TradeValidator<T extends Trade> implements  Validator<T>{
	
	
	@Override
	public boolean validateNotLowerVersion(T trade) throws LowerVersionException {
		
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		
		int existingTradeVersion = tradeStoreInstance.getCurrentTradeVersion(trade.getTradeId());
		
		if (existingTradeVersion == -1) {

			return true;
		}
		

		if ( trade.getVersion() >= existingTradeVersion ) {

			return true;	
		}		
		else {
			throw new LowerVersionException(trade.getTradeId());
			//throw new RuntimeException("Exception!! Trade " + trade.getTradeId()+ " version is Lower than existing version !!");
		}

	}
	
	
	@Override
	public boolean validateMaturityDate(T trade) {
		LocalDate yesterday = today.minus(Period.ofDays(1));
		return (trade.getMaturityDate().isAfter(yesterday)) ? true : false;
	}
	
	
	@Override
	public boolean validate(T trade) throws LowerVersionException {
		if (this.validateNotLowerVersion( trade)) {
			return true;
		}
		/* commented this validation since this has been taken care in the client side
		boolean v2 = this.validateMaturityDate( trade);
		if (v1 && v2) {
			return true;
		}*/
		return false;
	}


}
