package data_structures;

import java.util.Iterator;
import searchtrees.*;

/**
 * Implementation of a generic top-down splay tree
 * Items are added as key-value pairs into the tree
 * @author Ruben Ramirez
 * @param <K>, keys stored in the tree
 * @param <V>, values stored in the tree
 */

public class SplayTree<K,V> implements Iterable<K> {
    private Node<K,V> root;
    private int currentSize;
    
    public SplayTree() {
        this.root = null;
        this.currentSize = 0;
    }
    
    /**
     * Adds an entry into the splay tree. Duplicate entries override existing 
     * data.
     * @param obj, the item to be added to the tree
     * @return true if the item was successfully added to the tree
     */
    public boolean add(K key, V value) {
        Node<K,V> newNode = new Node<>(key, value);
        
        if(root == null) {
            root = newNode;
            this.currentSize++;
            return true;
        }
        splay(newNode);
        
        // If element is already in the tree, override it's value
        if(((Comparable<K>)key).compareTo(root.key) == 0) {
            root.value = value;
            return true;
        }
        
        // Insert new node at the root of the tree
        if(((Comparable<K>)key).compareTo(root.key) < 0) {
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
            root = newNode;
        }
        else {
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
            root = newNode;
        }
        this.currentSize++;
        return true;
    }
    /**
     * Searches the tree to see if an item exists in the structure
     * @param obj, the object to be searched for
     * @return true if the object is found, false otherwise
     */
    public boolean contains(K key) {
        Node<K,V> node = new Node<>(key, null);
        
        if(root == null) 
            return false;
        splay(node);
        if(((Comparable<K>)key).compareTo(root.key) == 0) 
            return true;
        return false;
    }
    
    /**
     * Searches for a key and returns the associated value
     * @param key, used to search the tree
     * @return V, the value associated with the key, null if key is not found
     */
    public V getValue(K key) {
        Node<K,V> node = new Node<>(key, null);
        
        if(root == null) 
            return null;
        splay(node);
        if(((Comparable<K>)key).compareTo(root.key) == 0) 
            return root.value;
        return null;
    }
    
    /**
     * Searches for a key and returns the key if it is found
     * @param key, used to search the tree
     * @return K, the key associated with the object, null if key is not found
     */
    public K get(K key) {
        Node<K,V> node = new Node<>(key, null);
        
        if(root == null) 
            return null;
        splay(node);
        if(((Comparable<K>)key).compareTo(root.key) == 0) 
            return root.key;
        return null;
    }
    
    /**
     * Returns the value of the element stored at the root of the tree
     * @return the value stored in the root node
     */
    public V peek() {
        if(isEmpty())
            return null;
        return root.value;
    }
    
    /**
     * Deletes a node if it is in the tree.
     * @param key, the key of the node to be deleted
     * @return K, the key of the deleted node, null if node was not found
     */
    public K remove(K key) {
        Node<K,V> node = new Node<>(key, null);
        Node<K,V> tmp; 
        if(root == null)
            return null;
        
        splay(node);
        
        // If object is not in the tree
        if(((Comparable<K>)key).compareTo(root.key) != 0) 
            return null;
        
        // If largest element has to be deleted
        if(root.right == null) {
            root = root.left;
            currentSize--;
            return key;
        }
        // Get the in order successor and delete it
        tmp = deleteNext(root);
        root.key = tmp.key;
        return key;
    }
    
    /**
     * Restores the tree back to an empty state
     */
    public void clear() {
        this.root = null;
        this.currentSize = 0;
    }
    
    /**
     * Helped method that finds the in order successor of a given node and 
     * deletes it. Returns the value of the in order successor to be placed
     * at the root of the tree.
     * @param node, node to be used to find the successor node
     * @return the node containing the in order successor, null if successor
     * doesn't exist
     */
    private Node<K,V> deleteNext(Node<K,V> node) {
        Node<K,V> tmp = null;
        if(node == null)
            return null;
        
        if(node.right != null) {
            if(node.right.left == null) {
                tmp = node.right;
                node.right = tmp.right;
                currentSize--;
                return tmp;
            }
            
            node = node.right;
            tmp = node;
            while(node.left != null) {
                tmp = node;
                node = node.left;
            }
            
            // Delete node has no children
            if(node.right == null && tmp != null) {
                tmp.left = null;
                currentSize--;
                return node;
            }
            // Delete node with right child
            else {
                tmp.left = node.right;
                currentSize--;
                return node;
            }
        }
        return null;
    }
    
    /**
     * Performs a splay operation. Traverses down the tree and splits the tree
     * into sub sections. The last node visited gets placed at the root position/
     * @param node, node to search for
     */
    private void splay(Node<K,V> node) {
        Node<K,V> holder = new Node<>(null, null);
        Node<K,V> L, R, top;
        top = root;
        L = R = holder;
        
        while(true) {
            if(((Comparable<K>)node.key).compareTo(top.key) < 0) {
                if(top.left == null) 
                        break;
                if(((Comparable<K>)node.key).compareTo(top.left.key) < 0) {
                    top = rightRotate(top);
                    if(top.left == null) 
                        break;
                }
                R.left = top;
                R = top;
                top = R.left;
                R.left = null;
            }
            else if(((Comparable<K>)node.key).compareTo(top.key) > 0) {
                if(top.right == null) 
                        break;
                if(((Comparable<K>)node.key).compareTo(top.right.key) > 0) {
                    top = leftRotate(top);
                    if(top.right == null) 
                        break;
                }
                L.right = top;
                L = top;
                top = L.right;
                L.right = null;
            }
            else
                break;
        }
        // Assemble tree
        L.right = top.left;
        R.left = top.right;
        top.left = holder.right;
        top.right = holder.left;
        root = top;
    }
    
    /**
     * Returns the number of elements stored in the tree
     * @return int, the amount of nodes within the tree
     */
    public int size() {
        return this.currentSize;
    }
    
    /**
     * Checks to see if the tree is currently empty
     * @return true if there are no elements in the tree, false otherwise
     */
    public boolean isEmpty() {
        return this.currentSize == 0;
    }
    
    /**
     * Performs a right rotation on a given node. 
     * @param node, the node to be rotated
     * @return the new root of the rotated subtree
     */
    private Node<K,V> rightRotate(Node<K,V> node) {
        Node<K,V> newTop = node.left;
        node.left = newTop.right;
        newTop.right = node;
        return newTop;
    }
    
    /**
     * Performs a left rotation on a given node. 
     * @param node, the node to be rotated
     * @return the new root of the rotated subtree
     */
    private Node<K,V> leftRotate(Node<K,V> node) {
        Node<K,V> newTop = node.right;
        node.right = newTop.left;
        newTop.left = node;
        return newTop;
    }
    
    public void inOrder(Node<K,V> node) {
        if(node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    public void breadthTraversal() {
        Queue<Node<K,V>> q = new Queue<>();
        Node<K,V> node;
        
        if(root != null) {
            q.enqueue(root);
            
            while(!q.isEmpty()) {
                node = q.dequeue();
                System.out.print(node.key + " ");
                if(node != null) {
                    if(node.left != null)
                        q.enqueue(node.left);
                    if(node.right != null)
                        q.enqueue(node.right);
                   
                }
            }
        }
    }
    
    /**
     *  Returns an iterator of the data in the Splay Tree. The 
     *  elements returned in in-order sequence
     *  @return an iterator that traverses the data in the Splay Tree
     */
    public Iterator<K> iterator() {
        return new IteratorHelper();
    }

    private class IteratorHelper implements Iterator<K> {
        private Node<K,V>[] array;
        private int index;

        public IteratorHelper() {
            array = new Node[currentSize];
            index = 0;
            fillArray(root);
            index = 0;
        }

        public boolean hasNext() {
            return index < array.length;
        }

        public K next() {
            return (K) array[index++].key;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        // Performs an in-order traversal of the tree and adds
        // each element to an array.
        private void fillArray(Node<K,V> node) {
        if(node != null) {
            fillArray(node.left);
            array[index++] = node;
            fillArray(node.right);
        }
    }
}

    /**
     * Node class which holds a key and value pair. Node also has pointers to
     * left and right child nodes. Implements the compareTo method in the 
     * Comparable interface.
     */
    private class Node<K,V> implements Comparable<Node<K,V>> {
        private K key;
        private V value;
        private Node<K,V> left, right;
        
        public Node(K key, V val) {
            this.key = key;
            this.value = val;
            this.left = null;
            this.right = null;
        }
        
        public int compareTo(Node<K,V> node) {
            return (((Comparable<K>)key).compareTo(node.key));
        }
    }
}
