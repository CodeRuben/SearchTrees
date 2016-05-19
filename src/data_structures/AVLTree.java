package data_structures;
import searchtrees.*;
import java.util.Iterator;

/**
 * Generic Balanced Binary Search tree structure(AVL tree).
 * Tree has methods which support insertion, removal and search 
 * operations.
 * 
 * @author Ruben Ramirez   
 * @param <E> the type of values to be stored in the tree
 */

public class AVLTree<E> implements Iterable<E> {
    private Node<E> root; 
    private int currentSize;

    public AVLTree() {
        this.root = null;
        this.currentSize = 0;
    }
	
    /**
     * Adds a new node to the tree.
     * @param obj, the object to be added to the tree.
     * @return true if node was successfully added. 
     */
    public boolean add(E obj) {
        Node<E> newNode = new Node<>(obj);
        if(root == null) {
            root = newNode;
            currentSize++;
            return true;
        }
        addNode(root, newNode);
        currentSize++;  
        checkBalance(newNode);
        return true;
    }

    /**
     * Helper function that recursively traverses the tree until it
     * reaches the proper spot to insert the new node.
     * @param parent, pointer to the current position within the tree. 
     * @param node, the node to be added to the tree.
     */
    private void addNode(Node<E> parent, Node<E> node) {

        if(((Comparable<E>)node.data).compareTo(parent.data) <= 0) {
            if(parent.leftChild == null) {
                parent.leftChild = node;
                node.parent = parent;
            }
            else 
                addNode(parent.leftChild, node);
        }
        else {
            if(parent.rightChild == null) {
                parent.rightChild = node;
                node.parent = parent;
            }
            else
                addNode(parent.rightChild, node);
        }
    }

    /**
     * Gets an object from the tree, if it's stored in the tree
     * @param obj, the object to be retrieved
     * @return E, the object being retrieved 
     */
    public E get(E obj) {
        Node<E> tmp = getNode(root, obj);
        if(tmp == null)
            return null;
        return tmp.data;
    }
    
    /**
	 * Removes an object from the AVL Tree if it is in the tree
	 * @param obj, the piece of data that is going to be searched for.
	 * @return E, the object that was deleted from the tree
	 */
    public E delete(E obj) {
    	Node<E> node;
        boolean flag = true;
    	E temp = null;

    	if(currentSize == 1) {
    		temp = root.data;
    		root = null;
                currentSize--;
    		return temp;
    	}
    	node = getNode(root, obj);
    	
    	if(node == null)
    		return null;
    	
    	// If node has zero subtrees
    	if(node.leftChild == null && node.rightChild == null) {
    		if(((Comparable<E>)node.parent.data).compareTo(node.data) < 0) {
    			temp = node.data;
    			node.parent.rightChild = null;
    			checkBalance(node.parent);
    		}
    		else {
    			temp = node.data;
    			node.parent.leftChild = null;
    			checkBalance(node.parent);
    		}
    	}
        // Node has left and right subtrees
        else if(node.leftChild != null && node.rightChild != null) {
    		temp = node.data;
    		E successor = findNext(node.data);
    		delete(successor);
    		node.data = successor;
                flag = false;
    	}
    	// Node has a left subtree 
    	else if(node.leftChild != null) {
            temp = node.data;
            node.data = node.leftChild.data;
            node.leftChild = null;
            checkBalance(node);
    		}
    	// Node has a right subtree
        else {
            temp = node.data; 
            node.data = node.rightChild.data;
            node.rightChild = null;
            checkBalance(node);
            }

    	if(temp != null && flag != false)
           currentSize--;
    	return temp;
    }

    /**
     * Helper function that recursively traverses the tree until it
     * finds the node containing the correct data
     * @param node, pointer to the current position within the tree
     * @param toFind, the object being searched for
     * @return Node<E>, the node containing the object
     */
    private Node<E> getNode(Node<E> node, E toFind) {

        if(node == null)
            return null;

        if(((Comparable<E>)toFind).compareTo(node.data) < 0)
            return getNode(node.leftChild, toFind);
        else if(((Comparable<E>)toFind).compareTo(node.data) > 0)
            return getNode(node.rightChild, toFind);
        else
            return node;
    }
    
    /**
     * Gets the in-order successor of the object entered if it exists
     * @param obj, the object to be searched for
     * @return E, the in-order successor
     */
    public E findNext(E obj) {
        Node<E> node = getNode(root, obj);
        
        if(node == null)
            return null;
        
        if(node.rightChild != null) {
            if(node.rightChild.leftChild == null)
                return node.rightChild.data;
            node = node.rightChild;
            while(node.leftChild != null)
                node = node.leftChild;
            return node.data;
        }
        // If node has no children
        else {
            if(((Comparable<E>)node.parent.data).compareTo(node.data) > 0)
                return node.parent.data;
            else {
                if(node.parent.parent == null)
                    return null;
                if(((Comparable<E>)node.parent.parent.data).compareTo(node.data) > 0)
                    return node.parent.parent.data;
            }
            while(node.parent != null) {
                node = node.parent;
                if(((Comparable<E>)node.data).compareTo(obj) > 0)
                    return node.data;
            }
        }
        return null;
    }
    
    /**
     * Gets the in-order predecessor of the object entered if it exists
     * @param obj, the object to be searched for
     * @return E, the in-order predecessor
     */
    public E findPrevious(E obj) {
        Node<E> node = getNode(root, obj);
        
        if(node == null)
            return null;
        
        if(node.leftChild != null) {
            if(node.leftChild.rightChild == null)
                return node.leftChild.data;
            node = node.leftChild;
            while(node.rightChild != null)
                node = node.rightChild;
            return node.data;
        }
        // If node has no children
        else {
            if(((Comparable<E>)node.parent.data).compareTo(node.data) <= 0)
                return node.parent.data;
            else {
                if(node.parent.parent == null)
                    return null;
                if(((Comparable<E>)node.parent.parent.data).compareTo(node.data) <= 0)
                    return node.parent.parent.data;
            }
            while(node.parent != null) {
                node = node.parent;
                if(((Comparable<E>)node.data).compareTo(obj) <= 0)
                    return node.data;
            }
        }
        return null;
    }

    /**
     * Returns the height of a node within the tree.
     * @param node, the node to be examined.
     * @return int, the height value of the given node.
     */
    public int heightBelow(Node<E> node) {
        if(node == null)
            return -1;
        return node.height;
    }

    /**
     * Returns the height of the tree
     * @return int, the height value of the AVL tree
     */
    public int height() {
        if(root == null)
            return -1;
        return root.height;
    }

    /**
     * Returns the number of elements stored in the tree
     * @return int, the number of elements in the tree
     */
    public int size() {
        return this.currentSize;
    }

    /**
     * Checks to see if the tree is current empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    /**
     * Checks to see if the tree is current full
     * @return false, since a tree is never full
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Returns the current balance factor value.
     * Balance factor = height(left subtree) - height(right subtree)
     * @return int, the current value of the balance factor.
     */
    private int balanceFactor(Node<E> node) {

        int leftHeight, rightHeight;
        
        if(node == null)
            return 0;
        if(node.leftChild == null)
        	leftHeight = -1;
        else
        	leftHeight = node.leftChild.height;
        if(node.rightChild == null)
        	rightHeight = -1;
        else
        	rightHeight = node.rightChild.height;
        return leftHeight - rightHeight;
    }
	
    /**
     * Checks the balance of a given node and all of it's parent nodes.
     * Performs rotation operations when imbalances are encountered.
     * @param Node<E>, the node to be balanced
     */
    private void checkBalance(Node<E> currentNode) {

    	Node<E> node = null;
    	
    	// Iterate up the tree until the root is met or an imbalance is found
    	while(currentNode != null) {
    		
            // Adjust the height of the current node
    		if(currentSize > 1)
    		    setHeight(currentNode);

    		// If balance factor is not greater than 1, continue moving up the tree
    		if(balanceFactor(node) >= -1 && balanceFactor(node) <= 1) {
    			node = currentNode;
    			currentNode = currentNode.parent;
    		}
    		// Imbalance in left subtree
    		if(balanceFactor(node) > 1) {
    			if(balanceFactor(node.leftChild) >= 0)  
    				rightRotate(node);
    			else {
    				leftRotate(node.leftChild);
    				rightRotate(node);
    			}
    		}	
    		// Imbalance in right subtree
            else if(balanceFactor(node) < -1) {
                if(balanceFactor(node.rightChild) <= 0) 
                    leftRotate(node);
                else {
                    rightRotate(node.rightChild);
                    leftRotate(node);
                }
            }
        }
    }
	
    /**
     * Performs a right rotation on the given node
     * @param Node<E>, the node to be balanced
     */
    private void rightRotate(Node<E> node) {
    	Node<E> newTop = node.leftChild;
    	node.leftChild = newTop.rightChild;
        
        if(newTop.rightChild != null)
            newTop.rightChild.parent = node;
        
        if(node.parent == null) {
            root = newTop;
            root.parent = null;
            root.leftChild.parent = root;
        }
        else if(((Comparable<E>)node.data).compareTo(node.parent.data) <= 0)
            node.parent.leftChild = newTop;
        else
            node.parent.rightChild = newTop;
        
    	newTop.rightChild = node;
        newTop.parent = node.parent;
        node.parent = newTop;
        
        if(newTop.leftChild != null)
            setHeight(newTop.leftChild);
        if(newTop.rightChild != null)
            setHeight(newTop.rightChild);
        setHeight(newTop);
    }
    
    /**
     * Performs a left rotation on the given node
     * @param Node<E>, the node to be balanced
     */
    private void leftRotate(Node<E> node) {
    	Node<E> newTop = node.rightChild;
    	node.rightChild = newTop.leftChild;
        
        if(newTop.leftChild != null)
            newTop.leftChild.parent = node;
        
        if(node.parent == null) {
            root = newTop;
            root.parent = null;
            root.rightChild.parent = root;
        }
        else if(((Comparable<E>)node.data).compareTo(node.parent.data) <= 0)
            node.parent.leftChild = newTop;
        else
            node.parent.rightChild = newTop;
        
    	newTop.leftChild = node;
        newTop.parent = node.parent;
        node.parent = newTop;
        
        // Adjust heights of the three nodes that were rotated
        if(newTop.leftChild != null)
            setHeight(newTop.leftChild);
        if(newTop.rightChild != null)
            setHeight(newTop.rightChild);
        setHeight(newTop);
    }
    
    /**
     * Helper function sets the height value of a given node
     * @param Node<E>, the node to be adjusted
     */
    private void setHeight(Node<E> node) {
        int leftHeight, rightHeight;

        // Set null children heights to -1
        if(node.leftChild == null)
        	leftHeight = -1;
        else
        	leftHeight = node.leftChild.height;
        if(node.rightChild == null)
        	rightHeight = -1;
        else
        	rightHeight = node.rightChild.height;
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
	 *  Returns an iterator of the data in the AVL Tree. The 
	 *  elements returned in in-order sequence
	 *  @return an iterator that traverses the data in the AVL Tree
     */
    public Iterator<E> allElements() {
    	return new AVLIterator();
    }
    
    /**
	 *  Returns an iterator of the data in the AVL Tree. The 
	 *  elements returned in in-order sequence
	 *  @return an iterator that traverses the data in the AVL Tree
     */
	public Iterator<E> iterator() {
		return new AVLIterator();
	}
	
	private class AVLIterator implements Iterator<E> {
		private Node<E>[] array;
		private int index;
		
		public AVLIterator() {
			array = new Node[currentSize];
			index = 0;
			fillArray(root);
			index = 0;
		}
		
		public boolean hasNext() {
			return index < array.length;
		}
		
		public E next() {
			return (E) array[index++].data;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		// Performs an in-order traversal of the tree and adds
		// each element to an array.
		private void fillArray(Node<E> node) {
	        if(node != null) {
	            fillArray(node.leftChild);
	            array[index++] = node;
	            fillArray(node.rightChild);
	        }
	    }
	}
	
	/*
	 * Node inner class that holds a piece of data and references
	 * to parent, left and right child nodes. The node also holds a
	 * height field that returns the height of the node in as it's 
	 * stored in the tree. 
	 */
	private class Node<T> implements Comparable<Node<T>> {
        private T data;
        private Node<T> leftChild, rightChild, parent; 
        private int height;

        public Node(T value) {
            this.data = value;
            this.height = 0;
            parent = leftChild = rightChild = null;
        }

        public int compareTo(Node<T> node) {
            return (((Comparable<T>)data).compareTo(node.data));
        }
    }
}