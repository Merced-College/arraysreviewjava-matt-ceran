/**
 * Blackjack game with arrays
 * 
 * name: Ahmet Ceran
 * January 29th 2026 
 * 8:38pm (SOLO) - Sorry, realized this assignment was due and was too late to join a group as they are all locked on canvas.
 * I implemented multiple rounds
 * Specifically: The ability to play multiple rounds
 * Ability to track wins, losses, and ties
 * Displaying final statistics at the end
 */



import java.util.Random;  // for shuffling
import java.util.Scanner; // for input

public class BlackJack {

    // the 4 suits
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    
    // card ranks 2 thru ace
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    
    // 52 card deck
    private static final int[] DECK = new int[52];
    
    // tracks position in deck
    private static int currentCardIndex = 0;

    // counters for game stats
    private static int playerWins = 0;
    private static int dealerWins = 0;
    private static int ties = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // controls game loop
        boolean playAgain = true;

        // main loop for mulitple rounds
        while (playAgain) {
            // reset deck position
            currentCardIndex = 0;
            
            // setup and shuffle
            initializeDeck();
            shuffleDeck();

            // deal inital cards
            int playerTotal = dealInitialPlayerCards();
            int dealerTotal = dealInitialDealerCards();

            // players turn
            playerTotal = playerTurn(scanner, playerTotal);
            
            // check if busted
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins.");
                dealerWins++;
            } else {
                // dealer goes if player didnt bust
                dealerTotal = dealerTurn(dealerTotal);
                determineWinner(playerTotal, dealerTotal);
            }

            // show stats
            System.out.println("\n--- Current Statistics ---");
            System.out.println("Wins: " + playerWins + " | Losses: " + dealerWins + " | Ties: " + ties);
            System.out.println("--------------------------\n");

            // ask to play agian
            System.out.println("Do you want to play again? (yes/no)");
            String response = scanner.nextLine().toLowerCase();
            
            if (!response.equals("yes") && !response.equals("y")) {
                playAgain = false;
            }
        }

        // final stats
        System.out.println("\n====== FINAL STATISTICS ======");
        System.out.println("Total Wins: " + playerWins);
        System.out.println("Total Losses: " + dealerWins);
        System.out.println("Total Ties: " + ties);
        System.out.println("Thanks for playing BlackJack!");
        System.out.println("==============================");

        scanner.close();
    }

    // fills deck with 0-51
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }

    // shuffles deck randomly
    private static void shuffleDeck() {
        Random random = new Random();
        
        // loop and swap
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length);
            
            // swap
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
        
        System.out.println("Deck has been shuffled!");
    }

    // deals 2 cards to player
    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        
        // show thier cards
        System.out.println("Your cards: " + RANKS[card1] + " and " + RANKS[card2]);
        
        return cardValue(card1) + cardValue(card2);
    }

    // deals 1 visable card to dealer
    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        
        System.out.println("Dealer's visible card: " + RANKS[card1]);
        
        return cardValue(card1);
    }

    // player hits or stands
    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            
            if (action.equals("hit")) {
                // get new card
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                
                System.out.println("You drew a " + RANKS[newCard]);
                
                // check bust
                if (playerTotal > 21) {
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    // dealer hits til 17
    private static int dealerTurn(int dealerTotal) {
        System.out.println("\nDealer's turn...");
        
        // must hit under 17
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
            System.out.println("Dealer drew a " + RANKS[newCard]);
        }
        
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

    // compares totals and determins winner
    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            // player wins
            System.out.println("You win!");
            playerWins++;
        } else if (dealerTotal == playerTotal) {
            // tie
            System.out.println("It's a tie!");
            ties++;
        } else {
            // dealer wins
            System.out.println("Dealer wins!");
            dealerWins++;
        }
    }

    // gets next card
    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    // gets card value, face cards worth 10
    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    // searches array for value
    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}