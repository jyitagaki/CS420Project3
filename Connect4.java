/**Joshua Itagaki and Jose Sandoval
 *CS 420
 * Project 3 - 4 in a line AI game using a-b pruning
 */
import java.util.Scanner;

public class Connect4 {
    private Board b;
    private Scanner scan;
    private int maxDepth = 5;
    private int locationX = -1; //row
    private int locationY = -1; //column
    public Connect4(Board b){
        this.b = b;
        scan = new Scanner(System.in);
    }

    public int miniMax(int depth, int turn, int alpha, int beta){
        if(beta <= alpha){
            if(turn == 1){
                return Integer.MAX_VALUE;
            } else{
                return Integer.MIN_VALUE;
            }
        }
        int result = checkResult(b);
        if(result == 1){
            return Integer.MAX_VALUE/2;
        } else if(result == 2){
            return Integer.MIN_VALUE/2;
        } else if(result == 0){
            return 0;
        }
        if(depth == maxDepth){
            return evalBoard(b);
        }
        int maxScore = Integer.MIN_VALUE;
        int minScore = Integer.MAX_VALUE;
        for(int i = 0; i<=8; i++){
            for(int j = 0; j<=8; j++){
                int currScore = 0;
                if(!b.isLegal(i,j)) continue;
                if(turn == 1){
                    b.placeTile(i,j,1);
                    currScore = miniMax(depth+1, 2, alpha, beta);
                    if(depth == 0){
                        if(currScore > maxScore){
                            locationX = i;
                            locationY = j;
                        }
                        if(currScore == Integer.MAX_VALUE/2){
                            b.undo(i,j);
                            break;
                        }
                    }
                    maxScore = Math.max(currScore, maxScore);
                    alpha = Math.max(currScore, alpha);
                }
                else if(turn == 2){
                    b.placeTile(i, j, 2);
                    currScore = miniMax(depth+1,1,alpha,beta);
                    minScore = Math.min(currScore, minScore);
                    beta = Math.min(currScore,beta);
                }
                b.undo(i,j);
                if(currScore == Integer.MAX_VALUE || currScore == Integer.MIN_VALUE){
                    break;
                }
            }
        }
        return turn == 1? maxScore:minScore;
    }
    //checks favorability for AI
    private int evalBoard(Board b) {
    }

    //checks game result win or lose
    private int checkResult(Board b) {
    }

    public static void main(String[] args){
        Board b = new Board();
        b.displayBoard();
    }
}

class Board{
    char[][] board = new char[9][9];
    public static final int a = 1, b = 2, c = 3, d = 4, e = 6, f = 7, g = 8, h = 9;
    public Board(){
        board = new char[][]{
                {' ','1','2','3','4','5','6','7','8'},
                {'a','-','-','-','-','-','-','-','-'},
                {'b','-','-','-','-','-','-','-','-'},
                {'c','-','-','-','-','-','-','-','-'},
                {'d','-','-','-','-','-','-','-','-'},
                {'e','-','-','-','-','-','-','-','-'},
                {'f','-','-','-','-','-','-','-','-'},
                {'g','-','-','-','-','-','-','-','-'},
                {'h','-','-','-','-','-','-','-','-'},
        };
    }
    //Can move be made
    public boolean isLegal(int row, int column){
        return board[row][column]=='-';
    }
    //Places tile on board
    public boolean placeTile(int row, int column, int player){
        if(!isLegal(row, column)){
            System.out.println("Cannot place there");
        }
        if(board[row][column] == '-'){
            board[row][column] = (char)player;
            return true;
        }
        return false;
    }
    //Shows board
    public void displayBoard(){
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //Displays movement
    public void displayMoves(int player, char[] moves){

    }

    public void undo(int row, int col) {
        if(board[row][col] != '-'){
            board[row][col] = '-';
        }
    }
}
