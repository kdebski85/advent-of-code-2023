package pl.krzysztofdebski.utils;

import java.util.Comparator;
import java.util.List;

public class ListComparator<T extends Comparable<T>> implements Comparator<List<T>> {

    @SuppressWarnings("rawtypes")
    private static final ListComparator INSTANCE = new ListComparator<>();
    public static final ListComparator<Integer> INTEGER_INSTANCE = get();
    @SuppressWarnings("unused")
    public static final ListComparator<Long> LONG_INSTANCE = get();

    @SuppressWarnings("unchecked")
    static <T extends Comparable<T>> ListComparator<T> get() {
        return INSTANCE;
    }

    @Override
    public int compare(List<T> o1, List<T> o2) {
        int l1Size = o1.size();
        int l2Size = o2.size();

        for (int i = 0; i < l1Size; i++) {
            if (l2Size <= i) {
                return 1;
            }

            int diff = o1.get(i).compareTo(o2.get(i));
            if (diff != 0) {
                return diff;
            }
        }

        if (l2Size > l1Size) {
            return -1;
        }

        return 0;
    }
}
