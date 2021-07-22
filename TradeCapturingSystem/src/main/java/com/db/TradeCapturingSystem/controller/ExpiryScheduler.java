package com.db.TradeCapturingSystem.controller;

import java.util.TimerTask;
/*
 * Expiry Flag change scheduler
 * */
public class ExpiryScheduler extends TimerTask{

	public void run() {
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		tradeStoreInstance.changeExpiryStatus();
	}
}
