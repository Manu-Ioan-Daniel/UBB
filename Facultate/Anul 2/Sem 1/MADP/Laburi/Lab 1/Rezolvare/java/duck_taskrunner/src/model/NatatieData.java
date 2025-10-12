package model;

public class NatatieData {
    private final int n;
    private final int m;
    private final Rata[] rate;
    private final int[] distante;
    public NatatieData(int n, int m, Rata[] rate, int[] distante) {
        this.n = n;
        this.m = m;
        this.rate = rate;
        this.distante = distante;
    }
    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Rata[] getRate() {
        return rate;
    }

    public int[] getDistante() {
        return distante;
    }
}
