package com.db.TradeCapturingSystem.controller;

public class TradeValidator implements  Validator{
	
	@Override
	public boolean validateNotLowerVersion(Trade trade) {
		
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		
		int existingTradeVersion = tradeStoreInstance.getCurrentTradeVersion(trade.getTradeId());
		
		if (existingTradeVersion == -1) {

			return true;
		}

		if ( trade.getVersion() >= existingTradeVersion ) {

			return true;	
		}		
		else {
			throw new RuntimeException("Exception!! Trade "+ trade.getTradeId() + " version is Lower than existing version !!");
		}

	}
	
	
	@Override
	public boolean validateMaturityDate(Trade trade) {

		return (trade.getMaturityDate().isAfter(today)) ? true : false;
	}
	
	
	@Override
	public boolean validate(Validator validateTrade, Trade trade) {
		boolean v1 = validateTrade.validateNotLowerVersion( trade);
		boolean v2 = validateTrade.validateMaturityDate( trade);
		if (v1 && v2) {
			return true;
		}
		return false;
	}


}
