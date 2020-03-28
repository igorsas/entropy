import com.sun.javafx.binding.StringFormatter;

import java.util.*;

public class Main {
    private static int N;

    public static void main(String[] args) {
        List<Boolean> set = getSet();
        N = set.size();
        buildTable(set);
    }

    private static void buildTable(List<Boolean> set) {
        int x, nl, ml, nr, mr;

        double p1l, p2l, p1r, p2r, il, ir, ix;
        List<Double> listIx = new ArrayList<>();
        Set<Boolean> colors = new HashSet<>(set);
        boolean isOneColor = colors.size() == 1;

        if(isOneColor){
            System.out.println("Here's only one color: " + (colors.contains(true) ? "black" : "white"));
            return;
        }

        System.out.println("| x| nl| ml|  Il | nr| mr|  Ir |  Ix |");

        for (x = 1; x < set.size(); x++) {
            nl = findNl(set, x);
            ml = findMl(set, x);
            nr = findNr(set, x);
            mr = findMr(set, x);

            p1l = nl / (double) (nl + ml);
            p2l = ml / (double) (nl + ml);
            p1r = nr / (double) (nr + mr);
            p2r = mr / (double) (nr + mr);

            il = -p1l * log2(p1l) - p2l * log2(p2l);
            ir = -p1r * log2(p1r) - p2r * log2(p2r);
            ix = (nl + ml) / (double) N * il + (nr + mr) / (double) N * ir;
            //preventing '-' sign before 0
            if (il == 0) {
                il = 0;
            }
            if (ir == 0) {
                ir = 0;
            }
            if (ix == 0) {
                ix = 0;
            }
            listIx.add(ix);
            System.out.println(StringFormatter.format("|%02d| %02d| %02d|%.3f| %02d| %02d|%.3f|%.3f|", x, nl, ml, il, nr, mr, ir, ix).getValue());
        }


        double minX = Collections.min(listIx);
        int indexMinIx = listIx.indexOf(minX);
        System.out.println("\nMin is " + minX + " on the position: " + (indexMinIx+1));

        System.out.println("\nGoing to the left\n\n");
        buildTable(set.subList(0, indexMinIx+1));
        System.out.println("\nGoing to the right\n\n");
        buildTable(set.subList(indexMinIx+1, set.size()));
    }


    public static double log2(double x) {
        return x != 0 ? (Math.log(x) / Math.log(2)) : 0;
    }

    private static int findMr(List<Boolean> set, int x) {
        return findN(set, x, true, true);
    }

    private static int findNr(List<Boolean> set, int x) {
        return findN(set, x, true, false);
    }

    private static int findMl(List<Boolean> set, int x) {
        return findN(set, x, false, true);
    }

    private static int findNl(List<Boolean> set, int x) {
        return findN(set, x, false, false);
    }

    private static int findN(List<Boolean> set, int x, boolean side, boolean color) {
        int size = 0;
        int i, length;

        if (side) {
            i = x;
            length = set.size();
        } else {
            i = 0;
            length = x;
        }

        for (; i < length; i++) {
            if (set.get(i) == color) {
                size++;
            }
        }
        return size;
    }


    private static List<Boolean> getSet() {
        return new ArrayList<Boolean>() {{
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);
            add(true);
            add(true);
            add(true);
        }};
    }

    private static List<Boolean> getDefaultSet() {
        return new ArrayList<Boolean>() {{
            add(false);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(false);
            add(true);
        }};
    }
}
