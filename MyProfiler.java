/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    Runheng Lei, lec004
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   Mar 28 by 10pm
 * Version:    1.0
 * 
 * Credits:    none
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    public MyProfiler() {
        // TODO: complete the Profile constructor
        // Instantiate your HashTable and Java's TreeMap
    	hashtable = new HashTable<K,V>();
    	treemap = new TreeMap<K,V>();
    }
    
    public void insert(K key, V value) {
        // TODO: complete insert method
        // Insert K, V into both data structures
        treemap.put(key, value);
        try {
			hashtable.insert(key, value);
		} catch (IllegalNullKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void retrieve(K key) {
        // TODO: complete the retrieve method
        // get value V for key K from data structures
    	treemap.get(key);
    	try {
			hashtable.get(key);
		} catch (IllegalNullKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void remove(K key) {
    	// remove key and value pair from data structure
    	treemap.remove(key);
    	try {
			hashtable.remove(key);
		} catch (IllegalNullKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);

            
            // TODO: complete the main method. 
            // Create a profile object. 
            // For example, Profile<Integer, Integer> profile = new Profile<Integer, Integer>();
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            // See, ProfileSample.java for example.
            
            MyProfiler<Integer, Integer> mp = new MyProfiler<Integer,Integer>();
            
            for(int i = 0; i < numElements; i++) {
            	mp.insert(i, i);
            }
            
            for(int i = 0; i < numElements; i++) {
            	mp.retrieve(i);
            }
            
            for(int i = 0; i < numElements; i++) {
            	mp.remove(i);
            }
        
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
