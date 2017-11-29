/**
 * Joshua Itagaki and Jose Sandoval
 * CS 420
 * Project 3 - 4 in a line AI game using a-b pruning
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Connect4
{
    private static int maximumTime = 0; // Maximum time in seconds.
    public static int firstTurn = 0;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum amount of time allowed "
                + "for the computer to generate the answer. \nTime(s): ");
        maximumTime = scanner.nextInt();

        System.out.print("Who is moving first?\n1) Player\n2) Opponent\nChoice: ");
        firstTurn = scanner.nextInt();

        Board b = new Board(firstTurn);

        do
        {
            MiniMax.getInstance().search(b, maximumTime);
            b.displayMoves(firstTurn);
            if(firstTurn == 1) {
                b.placeTile(MiniMax.getAITurnX(), MiniMax.getAITurnY(), 'X');
            } else if(firstTurn == 2){
                b.placeTile(MiniMax.getAITurnX(), MiniMax.getAITurnY(), 'X');
            }
        }while(MiniMax.getInstance().checkResult(b) == 0);
    }
}
