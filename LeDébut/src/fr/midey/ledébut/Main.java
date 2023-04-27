package fr.midey.ledébut;

public class Main {

	public static void main(String[] args) {
		
		
		int money = 1000;
		int pricephone = 800;
		
		if(money>pricephone) {
			money = money - pricephone;
			System.out.println("Le téléphone a bien été acheté" );
			System.out.println("il vous reste " + money + " euros" );
		}
		
		else {
			System.out.println("pas assez d'argent");
		}
		int i = 0
		for(i < 100; i++) {
			System.out.println("nbre de tour" + i);
		}
	}

}

