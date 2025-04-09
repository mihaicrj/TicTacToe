package org.example;

public class Minimax {
    public static char turn;
    public static char[][] board;
    public Minimax()
    {
        board=new char[4][4];
        for(int i=1;i<=3;i++)
        {
            for(int j=1;j<=3;j++)
            {
                board[i][j]=' ';
            }

        }
    }
    public String getTabla()
    {
        StringBuilder tabla= new StringBuilder();
        tabla.append("  1 2 3\n");
        for(int i=1;i<=3;i++)
        {
            tabla.append(i).append(" ");
            for(int j=1;j<=3;j++)
            {
                tabla.append(board[i][j]).append(" ");
            }
            tabla.append("\n");
        }
        return tabla.toString();
    }
    public String getFreePositions()
    {
        String tabla="";
        for(int i=1;i<=3;i++)
        {
            for(int j=1;j<=3;j++)
            {
                if(board[i][j]==' ')
                {
                    tabla+=i+","+j+" ";
                }
            }
        }
        return tabla;

    }
    public void setTurn(char t)
    {
        turn=t;
    }
    public char getTurn()
    {
        return turn;
    }
    public void setBoard(char[][] b)
    {
        board=b;
    }
    public void setBoardPosition(int i,int j,char m)
    {
        if(board[i][j]==' ')
            board[i][j]=m;
    }

    public Pair<Integer,Integer> bestMove()
    {
        int bestScore=-1000;
        if(turn=='0')bestScore=1000;
        int col=0,lin=0;
        for(int i=1;i<=3;i++)
        {
            for(int j=1;j<=3;j++)
            {
                int score;
                if(board[i][j]==' ')
                {
                    board[i][j]=turn;
                    if(turn=='x')
                    {
                        score=minimax(0,false);

                    }
                    else{
                        score=minimax(0,true);
                    }
                    board[i][j]=' ';
                    if(score>bestScore && turn=='x')
                    {
                        bestScore=score;
                        lin=i;
                        col=j;
                    }
                    else if(score<bestScore && turn=='0')
                    {
                        bestScore=score;
                        lin=i;
                        col=j;
                    }
                }
            }
        }
        board[lin][col]=turn;
        return new Pair<Integer,Integer>(lin,col);
    }
    public static int minimax(int depth, boolean isMaximizing)
    {
        char result=checkWinner();
        if(result!='n')
        {
            if(result=='t')return 0;
            else if (result=='x')return 10;
            return -10;
        }
        if(isMaximizing)
        {
            int bestScore=-1000;
            for(int i=1;i<=3;i++) {
                for (int j = 1; j <= 3; j++) {
                    if (board[i][j] == ' ') {

                        board[i][j]='x';
                        int score=minimax(depth+1,false);
                        board[i][j]=' ';
                        if(score>bestScore)bestScore=score;
                    }

                }
            }
            return bestScore;
        }
        else
        {
            int bestScore=1000;
            for(int i=1;i<=3;i++) {
                for (int j = 1; j <= 3; j++) {
                    if (board[i][j] == ' ') {

                        board[i][j]='0';
                        int score=minimax(depth+1,true);
                        board[i][j]=' ';
                        if(score<bestScore)bestScore=score;
                    }

                }
            }
            return bestScore;
        }

    }
    public static char checkWinner()
    {
        for(int i=1;i<=3;i++)
        {
            if(board[i][1]==board[i][2] && board[i][1]==board[i][3] && board[i][1]!=' ')
            {
                return board[i][1];
            }
        }
        for(int j=1;j<=3;j++)
        {
            if(board[1][j]==board[2][j] && board[1][j]==board[3][j] && board[1][j]!=' ')
            {
                return board[1][j];
            }
        }
        if(board[1][1]==board[2][2] && board[1][1]==board[3][3] && board[1][1]!=' ')
        {
            return board[1][1];
        }
        if(board[1][3]==board[2][2] && board[1][3]==board[3][1] && board[1][3]!=' ')
        {
            return board[1][3];
        }
        int possibleMoves=0;
        for(int i=1;i<=3;i++) {
            for (int j = 1; j <= 3; j++) {
                if (board[i][j] == ' ') {
                    possibleMoves++;
                }
            }
        }
        if(possibleMoves==0)return 't';
        return 'n';
    }
    public char getWinner()
    {
        return checkWinner();
    }
}
