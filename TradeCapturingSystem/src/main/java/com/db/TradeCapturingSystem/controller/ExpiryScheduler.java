package com.db.TradeCapturingSystem.controller;

import java.util.TimerTask;

public class ExpiryScheduler extends TimerTask{

	public void run() {
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		tradeStoreInstance.changeExpiryStatus();
	}
}
