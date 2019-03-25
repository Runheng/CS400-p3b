import java.util.ArrayList;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Assignment:      Hash Table Implementation
//Title:           HashTable
//Files:           HashTable, HashTableTest
//Course:          CS400 2019 Spring Lec004
//Due date:        10pm Mar 
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


// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// TODO: explain your hashing algorithm here 
// I use array of linked nodes (linked buckets) as the resolution of collision. As for hashing
// algorithm, I first use java's hashCode() method to get the hashCode of a key, and then use
// hashCode/table size to get hashIndex, and then store that key into array[hashIndex], then if 
// a Node is already there in the array, then add the new node to linked list, and set the new 
// node as new head, which is the node that stored in array.
//
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object
//       and one of the techniques presented in lecture
//
/**
 * This class is a hash table that stores key value data pairs in array of inked
 * nodes, and can perform insert, delete and get value operations
 * 
 * @author Runheng Lei
 * 
 * @param <K> key
 * @param <V> value
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	// TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation

	private int capacity; // table size
	private double loadFactorThreshold; // threshold load factor
	private int numKeys; // total number of data pairs
	private Object[] hashTable; // hash table

	/**
	 * This is a inner class that represents how key,value pairs are stored in hash
	 * table
	 * 
	 * @author Runheng Lei
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	private class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> next;

		/**
		 * This is the constructor of a node
		 * 
		 * @param key, key of the node
		 * @param value, value stored in the node
		 * @param next, the node that is attached to current node
		 */
		private Node(K key, V value, Node<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * This is a default no-arg constructor
	 */
	public HashTable() {
	}

	/**
	 * This constructor initialize capacity and load factor threshold
	 * 
	 * @param initialCapacity, the table size
	 * @param loadFactorThreshold, the load factor that causes a resize and rehash
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		capacity = initialCapacity;
		this.loadFactorThreshold = loadFactorThreshold;
		hashTable = new Object[capacity];
	}

	/**
	 * Add the key,value pair to the data structure and increase the number of keys.
	 * 
	 * @throws IllegalNullKeyException if key is null
	 * @throws DuplicateKeyException   if key is already in data structure
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if (key == null) { // if null key, throw exception
			throw new IllegalNullKeyException();
		}
		if (contains(key)) { // if duplicated key, throw exception
			throw new DuplicateKeyException();
		}
		numKeys++; // increment total item number
		// check if load factor threshold is reached
		if (getLoadFactor() >= getLoadFactorThreshold()) {
			updateTableSize(); // resize hash table and rehash all elements
		}
		Node<K, V> newNode = new Node<K, V>(key, value, null);
		insertHelper(newNode);
	}

	/**
	 * This is the helper method that helps insert a node in hash table
	 * 
	 * @param newNode, the node to be inserted
	 */
	@SuppressWarnings("unchecked")
	private void insertHelper(Node<K, V> newNode) {
		int index = getIndex(newNode.key);
		Node<K, V> top = (Node<K, V>) hashTable[index]; // retrieve the first node in linked nodes
		newNode.next = top; // set newNode as the first node in linked nodes
		hashTable[index] = newNode;// store new back to array
	}

	/**
	 * If key is found, remove the key,value pair from the data structure, decrease
	 * number of keys, and return true.
	 * 
	 * @throws IllegalNullKeyException, if key is null
	 * @return true, if key is found and removed, return false if key not found
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {
		if (key == null) { // if key is null, throw exception
			throw new IllegalNullKeyException();
		}
		if (!contains(key)) { // if key not exist, return false
			return false;
		}
		return removeHelper(key);
	}

	/**
	 * This is the helper method for remove.
	 * 
	 * @param key, node with this key will be removed from hash table
	 * @return true, if removed successfully
	 */
	@SuppressWarnings("unchecked")
	private boolean removeHelper(K key) {
		int index = getIndex(key); // get hash code
		Node<K, V> current = (Node<K, V>) hashTable[index]; // get first node in linked node
		if (current.key.equals(key)) { // if first node contains that key, remove it
			numKeys--; // decrement total items
			hashTable[index] = current.next; // store next node as top node into hash table array
			return true;
		}
		// if first node is not target node to be removed, traverse through linked nodes
		while (!current.next.key.equals(key)) {
			current = current.next;
		}
		// now current's next node is the one to be deleted
		current.next = current.next.next;
		numKeys--;
		return true;
	}

	/**
	 * Returns the value associated with the specified key, does not remove key or
	 * decrease number of keys
	 * 
	 * @param key, used to search for value
	 * @return the value associated with the specified key
	 * @throws IllegalNullKeyException ,if key is null
	 * @throws KeyNotFoundException,   if key is not found
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if (key == null) { // if null key, throw exception
			throw new IllegalNullKeyException();
		}
		if (!contains(key)) { // if key not exist, throw exception
			throw new KeyNotFoundException();
		}
		return getHelper(key).value;
	}

	/**
	 * This is the helper method for get
	 * 
	 * @param key, the node with this key is target node
	 * @return the value stored in target node
	 */
	@SuppressWarnings("unchecked")
	private Node<K, V> getHelper(K key) {
		int index = getIndex(key); // get hash code
		Node<K, V> current = (Node<K, V>) hashTable[index]; // get first node in linked node
		while (!current.key.equals(key)) {
			current = current.next;
		}
		return current;
	}

	/**
	 * This method traverse through hash table to check if given key is in hash
	 * table
	 * 
	 * @param targetKey, the key to be searched
	 * @return true, if key is in hash table, return false if key not exist
	 */
	@SuppressWarnings("unchecked")
	private boolean contains(K targetKey) {
		int index = getIndex(targetKey);
		// if no nodes is stored in this index, return false
		if (hashTable[index] == null) {
			return false;
		}
		Node<K, V> currentNode = (Node<K, V>) hashTable[index];
		while (currentNode != null) {
			if (currentNode.key.equals(targetKey)) {
				return true; // if target key found, return true
			}
			currentNode = currentNode.next;
		}
		return false;
	}

	/**
	 * This method computes the index of array where the data pair should be stored
	 * 
	 * @param node
	 * @return index
	 */
	private int getIndex(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/**
	 * When the load factor threshold is reached, the capacity must increase to: 2 *
	 * capacity + 1. Once increased, the capacity never decreases
	 */
	@SuppressWarnings("unchecked")
	private void updateTableSize() {
		ArrayList<Node<K, V>> allData = new ArrayList<Node<K, V>>();
		for (int i = 0; i < capacity; i++) { // traverse through the array
			if (hashTable[i] != null) { // if array that this index is not null
				Node<K, V> current = (Node<K, V>) hashTable[i];
				while (current != null) { // go through all nodes and store them in arrayList
					allData.add(current);
					current = current.next;
				}
			}
		}
		// after collecting all data pairs, update table size
		capacity = capacity * 2 + 1;
		hashTable = new Object[capacity];
		// then add all data pairs back to bigger table
		for (int i = 0; i < allData.size(); i++) {
			insertHelper(allData.get(i));
		}
	}

	/**
	 * Returns the number of key,value pairs in the data structure
	 * 
	 * @return number of data pairs
	 */
	@Override
	public int numKeys() {
		return numKeys;
	}

	/**
	 * Returns the load factor threshold that was passed into the constructor when
	 * creating the instance of the HashTable. When the current load factor is
	 * greater than or equal to the specified load factor threshold, the table is
	 * resized and elements are rehashed.
	 * 
	 * @return load factor threshold
	 */
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}

	/**
	 * Returns the current load factor for this hash table.
	 * 
	 * load factor = number of items / current table size
	 * 
	 * @return load factor
	 */
	@Override
	public double getLoadFactor() {
		return (double) numKeys / (double) capacity;
	}

	/**
	 * Return the current Capacity (table size) of the hash table array. The initial
	 * capacity must be a positive integer, 1 or greater and is specified in the
	 * constructor. REQUIRED: When the load factor threshold is reached, the
	 * capacity must increase to: 2 * capacity + 1. Once increased, the capacity
	 * never decreases
	 * 
	 * @return table size
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Returns the collision resolution scheme used for this hash table. Implement
	 * with one of the following collision resolution strategies. Define this method
	 * to return an integer to indicate which strategy.
	 * 
	 * 1 OPEN ADDRESSING: linear probe; 2 OPEN ADDRESSING: quadratic probe; 3 OPEN
	 * ADDRESSING: double hashing; 4 CHAINED BUCKET: array of arrays; 5 CHAINED
	 * BUCKET: array of linked nodes; 6 CHAINED BUCKET: array of search trees; 7
	 * CHAINED BUCKET: linked nodes of arrays; 8 CHAINED BUCKET: linked nodes of
	 * linked node; 9 CHAINED BUCKET: linked nodes of search trees
	 * 
	 * @return the number that indicates which strategy is used
	 */
	@Override
	public int getCollisionResolution() {
		return 5; // I choose to implement with array of linked nodes
	}
}
