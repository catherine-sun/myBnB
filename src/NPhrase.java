
public class NPhrase implements Comparable<NPhrase> {
    private String nPhrase;
    private int count;

    public NPhrase(String nPhrase, int count) {
        this.nPhrase = nPhrase;
        this.count = count;
    }

    @Override
    public int compareTo(NPhrase o) {
        if (o.getNPhrase().equals(nPhrase)) {
            return 0;
        }
        return o.getCount() - count;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NPhrase) {
            return ((NPhrase) o).getNPhrase().equals(nPhrase);
        }
        return false;
    }

    public String getNPhrase() { return nPhrase; }
    public int getCount() { return count; }
    public void incrementCount() { count++; }

}
