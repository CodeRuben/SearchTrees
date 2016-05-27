package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic singly Linked List data structure which implements
 * the ListI interface. Linked List has methods which allow for
 * insertion and removal of elements, as well as a reversal 
 * method used to reverse the order of all of the elements in 
 * the list
 * 
 * @author Ruben Ramirez 
 * @param <E> the type of elements in the list
 */

public class LinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an object to the beginning of the list. 
     * @param obj the object to be added to the list.
     */
    public void addFirst(E obj) {
        Node<E> newNode = new Node<>(obj);

        if(size == 0)
            tail = head = newNode;
        else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds an object to the end of the list.
     * @param obj the object to be added to the list.
     */
    public void addLast(E obj) {
        Node<E> newNode = new Node<>(obj);

        if(size == 0)
            head = tail = newNode;
        else 
            tail.next = tail = newNode;
        size++;
    }

    /**
     * Removes the first Object in the list and returns it.
     * Returns null if the list is empty.
     * @return the object removed.
     */
    public E removeFirst() {
        E tmp;

        if(size == 0)
            return null;

        tmp = head.data;
        head = head.next;
        size--;
        return tmp;
    }

    /**
     * Removes the last Object in the list and returns it.
     * Returns null if the list is empty.
     * @return the object removed.
     */
    public E removeLast() {
        E tmp;
        Node<E> index = head;
        Node<E> trailIndex = null;

        if(size == 0)
            return null;
        else if(size == 1) {
            tmp = head.data;
            head = tail = null;
            size--;
            return tmp;
        }

        // Use trailing pointer to get position of the second to last node. 
        for(int i = 0; i < size - 1; i++) {
            trailIndex = index;
            index = index.next;
        }
        tmp = index.data;
        tail = trailIndex;
        trailIndex.next = null;
        size--;
        return tmp;
    }

    public E removeLastInstance(E obj) {
        Node<E> chere = null, phere = null;
        Node<E> previous = null;
        Node<E> current = head;
        E tmp;

        if(isEmpty())
            return null;

        while(current != null) {
            if(((Comparable<E>)obj).compareTo(current.data) == 0) {
                phere = previous;
                chere = current;
            }
            previous = current;
            current = current.next;
        }
        if(phere == null && chere == null)
            return null;
        else if(phere == null && chere != null)
            return removeFirst();
        else if(chere == null && phere != null)
            return removeLast();
        else {
            tmp = chere.data;
            phere.next = chere.next;
            size--;
            return tmp;
        }
    }

    /**
     * Find and removes an Object in the list and returns it.
     * Returns null if the list is empty.
     * @param obj the object to be removed from the list.
     * @return the object removed.
     */
    public E remove(E obj) {
        Node<E> previous = null;
        Node<E> current = head;
        E tmp;

        if(size == 0)
            return null;

        while(current != null && ((Comparable<E>) obj).compareTo(current.data)!= 0) {
            previous = current;
            current = current.next;
        }

        if(current == null)       // Item not found
            return null;
        else if(current == head) {  // Item is in the first position
            tmp = current.data;
            head = head.next;
        }
        else if(current == tail) {  // Item is in the last position
            tmp = current.data;
            previous.next = null;
            tail = previous;
        }
        else {                    // Item is in the middle of the list
            tmp = current.data;
            previous.next = current.next;
        }
        if(head == null)
            tail = null;

        size--;
        return tmp;
    }

    /**
     * Returns the first Object in the list, but does not remove it.
     * Returns null if the list is empty.
     * @return the object at the beginning of the list.
     */
    public E peekFirst() {
        if(size == 0)
            return null;
        return head.data;		
    }

    /**
     * Returns the last Object in the list, but does not remove it. 
     * Returns null if the list is empty.
     * @return the object at the end of the list.
     */
    public E peekLast() {
        if(size == 0)
            return null;
        return tail.data;
    }

    /**
     * Return the list to an empty state.
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    /**
     * Test whether the list is empty.
     * @return true if the list is empty, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Test whether the list is full.
     * @return true if the list is full, otherwise false
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Returns the number of Objects currently in the list.
     * @return the number of Objects currently in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Test whether the list contains an object. This will use the object's
     * compareTo method to determine whether two objects are the same.
     * @param obj The object to look for in the list
     * @return true if the object is found in the list, false if it is not found
     */
    public boolean contains(E obj) {
        Node<E> temp = head;  // temporary pointer used to traverse the list

        while(temp != null) {
            if(((Comparable<E>)obj).compareTo(temp.data) == 0)
                return true;
            temp = temp.next;
        }
        return false;
    }

    /**
     * Checks whether the list contains an object. This will use the object's
     * compareTo method to determine whether two objects are the same.
     * @param obj The object to look for in the list
     * @return the object if it is found in the list, false if it is not found
     */
    public E get(E obj) {
        Node<E> temp = head;  // temporary pointer used to traverse the list

        while(temp != null) {
            if(((Comparable<E>)obj).compareTo(temp.data) == 0)
                return temp.data;
            temp = temp.next;
        }
        return null;
    }

    /**
     * Reverse the order of the list.
     * This will exactly reverse the order of the list, so the first element is 
     * last, and vice-versa.
     */
    public void reverse() {
        Node<E> index = head;
        E[] array = (E[]) new Object[size];

        // Store linked list data in a generic array
        for(int i = 0; i < size; i++) {
            array[i] = index.data;
            index = index.next;
        }

        index = head;

        // Insert data back into linked list in reverse order
        for(int j = size - 1; j >= 0; j--) {
            index.data = array[j];
            index = index.next;
        }
    }

    /**
     * Returns an Iterator of the values in the list, presented in
     * the same order as the list. 
     */
    public Iterator<E> iterator() {
        return new iteratorHelper();
    }

    /*
     * Node inner class that holds a piece of data and a reference
     * to another node. Nodes serve as a building block for the 
     * linked list.
     */
    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T obj) {
            this.data = obj;
            this.next = null;
        }
    }

    class iteratorHelper implements Iterator<E> {
        Node<E> index;   // Pointer used to traverse the list

        public iteratorHelper(){
            index = head;
        }

        /*
         * Tests whether there are additional pieces of data in the
         * linked list. Return true if there is at least one more 
         * node, false if index pointer has reached the end of the list.
         */
        public boolean hasNext() { 
            return index != null;
        }

        /*
         * Returns the next item on the linked list if there is one.
         */
        public E next() {
            E tmp; 
            if(!hasNext())
                throw new NoSuchElementException();

            tmp = index.data;  
            index = index.next;
            return tmp;
        }

        // Unsupported operation
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
