package com.example.indstudy.tic_tac_ception;

/**
 * Created by 150072 on 5/4/2015.
 */
public class Board {
    private int[][] matrix;

    public Board()
    {
        matrix = new int[3][3];
    }

    public Board(int r)
    {
        matrix = new int[r][r];
    }

    public boolean play(int point, int isX)
    {
        matrix[point / 3][point - (3 * (point / 3))] = isX;
        return checkWin(point, isX);
    }

    public int getButtonValue(int point)
    {
       return matrix[point / 3][point - (3 * (point / 3))];
    }

    //1 = X 2 = O 0 = Not won
    public boolean checkWin(int pos, int playa)
    {
        int row, col;
        row = pos/3;
        col = pos-(3*(pos/3));
        boolean tictactoe = false;
        for(int i = 0; i < 4 && !tictactoe; i++)
        {
            switch (i) {
                case 0:
                    tictactoe = checkHoriz(row, playa);
                    break;
                case 1:
                    tictactoe = checkVert(col, playa);
                    break;
                case 2:
                    if(matrix.length - row -1 == col)
                    {
                        tictactoe = checkFoSlash(playa);
                    }
                    break;
                case 3:
                    if(row == col)
                    {
                        tictactoe = checkBackSlash(playa);
                    }
                    break;
            }


        }
        return tictactoe;

    }


    public boolean checkFinished() {
        for (int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix.length; c++) {
                if (matrix[r][c] == 0)
                    return false;
            }
        }
        return true;
    }

    public boolean checkHoriz(int r, int playa)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            if(matrix[r][i] != playa)
                return false;
        }
        return true;
    }

    public boolean checkVert(int c, int playa)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            if(matrix[i][c] != playa)
                return false;
        }
        return true;
    }

    public boolean checkFoSlash(int playa)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            if(matrix[matrix.length - 1 - i][i] != playa)
                return false;
        }
        return true;
    }

    public boolean checkBackSlash(int playa)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            if(matrix[i][i] != playa)
                return false;
        }
        return true;
    }

    public void clear()
    {
        for(int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix.length; c++) {
                matrix[r][c] = 0;
            }
        }
    }
}

