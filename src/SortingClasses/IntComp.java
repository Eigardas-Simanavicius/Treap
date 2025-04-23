package SortingClasses;

import java.util.Comparator;

public  class IntComp implements Comparator<Integer> {
    @Override
    public int compare(Integer a,Integer b) {
        return (Integer.compare(a, b));
    }


}