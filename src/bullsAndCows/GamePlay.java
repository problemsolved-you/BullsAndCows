package bullsAndCows;
import java.util.Scanner;

public class GamePlay {
	
	public static void main(String[] args) {
		System.out.println("Welcome to the game Bulls and Cows!\n"
				+ "\nHow to Play:"
				+ "\n1. Guess any four digit number. Hit 'Enter' to submit your guess."
				+ "\n2. Consider the response..."
				+ "\n    a. The number of 'Bulls' is how many digits are in the right spot."
				+ "\n    b. The number of 'Cows' is the number of correct digits that are NOT in the right spot."
				+ "\n3. Keep guessing until you figure out the secret number!"
				+ "\nGoal: Try to guess the secret number in as few attempts as possible!"
				+ "\n\nImportant Note: There are no repeat digits at this stage of development.");
		playGame();
	}
	
	/**
	 * Runs the game Bulls and Cows.
	 */
	public static void playGame() {
		boolean playing = true;
		Scanner input = new Scanner(System.in);
		while(playing) {
			//Setting it all up
			String secretNumber = generateSecretNumber();
			int numAttempts = 1;
			
			//Getting started with the game.
			System.out.println("\nWhat is your first guess?");
			String userGuess = getGuess(input);	//getGuess makes sure the user inputs a 4 digit number.
			while (!compare(secretNumber, userGuess)) {//While the user's guess isn't right
				System.out.println("\nWhat is your next guess?"); 
				userGuess = getGuess(input); //keep allowing for more guesses
				numAttempts++; //and count how many guesses they have made.
				/* Originally used for detecting bugs.
				if(numAttempts > 15) {
					System.out.println(secretNumber);
				}
				*/
			};
			
			//After getting the number correct.
			System.out.println("\nWell done!");
			if (numAttempts == 1) {
				System.out.println("You got it on the first try! What luck!");
			}
			else {
				System.out.println("You got it in " + numAttempts + " attempts.");
			}
			
			//Continuing the game (or not).
			System.out.println("\nDo you want to play again? "
					+ "\nType 'Y' for Yes, or 'N' for No.");
			String again = input.nextLine().toUpperCase();
			while(!(again.equals("Y") || again.equals("N"))) {//Input validation
				System.out.println("Please type 'Y' or 'N'");
				again = input.nextLine().toUpperCase(); //Re-do input if invalid
			}
			playing = again.equals("Y"); //If the user typed 'Y', playing will remain true and this while loop will continue.
		}
	}
	
	/**
	 * Compares two numbers and prints the number of bulls and cows. Also returns true if the two numbers are identical.
	 * @param secretNumber The number to be guessed.
	 * @param userNumber The number that was guessed.
	 * @return true if the two parameter numbers are equal.
	 */
	public static boolean compare(String secretNumber, String userNumber) {
		boolean areSame = false;
		int bulls = 0;
		int cows = 0;
		
		//Calculate # of bulls and cows.
		//TODO This calculation is incompatible with duplicate numbers.
		for (int i = 0; i < secretNumber.length(); i++) {
			char one = secretNumber.charAt(i);
			char two = userNumber.charAt(i);
			if(one == two ) {
				bulls++;
			}
			else if(secretNumber.contains(Character.toString(two))) {
				//My that conversion from char to String to check and see if it exists is mighty tricky!
				cows++;
			}
		}
		
		System.out.print("Bulls: " + bulls + " ");
		System.out.println("Cows: " + cows);
		
		if(bulls == 4) {//4 Bulls means that the player has correctly guessed the number.
			areSame = true;
		}
		
		return areSame;
	}
	
	/**
	 * Generates a random 4 digit number without repeating digits.
	 * @return A string of a 4 digit number without repeating digits.
	 */
	public static String generateSecretNumber() {
		String num = Integer.toString(((int) (Math.random() * 10000)) + 1); //Generate a random number b/w 0 and 10,000
		while (num.length() < 4) {//This loop guarantees that the number is at least 4 digits long.
			num = "0" + num;
		}
		boolean needsRedo = false;
		do {
			needsRedo = false; //This may seem redundant given two lines ago, but it is necessary to reset this 
								//after re-generating each number. (If you think of something more efficient, change this!)
			for(int i = 0; i < num.length(); i++) {//For each digit in the number,
				for(int j = i + 1; j < num.length(); j++) {//check the rest of the number
					if(num.charAt(i) == num.charAt(j)) {//for any repeats.
						needsRedo = true; //Repeated digits require a new number.
					}
				}
			}
			if (needsRedo) {
				num = Integer.toString(((int) (Math.random() * 10000)) + 1);
				while (num.length() < 4) {//This loop guarantees that the number is at least 4 digits long.
					num = "0" + num;
				}
			}
		} while (needsRedo);
		
		return num;
	}
	
	/**
	 * Processes and validates a user's guess.
	 * @param input A Scanner object (so that user input can be processed)
	 * @return A String of a 4-digit number.
	 */
	public static String getGuess(Scanner input) {
		String userNumber = input.nextLine(); 
		boolean valid = false;
		while(!valid) {
			//Step 1 - Verify that it's actually a number.
			try { 
				Integer.parseInt(userNumber); //If this doesn't throw an exception then the input is actually a number.
				valid = true; //Meaning that the user's input passes this test.
			} 
			catch(NumberFormatException n) {
				valid = false; //Meaning this test fails,
				System.out.println("That wasn't even a number!");
				System.out.println("Please actually enter a number this time.");
				userNumber = input.nextLine(); //so take in a new number.
			}
			
			//Step 2 - Verify length
			if (valid) {//Only take this step if it has passed the previous test.
				if(userNumber.length() == 4) { //The user's input must be four digits.
					valid = true; //Meaning it passes this test. Yes it's redundant, but it makes the code clearer.
				}
				else {
					valid = false; //Meaning it failed this test,
					System.out.println("Please enter a 4 digit number.");
					userNumber = input.nextLine(); //so enter a new number.
				}
			}
		}
		return userNumber;
	}
	
}
