import java.util.ArrayList;
import java.util.List;

public class MiniMax
{
    private static MiniMax instance = null;
    private static Board b;
    private static int maxDepth = 5;
    private static int locationX = -1; //row
    private  static int locationY = -1; //column
    private static int maximumTime; // Maximum time in seconds.


    private MiniMax()
    {
    }

    public static MiniMax getInstance()
    {
        if(instance == null)
            instance = new MiniMax();
        return instance;
    }

    public String search(Board board, int maximumTime)
    {
        b = board;
        this.maximumTime = maximumTime;

        // Prune tree.
        return null;
    }


    private static int[] ABPruning(int depth, int turn, int alpha, int beta){
        List<int[]> nextMove = checkMoves();
        int[] rC = new int[2];
        int score = 0;
        int bRow = -1;
        int bCol = -1;
        if(maxDepth==0 || nextMove.size()==0){
            score = evalBoard(b);
            return new int[]{score, bRow,bCol};
        }
        for(int i = 0; i < nextMove.size(); i++){
            rC = nextMove.get(i);
            if(turn == 1) {
                b.placeTile(rC[0], rC[1], 'X');
                //score = ABPruning(depth+1, 2, alpha, beta)[0];
                if(score > alpha){
                    alpha = score;
                    bRow = rC[0];
                    bCol = rC[1];
                }
            } else if(turn == 2){
                b.placeTile(rC[0], rC[1], 'O');
                //score = ABPruning(depth+1, 2, alpha, beta)[0];
                if(score < beta){
                    beta = score;
                    bRow = rC[0];
                    bCol = rC[1];
                }
            }
            b.undo(rC[0],rC[1]);
            if(alpha >= beta){
                break;
            }
        }
        return new int[]{bRow,bCol};
    }

    private static List<int[]> checkMoves() {
        List<int[]> nextMove = new ArrayList<int[]>();
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                if(b.isLegal(i,j)){
                    nextMove.add(new int[] {i,j});
                }
            }
        }
        return nextMove;
    }

    public static int getAITurnX(){
        int[] locationX = ABPruning(0,1,Integer.MIN_VALUE,Integer.MAX_VALUE);
        return locationX[0];
    }
    public static int getAITurnY(){
        int[] locationY = ABPruning(0,1,Integer.MIN_VALUE,Integer.MAX_VALUE);
        return locationY[1];
    }
    //checks favorability for AI
     private static int evalBoard(Board b) {
        int aiScore = 1;
        int score = 0;
        int blank = 0;
        int moves = 0;
        for(int i = 8; i >= 0; i--){
            for(int j = 0; j <= 8; j++){
                if(b.board[i][j] == '-' || b.board[i][j] == 'O'){
                    continue;
                }
                if(j<=3){
                    for(int k = 1; k < 4; k++){
                        if(b.board[i][j+k] == 'X'){
                            aiScore++;
                        } else if(b.board[i][j+k] == 'O'){
                            aiScore = 0;
                            blank = 0;
                            break;
                        } else{
                            blank++;
                        }
                    }
                    moves = 0;
                    if (blank > 0) {
                        for(int r = 1; r < 4; r++){
                            for(int c = 1; c < 4; c++){
                                int row = i+r;
                                int column = j+c;
                                for(int m = i; m<=5; m++){
                                    if(b.board[row][column]=='-'){
                                        moves++;
                                    } else{
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(moves != '-'){
                        score += calcScore(aiScore,moves);
                        aiScore = 1;
                        blank = 0;
                    }
                }
                if(i>=3){
                    for(int k = 1; k < 4; k++){
                        if(b.board[i-k][j]=='X'){
                            aiScore++;
                        } else if(b.board[i-k][j]=='O'){
                            aiScore = 0;
                            break;
                        }
                    }
                    moves = 0;
                    if(aiScore>0) {
                        int row = i;
                        int column = j;
                        for (int m = i; m <= 5; m++) {
                            if (b.board[row][column] == '-') {
                                moves++;
                            } else {
                                break;
                            }
                        }
                    }
                    if(moves != '-'){
                        score += calcScore(aiScore,moves);
                    }
                    aiScore = 1;
                    blank = 0;
                }
                if(j>=3){
                    for(int k = 1; k<4; k++){
                        if(b.board[i][j-k] == 'X'){
                            aiScore++;
                        } else if(b.board[i-k][j]=='O'){
                            aiScore = 0;
                            break;
                        } else{
                            blank++;
                        }
                    }
                    moves = 0;
                    if(blank>0){
                        for(int r = 1; r < 4; r++){
                            for(int c = 1; c < 4; c++){
                                int row = i+r;
                                int column = j+c;
                                for(int m = i; m<=5; m++){
                                    if(b.board[row][column]=='-'){
                                        moves++;
                                    } else{
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(moves!='-'){
                        score += calcScore(aiScore,moves);
                    }
                }
            }
        }
        return score;
    }

    private static int calcScore(int aiScore, int moves) {
        int moveScore = 4 - moves;
        if(aiScore==0){
            return 0;
        } else if(aiScore==1){
            return moveScore;
        } else if(aiScore==2){
            return moveScore+100;
        } else if(aiScore==3){
            return moveScore+1000;
        } else{
            return 30000;
        }
    }
    //checks game result win or lose
    public static int checkResult(Board b)
    {
        int xCounter;
        int oCounter;

        // Horizontal check
        for(int i = 1; i <= 8; i++)
        {
            xCounter = 0;
            oCounter = 0;

            for(int j = 1; j <= 8; j++)
            {

                if(b.board[i][j] == 'X')
                {
                    xCounter++;
                    oCounter = 0;
                }
                else if(b.board[i][j] == 'O')
                {
                    oCounter++;
                    xCounter = 0;
                }
                else
                {
                    xCounter = 0;
                    oCounter = 0;
                }

                if(xCounter == 4)
                    return 1;
                else if(oCounter == 4)
                    return -1;
            }
        }

        // Vertical check
        for(int i = 0; i <= 8; i++)
        {
            xCounter = 0;
            oCounter = 0;

            for(int j = 0; j <= 8; j++)
            {
                if(b.board[j][i] == 'X')
                {
                    xCounter++;
                    oCounter = 0;
                }
                else if(b.board[j][i] == 'O')
                {
                    oCounter++;
                    xCounter = 0;
                }
                else
                {
                    xCounter = 0;
                    oCounter = 0;
                }

                if(xCounter == 4)
                    return 1;
                else if(oCounter == 4)
                    return -1;
            }
        }

        return 0;
    }
}
