package datastructures;

/**
 * Linked list data structure that handles list nodes with
 * two pointers. This is merely a mini implementation of
 * java's built-in LinkedList
 *
 * @author Dekxisosta
 * @version 1.0
 * @param <T> data type of the linked list instance
 * @see ListNode
 */
public final class LinkedList <T>{
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    /**
     * Internal class for handling list exceptions.
     * Extends {@link RuntimeException}
     */
    public static class ListException extends RuntimeException {
        public ListException(String message) {
            /*
             * Since this is 'advanced' code, super() basically points back
             * to the superclass this class extends to, which in this case
             * is RuntimeException. The message param is to ensure the superclass's
             * public APIs will work (e.g. e.getMessage())
             */
            super(message);
        }
    }

    /**
     * Public constructor of the class. When instantiated, it
     * does not insert values. Can be removed as it is an unnecessary declaration,
     * but to make intent clearer, is written as so
     */
    public LinkedList(){}

    /**
     * Inserts a valued node to the ending point of the list.
     * Replaces existing tail node if list is not empty
     * @param data the node's data
     */
    public void add(T data){
        addLast(data);
    }
    /**
     * Inserts a valued node to the starting point of the list.
     * Replaces existing head node if list is not empty
     * @param data the node's data
     */
    public void addFirst(T data){
        if(isEmpty()){
            head = new ListNode<>(data);
            tail = head;
        }else{
            head.setPrev(new ListNode<>(data));
            head.getPrev().setNext(head);
            head = head.getPrev();
        }
        size++;
    }

    /**
     * Inserts a valued node to the ending point of the list.
     * Replaces existing tail node if list is not empty
     * @param data the node's data
     */
    public void addLast(T data){
        if(isEmpty()){
            addFirst(data);
            return;
        }else{
            tail.setNext(new ListNode<>(data));
            tail.getNext().setPrev(tail);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Inserts a node at index with the given value.
     * The checks before the last code block are necessary
     * to prevent null pointer exceptions from being thrown
     * @param data the node's data
     * @param index
     */
    public void insertAtIndex(T data, int index) throws ListException{
        if(!isValidIndex(index)) throw new ListException("Index out of bounds.");

        if(isEmpty()) return;
        if(isFirst(index)) {addFirst(data); return;}
        if(index==size) {addLast(data); return;}

        ListNode<T> newNode = new ListNode<>(data);

        // Sets the pointers of the new node
        newNode.setNext(getNodeAtIndex(index));
        newNode.setPrev(newNode.getNext().getPrev());

        // Updates the pointers of the existing nodes
        // to accommodate the insertion of the new node
        newNode.getNext().setPrev(newNode);
        newNode.getPrev().setNext(newNode);

        size++;
    }

    /**
     * Removes the last node of the list
     * If list is empty, then nothing is done
     */
    public void remove(){
        removeLast();
    }

    /**
     * Removes the first node of the list
     * If list is empty, then nothing is done
     */
    public void removeFirst(){
        if(isEmpty()) return;
        if(isSingle()){
            head = tail = null;
        }else{
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
    }

    /**
     * Removes the last node of the list
     * If list is empty, then nothing is done
     */
    public void removeLast(){
        if(isEmpty()) return;
        if(isSingle()){
            removeFirst();
            return;
        }else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
    }

    /**
     * Removes a list node at the specified index.
     * Immediately throws an error if index is invalid,
     * if not, then proceed with reusing earlier methods
     * to avoid repetitive code. If none of the prior
     * conditions are fired, then continue with
     * regular removal
     *
     * @param index the location of the node to be removed
     */
    public void removeAtIndex(int index){
        if(!isValidIndex(index)) throw new ListException("Index out of bounds.");
        if(isFirst(index)){
            removeFirst();
            return;
        }
        if(isLast(index)){
            removeLast();
            return;
        }
        ListNode<T> current = getNodeAtIndex(index);
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
    }

    /**
     * Checks if list is empty
     * @return true if empty, otherwise false
     */
    public boolean isEmpty(){
        return head == null;
    }

    /**
     * Checks if linked list has only one element
     *
     * @return true if list contains a single element,
     * otherwise false
     */
    public boolean isSingle(){
        return size == 1;
    }

    /**
     * Gets the value of the data inside the indexed list node
     *
     * @param index the index of the list node
     */
    public T get(int index) throws ListException{
        if(!isValidIndex(index)) throw new ListException("Index out of bounds.");
        return getNodeAtIndex(index).getData();
    }

    /**
     * Sets the value of the data of the specified indexed list node
     *
     * @param data the new value of the specified list node
     * @param index the index or location of the list node to be set
     * @throws ListException masking an index out of bounds exception
     */
    public void set(T data, int index) throws ListException{
        if(!isValidIndex(index)) throw new ListException("Index out of bounds.");
        getNodeAtIndex(index).setData(data);
    }

    /**
     * Checks if the linked list contains a specified
     * value of data type
     *
     * @param data the data to be searched
     * @throws ListException masking an index out of bounds exception
     * @return true if it does contain a node with the matching data,
     *         otherwise false
     */
    public boolean contains(T data) throws ListException{
        ListNode<T> current = head;

        while(current!=null){
            if(current.getData().equals(data)) return true;
            current = current.getNext();
        }
        return false;
    }

    /**
     * Traverses the array and looks for the first instance
     * from the head that contains matching data.
     *
     * In case of not finding the index, instead of throwing an exception,
     * a sentinel value -1 is put in place
     *
     * @param data comparable data for searching the first occurrence
     * @return first instance that matches param data
     */
    public int indexOf(T data){
        ListNode<T> current = head;

        int counter=0;
        while(current!=null){
            if(current.getData().equals(data)) return counter;
            current = current.getNext();
            counter++;
        }

        return -1;
    }

    /**
     * To prevent O(n) lookups for checking size,
     * an internal counter field, which mimics the
     * actual linked list structure, is created
     *
     * @return the size of the linked list
     */
    public int size(){
        return size;
    }

    /**
     * Creates an array of the list's elements in usual order
     * since regular arrays implement Iterable,
     * which is useful for viewing elements
     *
     * This ensures clear separation of concerns
     * as the LinkedList only handles how to manage data
     * not how to display it
     *
     * @return array of list's datatype
     */
    public T[] toArray(){
        // Explicit typecasting to ensure type consistency
        T[] arr = (T[]) new Object[size];

        // Loop through the list starting from the head
        ListNode<T> current = head;
        for(int i = 0; current!=null ; i++){
            arr[i] = current.getData();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Creates an array of the list's elements in reverse order
     * since regular arrays implement Iterable,
     * which is useful for viewing elements
     *
     * This ensures clear separation of concerns
     * as the LinkedList only handles how to manage data
     * not how to display it
     *
     * @return array of list's datatype, reverse in order
     */
    public T[] toReverseArray(){
        // Explicit typecasting to ensure type consistency
        T[] arr = (T[]) new Object[size];

        // Loop through the list starting from the tail
        ListNode<T> current = tail;
        for(int i = size-1; current!=null ; i--){
            arr[i] = current.getData();
            current = current.getPrev();
        }
        return arr;
    }

    /*
     * Author's Note:
     * This lookup method adds one extra call, but the cost is small
     * and improves clarity and readability of the code.
     *
     * It adapts traversal for a doubly linked list by choosing
     * the faster direction (head or tail), reducing average time
     * to O(n/2) instead of O(n).
     *
     * Even though callers already check the index, this method
     * revalidates it to prevent possible list corruption.
     *
     * Used mainly for node access in get, set, and remove operations.
     */
    private ListNode<T> getNodeAtIndex(int index){
        if(!isValidIndex(index)) throw new ListException("Index out of bounds.");
        if(index < size / 2){
            ListNode<T> current = head;
            for(int counter=0;;counter++)
                if(counter==index) return current;
                else current = current.getNext();
        }else{
            ListNode<T> current = tail;
            for(int counter=size-1;;counter--)
                if(counter==index) return current;
                else current = current.getPrev();
        }
    }

    // Unnecessary boilerplate for index validation, but improves readability
    private boolean isValidIndex(int index){
        return index>=0 && index<size;
    }
    // Checks if the index is the head, makes code more self-documenting
    private boolean isFirst(int index){
        return index == 0;
    }
    // Checks if the index is the tail, makes code more self-documenting
    private boolean isLast(int index){
        return index == size-1;
    }
}
