package project.cs3360.socketserver.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class SortedList<E> extends ArrayList<E> {
    private final Comparator<? super E> comparator;

    public SortedList(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean add(E e) {
        int insertionPoint = Collections.binarySearch(this, e, comparator);
        if (insertionPoint < 0) {
            insertionPoint = -(insertionPoint + 1);
        }
        super.add(insertionPoint, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        this.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            modified |= add(e);
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return addAll(c);
    }
}
