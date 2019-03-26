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
 * Bugs:       none
 */
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Assignment:      p3b Performance Analysis
//Title:           MyProfiler
//Files:           HashTable, MyProfiler
//Course:          CS400 2019 Spring Lec004
//Due date:        10pm Mar 28
//
//Author:          Runheng Lei
//Email:           rlei5@wisc.edu
//Lecturer's Name: Kuemmel 
//Known bugs:      None
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name:    NONE
//Partner Email:   NONE
//Partner Lecturer's Name: NONE
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//___ Write-up states that pair programming is allowed for this assignment.
//___ We have both read and understand the course Pair Programming Policy.
//___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully 
//acknowledge and credit those sources of help here.  Instructors and TAs do 
//not need to be credited here, but tutors, friends, relatives, room mates, 
//strangers, and others do.  If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons:         NONE
//Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

// Used as the data structure to test our hash table against
import java.util.TreeMap;

/**
 * This class is used as the data structure to test hash table against java's
 * treeMap
 * 
 * @author Runheng Lei
 *
 * @param <K> data type of Key
 * @param <V> data type of Value
 */
public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;

	/**
	 * This is the Profile constructor, used to instantiate HashTable and Java's
	 * TreeMap
	 */
	public MyProfiler() {
		hashtable = new HashTable<K, V>();
		treemap = new TreeMap<K, V>();
	}

	/**
	 * This method insert K,V into both data structures
	 * 
	 * @param key, key of data pair
	 * @param value, value of data pair
	 */
	public void insert(K key, V value) {
		try {
			// insert into both data structures
			hashtable.insert(key, value);
			treemap.put(key, value);
		} catch (IllegalNullKeyException e) {
			e.printStackTrace();
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method gets value V for key K from data structures
	 * 
	 * @param key, key of data pair
	 */
	public void retrieve(K key) {
		try {
			hashtable.get(key);
			treemap.get(key);
		} catch (IllegalNullKeyException e) {
			e.printStackTrace();
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method removes key and value pair from data structure
	 * 
	 * @param key, the key of data pair
	 */
	private void remove(K key) {
		try {
			hashtable.remove(key);
			treemap.remove(key);
		} catch (IllegalNullKeyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is the main method of the profiler, which create a profile object and
	 * execute lots of insertions, gets, and removes for later comparison.
	 * 
	 * @param args, num of elements to be executed, input from user
	 */
	public static void main(String[] args) {
		try {

			int numElements = Integer.parseInt(args[0]);

			// Create a profile object.
			MyProfiler<Integer, Integer> mp = new MyProfiler<Integer, Integer>();

			// execute the insert method of profile as many times as numElements
			for (int i = 0; i < numElements; i++) {
				mp.insert(i, i);
			}

			// execute the retrieve method of profile as many times as numElements
			for (int i = 0; i < numElements; i++) {
				mp.retrieve(i);
			}

			// execute the remove method of profile as many times as numElements
			for (int i = 0; i < numElements; i++) {
				mp.remove(i);
			}

			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
