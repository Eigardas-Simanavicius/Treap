package tree;

import interfaces.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreapTest {

    @Test
    void treeMin() throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{5, 84, 79, 99, 6, 26, 57, 56, 24, 39};
        Integer[] prios = new Integer[]{56, 18, 2, 67, 69, 24, 12, 41, 19, 77, 1};


        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(5, treap.treeMin(treap.tree.root).getElement().getKey() );
    }
    @Test
    void treeMax() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{61, 60, 79, 37, 62, 43, 21, 41, 92, 5};
        Integer[] prios = new Integer[]{56, 18, 2, 67, 69, 24, 12, 41, 19, 77, 1};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(92, treap.treeMax(treap.tree.root).getElement().getKey() );
    }
    @Test
    void firstEntry() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{61, 60, 79, 37, 62, 43, 21, 41, 92, 5};
        Integer[] prios = new Integer[]{38, 91, 83, 65, 15, 40, 1, 11, 18, 67};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(5, treap.firstEntry().getKey() );
    }

    @Test
    void lastEntry() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{87,20, 2, 41, 19, 78, 42, 63, 82, 90};
        Integer[] prios = new Integer[]{38, 91, 83, 65, 15, 40, 1, 11, 18, 67};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(90, treap.lastEntry().getKey() );
    }

    @Test
    void ceilingEntry() throws IOException{
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{87,20, 2, 41, 19, 78, 42, 63, 82, 90};
        Integer[] prios = new Integer[]{32, 30, 56, 57, 45, 55, 2 ,97, 36, 34};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(41, treap.ceilingEntry(41).getKey() );
        Assertions.assertEquals(2, treap.ceilingEntry(2).getKey() );
    }

    @Test
    void floorEntry() throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{22, 16, 49, 79, 30, 44, 93, 58, 68, 66};
        Integer[] prios = new Integer[]{32, 30, 56, 57, 45, 55, 2 ,97, 36, 34};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(30, treap.floorEntry(41).getKey() );
        Assertions.assertEquals(93, treap.floorEntry(93).getKey() );
    }

    @Test
    void lowerEntry() throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{22, 16, 49, 79, 41, 40, 93, 58, 68, 66};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(40, treap.lowerEntry(41).getKey() );
        Assertions.assertEquals(93, treap.lowerEntry(97).getKey() );
    }

    @Test
    void higherEntry() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{22, 16, 49, 79, 41, 40, 93, 58, 68, 66};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(22, treap.higherEntry(16).getKey() );
        Assertions.assertEquals(41, treap.higherEntry(40).getKey() );
    }

    @Test
    void size() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{77,83, 13, 88, 39, 42, 6 ,54 ,46, 80};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(10, treap.size());
        treap.remove(42);
        Assertions.assertEquals(9, treap.size() );
    }

    @Test
    void get() throws IOException{
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{89 ,38, 96, 78, 50, 27, 80, 15, 87, 17};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(89,treap.get(89) );
        Assertions.assertEquals(50,treap.get(50) );

    }

    @Test
    void put() throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{89 ,38, 96, 78, 50, 27, 80, 15, 87, 17};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(10,treap.size());
        treap.put(12,12);
        Assertions.assertEquals(11,treap.size());
    }

    @Test
    void remove() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{89 ,38, 96, 78, 50, 27, 80, 15, 87, 17};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(10,treap.size());
        treap.remove(27);
        Assertions.assertEquals(9,treap.size());

    }

    @Test
    void entrySet() throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{89 ,38, 96, 78, 50, 27, 80, 15, 87, 17};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }

        Assertions.assertEquals(10,treap.size());
        treap.remove(50);
        Assertions.assertEquals(9,treap.size());

    }

    @Test
    void subMap() throws IOException {
        Treap<Integer, Integer>  treap = new Treap<>();
        Integer[] keys = new Integer[]{89 ,38, 96, 78, 50, 27, 80, 15, 87, 17};
        Integer[] prios = new Integer[]{38, 49, 76, 30, 53, 63, 59, 77, 24, 35};

        for (int i = 0; i < keys.length; i++) {
            treap.put(keys[i], keys[i]);
        }
      Iterable<Entry<Integer,Integer>> entries = treap.subMap(50,90);
        assertEquals("[<50, 50>, <78, 78>, <80, 80>, <87, 87>, <89, 89>]",entries.toString());


    }




}

