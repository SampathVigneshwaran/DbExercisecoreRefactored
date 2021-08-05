package com.db.TradeCapturingSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.TradeCapturingSystem.controller.Trade;
import com.db.TradeCapturingSystem.controller.TradeStore;
import com.db.TradeCapturingSystem.controller.TradeValidator;

@SpringBootTest
class TradeCapturingSystemApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void testTrade() {
		Trade trade = new Trade("T1", 1, "MEGA", "NEMU", LocalDate.of(2021, 7, 22), LocalDate.of(2021, 7, 22));
		String str = "TradeId:'T1', Version:1, CounterParty:'MEGA', BookId:'NEMU', MaturityDate:2021-07-22, CreatedDate:2021-07-22, Expired:'N'";
		assertEquals(str, trade.toString());
		
	}
	
	@Test
	void testTradeValidator() {
		
		TradeValidator<Trade> tradeValidator = new TradeValidator<Trade>();
		Trade trade = new Trade("T1", 1, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		boolean out = tradeValidator.validate(trade);
		assertEquals(true, out);
		
		
		LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));
		Trade trade1 = new Trade("T1", 1, "MEGA", "NEMU", yesterday, LocalDate.now());
		boolean out1 = tradeValidator.validateMaturityDate(trade1);
		assertEquals(false, out1);
		
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		Trade trade2 = new Trade("T1", 1, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		tradeStoreInstance.addTradeToStore(trade2);
		Trade trade3 = new Trade("T1", 0, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		try {
			tradeValidator.validateNotLowerVersion(trade3);
		}catch (Exception e) {
			assertEquals("Exception!! Trade T1 version is Lower than existing version !!", e.getMessage());
		}
	}
	
	@Test
	void testTradeStore() {
		
		Trade trade = new Trade("T1", 10, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		tradeStoreInstance.addTradeToStore(trade);
		assertEquals(10, tradeStoreInstance.getCurrentTradeVersion("T1"));
		assertEquals(true,tradeStoreInstance.isTradeUpdate(trade));
		assertEquals(10, tradeStoreInstance.getCurrentTradeVersion("T1"));
		
		Trade trade1 = new Trade("T2", 10, "MEGA", "NEMU", LocalDate.of(2021, 7, 22), LocalDate.of(2021, 7, 22));
		tradeStoreInstance.addTradeToStore(trade1);
		Trade t = tradeStoreInstance.getTrade("T2");
		String str = "TradeId:'T2', Version:10, CounterParty:'MEGA', BookId:'NEMU', MaturityDate:2021-07-22, CreatedDate:2021-07-22, Expired:'N'";
		assertEquals(str, t.toString());
	}
	
	@Test
	void testTradeCapturingSystem() {
		
		Trade[] trades = new Trade[4];
		trades[0] = new Trade("T1", 1, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		trades[1] = new Trade("T2", 1, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		trades[2] = new Trade("T1", 4, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		trades[3] = new Trade("T1", 0, "MEGA", "NEMU", LocalDate.now(), LocalDate.now());
		
		String compareOutList = "[TradeId:'T1', Version:4, CounterParty:'MEGA', BookId:'NEMU', MaturityDate:2021-08-05, CreatedDate:2021-08-05, Expired:'N', TradeId:'T1', Version:1, CounterParty:'MEGA', BookId:'NEMU', MaturityDate:2021-08-05, CreatedDate:2021-08-05, Expired:'N', TradeId:'T2', Version:1, CounterParty:'MEGA', BookId:'NEMU', MaturityDate:2021-08-05, CreatedDate:2021-08-05, Expired:'N']";
		
		TradeValidator<Trade> tradeValidator = new TradeValidator<Trade>();
		TradeStore tradeStoreInstance = TradeStore.getTradeStoreInstance();
		tradeStoreInstance.clearStoreData();
		try {
		for (Trade trade :  trades) {
		if (tradeValidator.validate(trade)) {
			if (tradeStoreInstance.isTradeUpdate(trade)) {
				tradeStoreInstance.updateTradeToStore(trade);
			}
			else {
				tradeStoreInstance.addTradeToStore(trade);
				}
			}
		}
		}
		catch(Exception e) {
			assertEquals("Exception!! Trade T1 version is Lower than existing version !!", e.getMessage());
		}
		
		List<Trade> tradeList = tradeStoreInstance.getAllTrades();
		List<String> outStringList = new  ArrayList<String>();
		for (Trade trade : tradeList) {
			
			outStringList.add(trade.toString());
		}
		
		assertEquals(compareOutList, outStringList.toString());
		
		
	

	}

}
