
public class Triple implements Comparable<Triple> {
    private int a, b, c;

    public Triple(int a, int b, int c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getFirst() { return a; }

    public int getSecond() { return b; }

    public int getThird() { return c; }

    public int compareTo(Triple t)
    {
        if (this.a == t.getFirst()) {
            if (this.b == t.getSecond())
                return this.c - t.getThird();
            else
                return this.b - t.getSecond();
        }
        return t.getFirst() - this.a;
    }
}
