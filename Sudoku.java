public class Sudoku {
    private int[][] sudoku;

    public Sudoku(String s)
    {
        sudoku = new int[9][9];
        
        for (int k = 0; k < s.length(); k++)
            sudoku[k / 9][k % 9] = s.charAt(k) - '0';
    }

    public void solve()
    {
        backtrack(0, 0);
    }

    public void print()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(sudoku[i][j] + " ");
            System.out.println();
        }
    }

    private boolean backtrack(int x, int y)
    {
        if (x == 8 && y == 9) return true;
        if (y == 9) return backtrack(x + 1, 0);
        if (sudoku[x][y] != 0) return backtrack(x, y + 1);

        boolean[] digits = new boolean[10];
        for (int i = 1; i < 10; i++) digits[i] = true;
        
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (sudoku[i][j] != 0 && (x == i || y == j || (x / 3 == i / 3 && y / 3 == j / 3) ) )
                    digits[sudoku[i][j]] = false;
        
        for (int i = 1; i <= 9; i++)
            if (digits[i]) {
                sudoku[x][y] = i;
                if (backtrack(x, y + 1))
                    return true;
                sudoku[x][y] = 0;
            }
        return false;
    }

    public static void main(String args[])
    {
        Sudoku s = new Sudoku("001004703078100602506000090600500020852000947090002006080000209704005360209800400");
        s.solve();
        s.print();
    }
}
