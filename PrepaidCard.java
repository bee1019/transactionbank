//Bansri Shah 110335850

public class PrepaidCard extends BankCard{
	boolean fraud;
	
	public PrepaidCard(String cardHolder, long cardNumber, double balance) {
		super(cardHolder, cardNumber);
		super.currentBalance = balance;
	}
	
	public PrepaidCard(String cardHolder, long cardNumber) {
		super(cardHolder, cardNumber);
		super.currentBalance = 0;
	}
	
	public boolean addTransaction(Transaction t) {
		if(fraudAlert() == false) {
		if((t.type().equals("debit")) && (t.amount() <= super.balance())) {
			super.currentBalance = super.currentBalance - t.amount();
			super.transaction.add(t);
			return true;
		}
		
		else if((t.type().equals("debit")) && (t.amount() > super.balance())) {
			return false;
		}
		
		else if(t.type().equals("credit")) {
			super.currentBalance = super.currentBalance - t.amount();
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
	
	public boolean addFunds(double amount) {
		if(amount > 0) {
			super.currentBalance += amount;
			Transaction t2 = new Transaction("top-up", "User payment", (-1) * amount);
			super.transaction.add(t2);
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public String toString() {
		return "Card # " + super.number() + "  Balance:$" + super.balance();
	}
	
	public void printStatement() {
		System.out.println();
		System.out.println();
		System.out.println("----------------------");
		System.out.println("Cardholder: " + super.cardHolder());
		System.out.println("Card Number: " + super.number());
		System.out.println("Current Balance: $" + super.balance());
		System.out.println();
		
		for(int i = 0; i < transaction.size(); i++) {
			System.out.println(super.transaction.get(i).toString());
	}
		System.out.println("----------------------");
	}
}
