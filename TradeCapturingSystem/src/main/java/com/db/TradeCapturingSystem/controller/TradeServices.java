package com.db.TradeCapturingSystem.controller;
import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Get and Post Services to get and update data 
 * */
//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = { "https://db-react-321914.uc.r.appspot.com/" })
@RestController
public class TradeServices {
	
	
	private TradeStore tradeStore = TradeStore.getTradeStoreInstance();
	
	
	private TradeValidator<Trade> tradeValidator = new TradeValidator<Trade>();
	
	
	@PostMapping("/trade")
	public ResponseEntity<String> tradeValidateStore(@RequestBody Trade trade) throws Exception{
	
		try {
		if(tradeValidator.validate(trade)) {
			
			
			if (tradeStore.isTradeUpdate(trade)) {
				tradeStore.updateTradeToStore(trade);
			}
			else {
			tradeStore.addTradeToStore(trade);
			}
		
			
	       }

	        return ResponseEntity.status(HttpStatus.OK).build();
		}
		catch (LowerVersionException Ex) {
	         throw new ResponseStatusException(
	                 HttpStatus.BAD_REQUEST, "Tarde Version is Lower", Ex);
		}
	    }

	    @GetMapping("/trade")
	    public List<Trade> getAllTrades(){
	        return tradeStore.getAllTrades();
	    }
	


}
