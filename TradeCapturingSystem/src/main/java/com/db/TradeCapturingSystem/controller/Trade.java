package com.db.TradeCapturingSystem.controller;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;





public class Trade {
	
	private String tradeId;
	
	private int version;
	
	private String counterParty;
	
	private String bookId;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate maturityDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate creationDate;
	
	private char expired;
	
	
	public Trade(String tradeId, int version, String counterParty, String bookId, LocalDate maturityDate, LocalDate creationDate ) {
		this.tradeId = tradeId;
		this.version = version;
		this.counterParty = counterParty;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.creationDate = creationDate;
		this.expired = 'N';
		
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public char getExpired() {
		return expired;
	}

	public void setExpired(char expired) {
		this.expired = expired;
	}
	
	
	
	@Override
    public String toString() {
        return  "TradeId:'" + tradeId + '\'' +
                ", Version:" + version +
                ", CounterParty:'" + counterParty + '\'' +
                ", BookId:'" + bookId + '\'' +
                ", MaturityDate:" + maturityDate +
                ", CreatedDate:" + creationDate +
                ", Expired:'" + expired + '\'';
    }

}