import java.util.ArrayList;
import java.util.Scanner;

public class Board
{
    public char[][] board = new char[9][9];
    public static int firstTurn = 0;
    private static Scanner scanner = new Scanner(System.in);
    String opponentMove;
    ArrayList<String> moves = new ArrayList<>();

    public Board(int firstTurn){
        this.firstTurn = firstTurn;

        board = new char[][]{
                {' ','1','2','3','4','5','6','7','8'},
                {'A','-','-','-','-','-','-','-','-'},
                {'B','-','-','-','-','-','-','-','-'},
                {'C','-','-','-','-','-','-','-','-'},
                {'D','-','-','-','-','-','-','-','-'},
                {'E','-','-','-','-','-','-','-','-'},
                {'F','-','-','-','-','-','-','-','-'},
                {'G','-','-','-','-','-','-','-','-'},
                {'H','-','-','-','-','-','-','-','-'},
        };
    }
    //Can move be made
    public boolean isLegal(int row, int column){
        return board[row][column]=='-';
    }

    /**
     * Checks if the human player inputted move is a valid move.
     * @param input - The move to check.
     * @return - Returns whether the move is valid or not.
     */
    private boolean isHumanInputValid(String input)
    {
        return input.matches("^[A-H][1-8]");
    }

    //Places tile on board
    public boolean placeTile(int row, int column, char player){
        if(!isLegal(row, column)){
            System.out.println("Cannot place there");
        }

        if(board[row][column] == '-'){
            board[row][column] = player;
            return true;
        }
        return false;
    }

    /**
     * Places a tile chosen by a human if it's valid,
     * otherwise it shows an error message.
     * @param move - The move inputted by the human player.
     * @return - Returns whether the tile was placed or not.
     */
    private boolean placeTile(String move)
    {
        final int ASCII_OFFSET = 64;

        if(isHumanInputValid(move))
        {
            int row = move.charAt(0) - ASCII_OFFSET;
            int column = Character.getNumericValue(move.charAt(1));
            placeTile(row, column, 'O');
            displayBoard();
            return true;
        }

        System.out.println("ERROR: Not a valid move.");
        return false;
    }

    //Shows board
    public void displayBoard(){
        System.out.println();
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Displays the movement log.
     * @param player
     */
    public void displayMoves(int player)
    {
        String movementLog = "";

        switch (player)
        {
            case 1:
                displayBoard();
                movementLog += "Player's move is: " +
                        "\nPlayer vs. Opponent";
                askHumanInput();
                break;
            case 2:
                calculateNextMove(askHumanInput());
                movementLog += "Opponent vs. Player";
                break;
            default:
                break;
        }

        for(int i = 0; i < moves.size(); i++)
        {
            movementLog += (i + 1) + moves.get(i);
        }

        System.out.println(movementLog);
    }

    public void undo(int row, int col) {
        if(board[row][col] != '-'){
            board[row][col] = '-';
        }
    }

    /**
     * Asks for human input.
     */
    private String askHumanInput()
    {
        do
        {
            System.out.print("\nChoose Opponent's next move: ");
            opponentMove = scanner.nextLine();
        }while(!placeTile(opponentMove.toUpperCase()));

        return opponentMove;
    }

    private String calculateNextMove(String opponentMove)
    {
        return null;
    }
}
