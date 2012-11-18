
public class Test {
    public static void main(String[] args)
    {
        int n = 10; int m = 6;
        int[][] a = new int[n][m];
        int[][] sum = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                a[i][j] = 1;

        sum[0][0] = a[0][0];
        for (int d = 1; d <= m + n - 2; d++)
            for (int i = 0; i <= d; i++)
                if (i < n && d - i < m) {
                    sum[i][d - i] += a[i][d - i];
                    if (i > 0)
                        sum[i][d - i] += sum[i - 1][d - i];
                    if (d - i > 0)
                        sum[i][d - i] += sum[i][d - i - 1];
                    if (i > 0 && d - i > 0)
                        sum[i][d - i] -= sum[i - 1][d - i - 1];
                }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(sum[i][j] + " ");
            System.out.println();
        }
    }
}
