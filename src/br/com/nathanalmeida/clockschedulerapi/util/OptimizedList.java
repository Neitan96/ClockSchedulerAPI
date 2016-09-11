package br.com.nathanalmeida.clockschedulerapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Neitan96 on 06/09/2016.
 */
public class OptimizedList<E> extends ArrayList<E>{

    protected final Comparator<E> comparator;

    public OptimizedList(Comparator<E> comparator){
        if(comparator == null)
            throw new IllegalArgumentException("Comparator NULL");
        this.comparator = comparator;
    }


    private boolean updateItem(E item, boolean removeItem){
        if(removeItem) remove(item);

        for(int i = 0; i < size(); i++){
            E itemNow = get(i);
            if(comparator.compare(item, itemNow) < 0){
                super.add(i, item);
                return true;
            }
        }

        return super.add(item);
    }

    public boolean updateItem(E item){
        return updateItem(item, true);
    }

    @Override
    public E set(int index, E element){
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e){
        return updateItem(e, false);
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        AtomicBoolean result = new AtomicBoolean(false);
        c.forEach(i -> result.set(result.get() && add(i)));
        return result.get();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c){
        throw new UnsupportedOperationException();
    }
}
