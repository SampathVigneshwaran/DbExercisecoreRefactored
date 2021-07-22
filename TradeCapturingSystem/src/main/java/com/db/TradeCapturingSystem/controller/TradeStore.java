package com.db.TradeCapturingSystem.controller;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;

@Service
public class TradeStore {
	
	private static TradeStore dbInstance = null;
	
	private  Map<String, List<Trade>> tradingDb = null;
	
	private TradeStore() {
		
			tradingDb = new HashMap<String, List<Trade>>();	 
			ExpiryScheduler es = new ExpiryScheduler();

			Timer timer = new Timer();
			timer.schedule(es, 300);

	}
	
	public static TradeStore getTradeStoreInstance() {
		
		if (dbInstance == null) {
			
			dbInstance = new TradeStore(); 
		}
		
		return dbInstance;
		
	}
	
	
	public Map<String, List<Trade>> getTradeStore(){
		return tradingDb;
	}

	public void addTradeToStore(Trade trade) {
		List<Trade> verList = tradingDb.get(trade.getTradeId());
		if ( verList == null) {
			verList = new  ArrayList<Trade>();
		}
		verList.add(trade);
		tradingDb.put(trade.getTradeId(), verList);
	}
	
	public void updateTradeToStore(Trade trade) {
		List<Trade> verList = tradingDb.get(trade.getTradeId());
		int vSize = verList.size();
		verList.remove(vSize-1);
		verList.add(trade);
		tradingDb.put(trade.getTradeId(), verList);
	}
	
	
	public Trade getTrade(String tardeId) {
		if (tradingDb.get(tardeId) != null) {
			int idx = tradingDb.get(tardeId).size();
			return tradingDb.get(tardeId).get(idx-1);
		}
		return null;
	}
	
	public Integer getCurrentTradeVersion(String tardeId) {
		if (tradingDb.get(tardeId) != null) {
			int idx = tradingDb.get(tardeId).size();
			return tradingDb.get(tardeId).get(idx-1).getVersion();
		}
		return -1;
	}
	
	public boolean isTradeUpdate(Trade trade) {
		
		if ( trade.getVersion() == this.getCurrentTradeVersion(trade.getTradeId())){
		return true;
		}
		return false;
	}

	
	public List<Trade> getAllTrades(){
		List<Trade> finalTradeList = new ArrayList<Trade>();
		for (List<Trade>  tradeList: tradingDb.values()) {
			List<Trade> tempTradeList = new ArrayList<Trade>();
			tempTradeList.addAll(tradeList);
			Collections.reverse(tempTradeList);
			finalTradeList.addAll(tempTradeList);
		}
		
		return finalTradeList;
	}
	
	public void changeExpiryStatus() {
		for (List<Trade>  tradeList: tradingDb.values()) {
			
			for (Trade trade : tradeList) {
				if (trade.getMaturityDate().isBefore(LocalDate.now())) {
					trade.setExpired('Y');
				}
			}
			
		}
	}
	

}