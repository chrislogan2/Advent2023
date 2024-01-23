import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import Cards.Hand;
//fsjsjdfj
class LearningJava {
    public static Pattern lineRegex= Pattern.compile("^(?<CARDS>[2-9TJQKA]{5})\\s+(?<BID>\\d+)$", Pattern.MULTILINE);
    public List<Cards.Hand> Hands= new ArrayList<Cards.Hand>();
    public void loadHand(String inputFileName) throws IOException{
        //File inputFile = new File(inputFileName);
        //inputFile.setReadOnly();
        //LineIterator it = FileUtils.lineIterator(inputFile, "UTF-8");
        //try {

            Path path = Paths.get(inputFileName);

        try (Stream<String>  lines = Files.lines(path)) {
            lines.forEachOrdered(line->{
                System.out.println(line);
                Matcher m = lineRegex.matcher(line);
                if (m.find( )) {
                    //System.out.println("Found value: " + m.group(0) );
                    String cardString=m.group("CARDS");
                    Integer currentBid=Integer.valueOf(m.group("BID"));
                    System.out.println("Found values: [CARDS]: " + cardString + " [BID]: " + currentBid);
                    Hands.add(new Cards.Hand(cardString, currentBid));
                } else {
                    System.out.println("NO MATCH");
                }
            });
        } catch (IOException e) {
            //error happened
        }finally {
            
           // System.out.println("dunno man");
        }
    }
    public static void main(String[] args) throws IOException{
        //Classy classerson = new Classy(25);
        Cards.CardHierarchy test = Cards.CardHierarchy.TWO;
        
        // a class I invented.
       // Scanner scanner = new Scanner(System.in);
        //System.out.println("Write a Number: " + test.cardValue());

        //read a line
       // String message = scanner.nextLine();
        String message2 = args.toString();
       // System.out.println(Integer.valueOf(message) + "\n the message you wrote( as an integre! ^^^^");
        var x = new LearningJava(); 
        x.loadHand("/Users/chrislogan/projects/Advent2023/day7/input.txt");

        x.Hands.sort(Comparator.comparingInt((Hand hand) -> { 
                    return hand.HandScore;
                })
                    .thenComparingInt((Hand hand) -> hand.hand.get(0).cardValue())
                    .thenComparingInt((Hand hand) -> hand.hand.get(1).cardValue())
                    .thenComparingInt((Hand hand) -> hand.hand.get(2).cardValue())
                    .thenComparingInt((Hand hand) -> hand.hand.get(3).cardValue())
                    .thenComparingInt((Hand hand) -> hand.hand.get(4).cardValue())
                    );
        int rank = 1;
        long totalWinnings =0;
        for(Cards.Hand hand : x.Hands){
            
            System.out.println(hand.hand + "\nHand Array: " + hand.counts + " Rank: " + rank + " Bid: " + hand.Bid);
            totalWinnings=totalWinnings+hand.Bid*rank;
            rank++;
        }
        System.out.println("WINNINGS: " + totalWinnings);
        int wholeNumber = 12;
        double floatingpoint = 1.23456;
        boolean trueOrFalse = true;
        Cards.Hand testHand = new Cards.Hand();
        testHand.countHand();
        List<Integer> compareHand = new ArrayList<Integer>();
        compareHand.add(3);
        compareHand.add(1);
        compareHand.add(1);

        System.out.println("Hand counts: \n" + testHand.counts +  "\n" + compareHand +"\n"+(testHand.counts.equals(compareHand)));
        
        
       //scanner.close();
    }
}