package com.dictionary;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Wushudeng on 2018/10/11.
 */
public class HashTable<K extends Comparable<? super K>, V> implements Dictionary<K, V>{
    private int size = 0;
    private int size_contain_removal = 0;
    private int[] primes = generate_primes(100000);
    private int prime_index = 6;
    private int capacity = primes[prime_index];
    private Entry<K,V>[] entries = new Entry[capacity];

    // if lazy removal is true, it denotes that it has been removed.
    // otherwise, it denotes that it has not been removed.
    private boolean[] lazyremoval = new boolean[capacity];

    public HashTable() {
        for (int i=0; i<capacity; i++) {
            entries[i] = null;
            lazyremoval[i] = false;
        }
    }

    private static int [] generate_primes(int max) {
        if (max < 2) return null;
        boolean[] seq = new boolean[max];

        seq[0] = false;
        seq[1] = false;
        int count = max-2;
        for (int i=2; i<max; i++)
            seq[i] = true;

        for (int i=2; i*i<max; i++){
            if (seq[i]) {
                for (int j=2*i; j<max; j+=i) {
                    seq[j] = false;
                }
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0, j=0; i<max; i++)
            if (seq[i])
                list.add(i);
        int[] primes = new int[list.size()];
        for (int i=0; i<list.size(); primes[i] = list.get(i), i++);
        return primes;
    }

    private static class Entry<K extends Comparable<? super K>, V> implements Comparable{
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Object o) {
            return this.key.compareTo((K)o);
        }
    }

    private int hash(K key) {
        return key.hashCode()%capacity;
    }

    private int probe4hit(K key) {
        int position = hash(key);
        for (int i=0; ;i++) {
            //key does not exists in ht\ash table
            if (entries[position] == null)
                return -1;
            if (entries[position].key.compareTo(key) != 0)
                position+=(2*i+1);
            else return position;
        }
    }

    private int probe4free(K key) {
        // if the capacity is prime, and use squared detecting method,
        // free position can always be found.

        int position = hash(key);
        for (int i=0; ! ((entries[position] == null) || (entries[position] !=null && lazyremoval[position])); i++) {
            position = (position+(2*i+1))%capacity;
        }
        return position;
    }


    private void rehash() {
        while (prime_index < primes.length && primes[++prime_index] < size_contain_removal*2);
        capacity = primes[prime_index];
        size = 0;
        size_contain_removal = 0;

        Entry<K, V> [] old_entries = entries;
        boolean[] old_lazyremoval = lazyremoval;
        lazyremoval = new boolean[capacity];
        entries = new Entry[capacity];

        int count = 0;
        for (int i=0; i<old_entries.length; i++)
            if (old_entries[i] != null && !lazyremoval[i])
                put(old_entries[i].key, old_entries[i].value);

        for (int i=0; i<capacity; lazyremoval[i]=false, i++);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        int position = probe4hit(key);
        return position == -1 ? null : entries[position].value;
    }

    @Override
    public boolean put(K key, V value) {
        if (size_contain_removal+1 > capacity/2)
            rehash();
        // squared detection
        int position = hash(key);
        // if entry of this position is not null, or the lazy removal of this position is false,
        // means that this position has been occupied.

        for (int i = 0; entries[position] != null && !lazyremoval[position]; i++) {
            if (entries[position].key.compareTo(key) == 0) {
                entries[position].value = value;
                return true;
            }
            position = (position+(2*i+1))%capacity;
        }
        if (entries[position] == null)
            size_contain_removal++;
        size++;
        entries[position] = new Entry(key, value);
        lazyremoval[position] = false;
        return true;
    }

    @Override
    public boolean is_empty() {
        return size == 0;
    }

    @Override
    public boolean contains(K key) {
        return probe4hit(key) != -1;
    }

    @Override
    public boolean remove(K key) {
        // it should decrese the size of hash table.
        int position = probe4hit(key);
        if (position==-1) return false;
        lazyremoval[position] = true;
        size--;
        return true;
    }

    public static void main(String[] args) {
        HashTable<Integer, Integer> hashTable = new HashTable<>();
        int[] nums = {6952,4376,921,4560,8998,1180,1843,5881,330,2030,112,6778,5066,3658,2678,1107,3999,3735,773,6046,5172,1809,4464,3641,8969,978,2649,6549,6406,7500,7962,8186,8173,8261,3167,8405,2504,7296,6354,5999,8578,212,2078,7323,8610,3259,3957,1515,9441,9753,9549,5824,2061,6087,7669,9292,1126,7723,4933,1989,8005,3318,9827,4745,2461,8297,466,6217,4169,4725,6085,6563,5205,7982,7746,9504,9145,5499,1153,2406,7104,4988,9555,2251,1111,9560,1216,6871,496,610,7464,3572,5750,3992,6443,9557,9755,6312,8821,4256};
        for (int num:nums) {
            hashTable.put(num, num);
        }
        System.out.println(hashTable.get(4376));

    }
}
