//Bansri Shah 110335850

public class CreditCard extends BankCard {
	private int expiration;
	protected double limit;
	boolean fraud;
	
	public CreditCard(String cardHolder, long cardNumber, int expiration, double limit) {
		super(cardHolder, cardNumber);
		this.expiration = expiration;
		this.limit = limit;
	}
	
	public CreditCard(String cardHolder, long cardNumber, int expiration) {
		super(cardHolder, cardNumber);
		this.limit = 500;
		this.expiration = expiration;
	}
	
	public double limit() {
		return this.limit;
	}
	
	public double availableCredit() {
		return this.limit - super.balance();
	}
	
	public int expiration() {
		return this.expiration;
	}
	
	public String setExpiration() {
		String date = null;
		int exp = expiration();
		String expiration = Integer.toString(exp);
		
		if(expiration.length() == 3) {
			date = expiration.substring(0, 1);
			date = date + "/" + expiration.substring(1);
			
			return date;
		}
		
		else if(expiration.length() == 4) {
			date = expiration.substring(0,2);
			date = date + "/" + expiration.substring(2);
			
			return date;
		}
		
		return date;
	}
	
	public boolean getCashAdvance(double cashRequest) {
		double fee = cashRequest * 0.05;
		
		if((cashRequest + fee) <= availableCredit()) {
			super.currentBalance += cashRequest + fee;
			Transaction t1 = new Transaction("advance", "CSEBank", cashRequest);
			transaction.add(t1);
			Transaction t2 = new Transaction("fee", "Cash advance fee", fee);
			transaction.add(t2);
			return true;
		}
		
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "  Expiration Date: " + expiration();
	}

	public boolean addTransaction(Transaction t) {
		if(fraudAlert() == false) {
		if((t.type().equals("debit")) && (t.amount() <= availableCredit())) {
			super.currentBalance += t.amount();
			super.transaction.add(t);
			return true;
		}
		
		else if((t.type().equals("debit")) && (t.amount() > availableCredit())) {
			return false;
		}
		
		else if(t.type().equals("credit")) {
			super.currentBalance += t.amount();
			super.transaction.add(t);
			return true;
		}
		
		else {
			return false;
		}
		}
		
		else if(fraudAlert() == true) {
			return false;
		}
		
		else {
			return false;
		}
	}
	
	public boolean fraudAlert() {
		if(fraud == false && transaction.size() >= 2 && (transaction.get(transaction.size()-2).type().equals("debit")) &&
				(transaction.get(transaction.size()-1).type().equals("debit")) &&
				(transaction.get(transaction.size()-2).amount() <= 1.50) &&
				(transaction.get(transaction.size()-1).amount() <= 1.50) &&
				(transaction.get(transaction.size()-2).merchant().equals(transaction.get(transaction.size()-1).merchant()))) {
			Transaction t1 = new Transaction("credit", "Fraud", (-1)*transaction.get(transaction.size()-2).amount());
			super.transaction.add(t1);
			Transaction t2 = new Transaction("credit", "Fraud", (-1)*transaction.get(transaction.size()-2).amount());
			super.transaction.add(t2);
			t2.addNotes("** Account frozen due to suspected fraud **");
			fraud = true;
			return true;
		}
		
		if(fraud == true) {
			return true;
		}
		else {
			fraud = false;
			return false;
		}
	}
	
	public void printStatement() {
		System.out.println();
		System.out.println();
		System.out.println("----------------------");
		System.out.println("Cardholder: " + super.cardHolder());
		System.out.println("Card Number: " + super.number());
		System.out.println("Current Balance: $" + super.balance());
		System.out.println("Expiration: " + setExpiration());
		System.out.println();
		
		for(int i = 0; i < super.transaction.size(); i++) {
			System.out.println(super.transaction.get(i).toString());
	}
		System.out.println("----------------------");
}
}
