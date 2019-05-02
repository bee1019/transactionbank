//Bansri Shah 110335850

// Driver class for the final project
import java.util.*;
import java.io.*;

public class TransactionProcessor
{
	private static String getCardType (long number)
	{
		// Return a String indicating whether 'number' belongs to a 
		// CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
		// of the three)
		
		String result;
		
		int firstTwo = Integer.parseInt(("" + number).substring(0,2));
		
		switch(firstTwo)
		{
			case 84:
			case 85: result = "CreditCard"; break;
			case 86:
			case 87: result = "RewardsCard"; break;
			case 88:
			case 89: result = "PrepaidCard"; break;
			default: result = null; // invalid card number
		}
		
		return result;
	}
	
	public static BankCard convertToCard(String data) {
		long cardNumber;
		String cardHolder;
		int expiration = 0;
		double limit = 0.0;
		
		Scanner scan = new Scanner(data);
		cardNumber = scan.nextLong();
		
		if(getCardType(cardNumber).equals(null)) {
			scan.hasNextLine();
		}
	
		else if(getCardType(cardNumber).equals("CreditCard")) {
				cardHolder = scan.next();
				cardHolder = cardHolder.replaceAll("_", " ");
				expiration = scan.nextInt();
				limit = scan.nextDouble();
				
				if(limit == 0) {
					CreditCard cc1;
					 return cc1 = new CreditCard(cardHolder, cardNumber, expiration);
				}
				
				else {
					CreditCard cc2;
					return cc2 = new CreditCard(cardHolder, cardNumber, expiration, limit);
				}
			}
			
		else if(getCardType(cardNumber).equals("RewardsCard")) {
				cardHolder = scan.next();
				cardHolder = cardHolder.replaceAll("_", " ");
				expiration = scan.nextInt();
				limit = scan.nextDouble();

				if(limit == 0.0) {
					RewardsCard rc1;
					return rc1 = new RewardsCard(cardHolder, cardNumber, expiration);
				}
				
				else {
					RewardsCard rc2;
					return rc2 = new RewardsCard(cardHolder, cardNumber, expiration, limit);
				}
			}
			
	else if(getCardType(cardNumber).equals("PrepaidCard")) {
				cardHolder = scan.next();
				cardHolder = cardHolder.replaceAll("_", " ");
					PrepaidCard pc1;
					return pc1 = new PrepaidCard(cardHolder, cardNumber);
			}
		
		return null;
	}
	
	public static CardList loadCardData(String fName) {
		CardList c = new CardList();
		
		try {
			File file = new File(fName);
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String cardLine = scan.nextLine();
				c.add(convertToCard(cardLine));
			}

		}
		
		catch(IOException ex) {
			return null;
		}
		
		return c;
	}
	
	public static void processTransactions(String filename, CardList c) {
		int index = 0;
		try {
			File f = new File(filename);
			Scanner scan = new Scanner(f);
			while(scan.hasNextLine()) {
				String [] line = scan.nextLine().split(" ");
				long cardNumber = Long.parseLong(line[0]);
				for(int i = 0; i < c.size(); i++) {
					if(cardNumber == c.get(i).number()) {
						index = i;
					}
				}
				
				String cardType = line[1];
				
				if(cardType.equals("redeem")) {
					int points = Integer.parseInt(line[2]);
					RewardsCard rc = (RewardsCard) c.get(index);
					rc.redeemPoints(points);
				}
				
				else if(cardType.equals("top-up")) {
					double addAmt = Double.parseDouble(line[2]);
					PrepaidCard pc = (PrepaidCard) c.get(index);
					pc.addFunds(addAmt);
				}
				
				else if(cardType.equals("advance")) {
					double cashAdv = Double.parseDouble(line[2]);
					CreditCard cc = (CreditCard) c.get(index);
					cc.getCashAdvance(cashAdv);
				}
				
				else {
					double tranAmt = Double.parseDouble(line[2]);
					String merchant = line[3];
					merchant = merchant.replaceAll("_", " ");
					BankCard bc = (BankCard) c.get(index);
					Transaction t = new Transaction(cardType, merchant, tranAmt);
					bc.addTransaction(t);
				}
			}
		}
		
		catch(IOException e) {
			
		}
	}
	
	public static void main (String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the name of the account list file:");
		String cardfileName = scan.nextLine();
		
		loadCardData(cardfileName);
		CardList cL = loadCardData(cardfileName);
		System.out.println("Read data for " + cL.size() + " account(s).");
		
		System.out.println();
		
		if(loadCardData(cardfileName) != null) {
			System.out.print("Please enter the name of a transaction file: ");
			String tranfileName = scan.nextLine();
			
			processTransactions(tranfileName, cL);
			
			System.out.println("Transaction processing results");
			System.out.println("------------------------------");
			
			for(int i = 0; i < cL.size(); i++) {
				cL.get(i).printStatement();
			}
		}
		
	}
}
