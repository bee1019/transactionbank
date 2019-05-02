//Bansri Shah 110335850

public class RewardsCard extends CreditCard{
	protected int points;
	boolean fraud;
	
	public RewardsCard(String holder, long number, int expiration, double limit) {
		super(holder, number, expiration, limit);
		this.points = 0;
	}
	
	public RewardsCard(String holder, long number, int expiration) {
		super(holder, number, expiration);
		super.limit = 500;
		this.points = 0;
	}
	
	public int rewardPoints() {
		return this.points;
	}
	
	public boolean redeemPoints(int points) {
		if((points <= this.points) && (points / 100.00 <= super.currentBalance)) {
			super.currentBalance = super.currentBalance - (points / 100.00);
			this.points = this.points - points;
			Transaction t1 = new Transaction("redemption", "CSEBank", (points / 100.00));
			transaction.add(t1);
			return true;
		}
		
		else if((points <= this.points) && (points / 100.00 > super.currentBalance)) {
			int newPoints = (int)(super.currentBalance * 100);
			this.points = this.points - newPoints;
			super.currentBalance = 0;
			Transaction t1 = new Transaction("redemption", "CSEBank", (newPoints / 100.00));
			transaction.add(t1);
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public String toString() {
		return super.toString() + "Reward Points: " + this.points;
	}
	
	@Override
	public boolean addTransaction(Transaction t) {
		if(fraudAlert() == false) {
		if((t.type().equals("debit")) && (t.amount() <= availableCredit())) {
			super.currentBalance += t.amount();
			super.transaction.add(t);
			this.points += (t.amount()) * 100;
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
		System.out.println("Expiration: " + super.setExpiration());
		System.out.println("Reward Points : " + rewardPoints());
		System.out.println();
		System.out.println("Transactions for this billing period:");
		
		for(int i = 0; i < super.transaction.size(); i++) {
			System.out.println(super.transaction.get(i).toString());
	}
		System.out.println("----------------------");
	}
}
