/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_structures;

import searchtrees.*;
import java.util.Iterator;

public class Queue<E> {
    private int size;
    private LinkedList<E> list;
    
    public Queue() {
        this.size = 0;
        this.list = new LinkedList<>();
    }
    
    // inserts the object obj into the queue
    public void enqueue(E obj) {
        list.addLast(obj);
        size++;
    }
     
    // removes and returns the object at the front of the queue   
    public E dequeue() {
        E data = list.peekFirst();
        if(list.removeFirst() != null) {
            size--;
            return data;
        }
        return null;
    }
    
    // returns the number of objects currently in the queue    
    public int size() {
        return size;
    }
    
    // returns true if the queue is empty, otherwise false   
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    // returns but does not remove the object at the front of the queue   
    public E peek() {
        return list.peekFirst();
    }
    
    // returns true if the Object obj is in the queue    
    public boolean contains(E obj) {
        return list.contains(obj);
    }
     
    // returns the queue to an empty state  
    public void makeEmpty() {
        size = 0;
        list.clear();
    }
    
    // removes the Object obj if it is in the queue and
    // returns true, otherwise returns false.
    public boolean remove(E obj) {
        if(list.remove(obj) == null)
            return false;
        size--;
        return true;
    }
    
    // returns an iterator of the elements in the queue.  The elements
    // must be in the same sequence as dequeue would return them.    
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
}
