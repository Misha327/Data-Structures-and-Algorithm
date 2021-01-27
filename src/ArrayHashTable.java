import java.util.HashSet;

public class ArrayHashTable extends HashTable {

    private Object[][] table;
    private int chainSize = 5;
    private int[] counts;

    public ArrayHashTable() {
        table = new Object[capacity][];
        counts = new int[capacity];
    }

    @Override
    public boolean add(Object obj) {
        int hashValue = obj.hashCode() % capacity;

        if (table[hashValue] == null) {
            table[hashValue] = new Object[chainSize];
            table[hashValue][0] = obj;
            counts[hashValue]++;
            size++;
            return true;
        } else {
            //Check if in the array
            if (contains(obj)) {
                return false;
            } else {

                table[hashValue][counts[hashValue]] = obj;
                counts[hashValue]++;
                size++;
                //Copy to a new bigger array if it is now full.
                if (table[hashValue].length == counts[hashValue]) {
                    if (chainSize == counts[hashValue]) {
                        chainSize *= 2;
                    }
                    Object[] tempChain = new Object[chainSize];
                    for (int i = 0; i < table[hashValue].length; i++) {
                        tempChain[i] = table[hashValue][i];
                    }
                    table[hashValue] = tempChain;
                }
                return true;
            }
        }
    }

    @Override
    public boolean contains(Object obj) {
        int hashValue = obj.hashCode() % capacity;
        if (table[hashValue] == null) return false;
        for (int i = 0; i < table[hashValue].length; i++) {
            if (table[hashValue][i] == obj) return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        int hashValue = obj.hashCode() % capacity;

        if (!contains(obj)) {
            return false;
        }

        Object[] tempChain = new Object[chainSize];
        for (int i = 0; i < table[hashValue].length; i++) {

            if (table[hashValue][i] == obj) {
                table[hashValue][i] = null;
                counts[hashValue]--;
                // Copy to a new array with no gaps
                System.arraycopy(table[hashValue], i + 1, tempChain, i, counts[hashValue]);
                table[hashValue] = tempChain;
                size--;

                return true;
            }
            tempChain[i] = table[hashValue][i];
        }
        return false;
    }

    public static long timingTest(int inputNum) {
        long estimatedTime = 0;

        int[] data = new int[inputNum];

        for (int i = 0; i < 10000; i++) {                  //Run the algorithm 10,000 times
            ArrayHashTable arrayHashTable = new ArrayHashTable();
//            HashSet hashSet = new HashSet();

            for (int j = 0; j < inputNum; j++) {
                data[j] = (int) (Math.random() * 100 + 1); //Randomize data with each iteration
            }

            long startTime = System.nanoTime();
            for (int x : data) {
//                hashSet.add(x);
                arrayHashTable.add(x);
            }
            for (int x : data) {
//                hashSet.remove(x);
                arrayHashTable.remove(x);
            }
            estimatedTime += System.nanoTime() - startTime;
        }
        return estimatedTime / 10000;
    }

    public static void main(String[] args) {
        System.out.println(timingTest(30000));

    }
}