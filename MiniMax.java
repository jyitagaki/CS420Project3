public class MiniMax
{
    private static MiniMax instance = null;
    private Board b;
    private int maxDepth = 5;
    private int locationX = -1; //row
    private int locationY = -1; //column
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


    private int ABPruning(int depth, int turn, int alpha, int beta){
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
                    b.placeTile(i,j,'X');
                    currScore = ABPruning(depth+1, 2, alpha, beta);
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
                    b.placeTile(i, j, 'O');
                    currScore = ABPruning(depth+1,1,alpha,beta);
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
    private int evalBoard(Board b)
    {


        return 0;
    }

    //checks game result win or lose
    public int checkResult(Board b)
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
