package Cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.List;
import java.util.Collections;

public class Hand {

    public static List<Integer> fiveOfAKind = new ArrayList<>(List.of(5)); //7000
    public static List<Integer> fourOfAKind = new ArrayList<>(List.of(4, 1));//6000
    public static List<Integer> fullHouse = new ArrayList<>(List.of(3, 2));//5000
    public static List<Integer> threeOfAKind = new ArrayList<>(List.of(3, 1, 1));//4000
    public static List<Integer> twoPairs = new ArrayList<>(List.of(2, 2, 1));//3000
    public static List<Integer> onePair = new ArrayList<>(List.of(2, 1, 1, 1));//2000
    public static List<Integer> highCard= new ArrayList<>(List.of(1,1,1,1,1));//1000
    //public Logger handLogs = new Logger()
    public List<CardHierarchy> hand = new ArrayList<CardHierarchy> (); 
    public Integer Bid=0;
    public Integer HandScore = 0;
    public List<Integer> counts = new ArrayList<Integer>();
    public void scoreHand() {
        if(counts.equals(fiveOfAKind))
            HandScore = 7000;
        else if(counts.equals(fourOfAKind))
            HandScore = 6000;
        else if(counts.equals(fullHouse))
            HandScore = 5000;
        else if(counts.equals(threeOfAKind))
            HandScore = 4000;
        else if(counts.equals(twoPairs))
            HandScore = 3000;
        else if(counts.equals(onePair))
            HandScore = 2000;
        else if(counts.equals(highCard))
            HandScore = 1000;
        else System.out.println("Hand doesn't match anything?");
    }
    public void countHand() {
        Integer val = 0;
        
        List <CardHierarchy> sortedHand = new ArrayList<CardHierarchy>(hand);
        //Collections.copy(sortedHand, hand);
        Collections.sort(sortedHand);
        Collections.reverse(sortedHand);
        CardHierarchy prev = sortedHand.get(0);
        for (CardHierarchy card : sortedHand) {
            if(card == prev) {
                val++;
                //System.out.println("Card " + card + " is first or same as first. " + val);
            }else{
                //System.out.println("Card " + card + " is different than the prev.");
                counts.add(val);
                val=1;
            }
            prev = card;
        }
        counts.add(val);
        Collections.sort(counts);
        Collections.reverse(counts);

        //int countsize=counts.size();
        /*
        if(countsize >= 5) {

        } else{
            counts.addAll(Collections.nCopies(5-countsize, 0));
        }
        */

    }
    public Hand () {
        hand.add(CardHierarchy.ACE);
        hand.add(CardHierarchy.ACE);
        hand.add(CardHierarchy.THREE);
        hand.add(CardHierarchy.FIVE);
        hand.add(CardHierarchy.ACE);
    }
    public Hand(String handString, Integer bidValue){
        for (int i = 0; i < handString.length(); i++) {
 
            // Print current character
            char curChar =handString.charAt(i);
            Bid = bidValue;

            System.out.print(curChar + " ");
            switch(curChar){
                case 'T':
                    hand.add(CardHierarchy.TEN);
                    break;
                case 'J':
                    hand.add(CardHierarchy.JACK);
                    break;
                case 'Q':
                    hand.add(CardHierarchy.QUEEN);
                    break;
                case 'K':
                    hand.add(CardHierarchy.KING);
                    break;
                case 'A':
                    hand.add(CardHierarchy.ACE);
                    break;
                case '2':
                    hand.add(CardHierarchy.TWO);
                    break;
                case '3':
                    hand.add(CardHierarchy.THREE);
                    break;
                case '4':
                    hand.add(CardHierarchy.FOUR);
                    break;
                case '5':
                    hand.add(CardHierarchy.FIVE);
                    break;
                case '6':
                    hand.add(CardHierarchy.SIX);
                    break;
                case '7':
                    hand.add(CardHierarchy.SEVEN);
                    break;
                case '8':
                    hand.add(CardHierarchy.EIGHT);
                    break;
                case '9':
                    hand.add(CardHierarchy.NINE);
                    break;

            }
            
        }
        System.out.println("Hand: " + hand);
       // Collections.reverse(hand);
       // System.out.println("Hand(reverse): " + hand);

        countHand();
        scoreHand();
    }
   
}
