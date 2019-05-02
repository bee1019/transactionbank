//Bansri Shah 110335850

import java.util.*;
public class CardList {
	private ArrayList <BankCard> bank;
	
	public CardList() {
		bank = new ArrayList <BankCard> ();
	}
	
	public void add(BankCard b) {
		bank.add(b);
	}
	
	public void add(int index, BankCard b) {
		if(index >= 0 && index < bank.size()) {
			bank.add(index, b);
		}
		
		else {
			bank.add(b);
		}
	}
	
	public int size() {
		return bank.size();
	}
	
	public BankCard get(int index) {
		if(index >= 0 && index < bank.size()) {
			return bank.get(index);
		}
		
		else {
			return null;
		}
	}
	
	public int indexOf(long cardNumber) {
		for(BankCard b : bank) {
			if(b.number() == cardNumber) {
				return bank.indexOf(b);
			}
		}
		return -1;
	}
}
