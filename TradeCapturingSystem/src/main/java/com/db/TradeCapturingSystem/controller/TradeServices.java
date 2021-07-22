package com.db.TradeCapturingSystem.controller;
import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class TradeServices {
	
	
	private TradeStore tradeStore = TradeStore.getTradeStoreInstance();
	
	
	private TradeValidator tradeValidator = new TradeValidator();
	
	
	@PostMapping("/trade")
	public ResponseEntity<String> tradeValidateStore(@RequestBody Trade trade) throws Exception{
	
		System.out.println(trade.toString());
		if(tradeValidator.validate(tradeValidator, trade)) {
			
			if (tradeStore.isTradeUpdate(trade)) {
				tradeStore.updateTradeToStore(trade);
			}
			else {
			tradeStore.addTradeToStore(trade);
			}
		
	       }
		else{
	          
	           throw new Exception(trade.getTradeId()+"  Trade Id is not found/ Version is lower");
	       }
	        return ResponseEntity.status(HttpStatus.OK).build();
	    }

	    @GetMapping("/trade")
	    public List<Trade> getAllTrades(){
	        return tradeStore.getAllTrades();
	    }
	


}
