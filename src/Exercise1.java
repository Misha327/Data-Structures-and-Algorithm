import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Exercise1 {

    public static String quadTimeComplexity(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int freq = 0;
            int n = array[i];
            for (int j = 0; j < array.length; j++) {
                if (n == array[j]) {
                    freq++;
                }
            }
            if (freq > (array.length / 2)) {
                return Integer.toString(n);
            }
        }
        return "SVD does not exist";
    }


    public static String logTimeComplexity(int[] array) {

        // Dual-Pivot Quicksort worst case performance nlog(n)
        Arrays.sort(array);
        int maxFreq = 1, freq = 1, value = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1])
                freq++;
            else {
                if (freq > maxFreq) {
                    maxFreq = freq;
                    value = array[i - 1];
                }
                freq = 1;
            }
        }
        // Check if the last element is the most frequent
        if (freq > maxFreq) {
            maxFreq = freq;
            value = array[array.length - 1];
        }
        if (maxFreq > array.length / 2) {
            return Integer.toString(value);
        }
        return "SVD does not exist";
    }

    public static String nTimeComplexity(int[] array) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int maxFreq = 0, value = 0;

        for (int i = 0; i < array.length; i++) {
            int key = array[i];
            // Increment the frequency of the key
            if (hashMap.containsKey(key)) {
                // Get the value mapped to the key
                int freq = hashMap.get(key);
                freq++;
                hashMap.put(key, freq);
            }
            // Insert key into the map if not already in
            else {
                hashMap.put(key, 1);
            }
        }
        // Find the key with the highest frequency.
        for (Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (maxFreq < entry.getValue()) {
                maxFreq = entry.getValue();
                value = entry.getKey();
            }
        }
        if (maxFreq > array.length / 2) {
            return Integer.toString(value);
        }
        return "SVD does not exist";
    }

    public static long timingTest(int inputNum) {
        long estimatedTime = 0;
        int[] data = new int[inputNum];

        for (int i = 0; i < 10000; i++) {                  //Run the algorithm 10,000 times

            for (int j = 0; j < inputNum; j++) {
                data[j] = (int) (Math.random() * 100 + 1); //Randomize data with each iteration
            }

            long startTime = System.nanoTime();

            nTimeComplexity(data);
            estimatedTime += System.nanoTime() - startTime;
        }
        return estimatedTime / 10000;
    }

    public static void main(String[] args) {
//        Testing that the algorithm returns the expected value
//        int[] array = {7,7,9,3,2,7,7};
//        System.out.println(quadTimeComplexity(array));

        System.out.println(timingTest(2000));
    }
}