//Bansri Shah 110335850

import java.util.*;
public abstract class BankCard {
	private String cardholderName;
	protected long cardNumber;
	protected double currentBalance;
	protected ArrayList<Transaction> transaction = new ArrayList <Transaction> ();
	
	public BankCard(String cardholderName, long cardNumber) {
		this.cardholderName = cardholderName;
		this.cardNumber = cardNumber;
		this.currentBalance = 0.0;
	}
	
	public double balance() {
		return ((int)(this.currentBalance * 100)) / (double)(100) ;
	}
	
	public long number() {
		return this.cardNumber;
	}
	
	public String cardHolder() {
		return this.cardholderName;
	}
	
	public String toString() {
		return "Card # " + number() + "  Balance:$" + balance();
	}
	
	public abstract boolean addTransaction(Transaction t);
	
	public abstract void printStatement();
}
