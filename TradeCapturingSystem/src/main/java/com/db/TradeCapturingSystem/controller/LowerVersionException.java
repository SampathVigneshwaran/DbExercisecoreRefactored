package com.db.TradeCapturingSystem.controller;

public class LowerVersionException extends RuntimeException  {
	
	  	private final String tradeId;

	    public LowerVersionException(final String tradeId) {
	        super("Exception!! Trade " + tradeId + " version is Lower than existing version !!");
	        this.tradeId = tradeId;
	    }

	    public String getId() {
	        return tradeId;
	    }
}
