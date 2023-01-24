package gg.acai.aurora.cycle;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author Clouke
 * @since 24.01.2023 15:02
 * © Acava - All Rights Reserved
 * Idea behind this; Works as if a node has a queue of data to process on multiple process provider ids
 * @param <E> - The type of data to be processed in multiple queues of types
 */
public class CycleMultiQueue<E> implements Queue<E>, AutoCloseable {

    private final Map<Integer, Set<E>> remaining;
    private final Map<Integer, Set<E>> processed;
    private final int procCountBeforeComplete;
    private int size;

    public CycleMultiQueue(int procCountBeforeComplete) {
        this.remaining = new HashMap<>();
        this.processed = new HashMap<>();
        this.procCountBeforeComplete = procCountBeforeComplete;
    }

    @Override
    public int size() {
        return remaining.size();
    }

    @Override
    public boolean isEmpty() {
        return remaining.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        for (Set<E> set : remaining.values()) {
            if (set.contains(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return !remaining.isEmpty();
            }

            @Override
            public E next() {
                Set<E> set = remaining.values().iterator().next();
                int idx = 0;
                for (Set<E> s : remaining.values()) {
                    if (s.size() < set.size()) {
                        set = s;
                        break;
                    }
                    idx++;
                }
                E e = set.iterator().next();
                set.remove(e);
                if (set.isEmpty()) remaining.remove(idx);
                processed.computeIfAbsent(idx, k -> new HashSet<>()).add(e);
                return e;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return remaining.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return remaining.values().toArray(a);
    }

    @Override @Deprecated
    public boolean add(E e) {
        throw new UnsupportedOperationException("Use add(int, E) instead");
    }

    public boolean add(int id, E e) {
        return remaining.computeIfAbsent(id, k -> new HashSet<>()).add(e);
    }

    @Override
    public boolean remove(Object o) {
        for (Set<E> set : remaining.values()) {
            if (set.remove(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(int id, Object o) {
        boolean rem = false;
        Set<E> set = remaining.get(id);
        if (set != null) {
            for (E e : set)
                if (e.equals(o)) rem |= set.remove(e);
        }
        return rem;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Set<E> set : remaining.values()) {
            if (set.addAll(c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean rem = false;
        for (Object o : c) {
            rem |= remove(o);
        }
        return rem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean rem = false;
        for (Set<E> set : remaining.values()) {
            rem |= set.retainAll(c);
        }
        return rem;
    }

    @Override
    public void clear() {
        remaining.clear();
        processed.clear();
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        return remaining.values().iterator().next().iterator().next();
    }

    @Override
    public E poll() {
        E e = peek();
        if (e != null) remove(e);
        return e;
    }

    @Override
    public E element() {
        E e = peek();
        if (e == null) throw new IllegalStateException("No elements in queue");
        return e;
    }

    @Override
    public E peek() {
        for (Set<E> set : remaining.values()) {
            if (!set.isEmpty()) {
                return set.iterator().next();
            }
        }
        return null;
    }

    @Override
    public void close() {
        synchronized (this) {
            remaining.clear();
            processed.clear();
        }
    }
}
