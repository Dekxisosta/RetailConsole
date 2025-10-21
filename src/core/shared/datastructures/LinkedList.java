package core.shared.datastructures;

import core.domain.api.model.Record;

/**
 * Linked list data structure that handles list nodes with
 * two pointers. This is merely a mini implementation of
 * java's built-in LinkedList.
 *
 * To avoid redundant code, this LinkedList is tailored for
 * model instances that extends the Record interface
 *
 * @version 1.2
 * @param <T> data type of the linked list instance
 * @see Record
 * @see ListNode
 */
public class LinkedList <T extends Record>{
    protected ListNode<T> head;
    protected ListNode<T> tail;
    protected int size;

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
             * public APIs will work as intended (e.g. e.getMessage())
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
        if(isEmpty()){
            head = new ListNode<>(data);
            tail = head;
            return;
        }else{
            tail.setNext(new ListNode<>(data));
            tail.getNext().setPrev(tail);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Removes the node with the specified id from this list
     * @param id the location of the node to be removed
     */
    public void remove(String id) throws ListException{
        removeNode(getNodeWithId(id));
    }

    /**
     * Gets the data of the node with the specified id
     * @param id reference to the node's identity
     * @return data of the node
     * @throws ListException when list is empty,
     *         and id cannot be found
     */
    public T get(String id) throws ListException{
        return getNodeWithId(id).getData();
    }

    /**
     * Checks if list is empty
     * @return true if empty, otherwise false
     */
    public boolean isEmpty(){
        return head == null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        ListNode<T> current = head;
        while(current!=null){
            sb.append(current.getData().toString());
            current = current.getNext();
        }
        return sb.toString();
    }

    public String toStringReverse(){
        StringBuilder sb = new StringBuilder();

        ListNode<T> current = tail;
        while(current!=null){
            sb.append(current.getData().toString());
            current = current.getPrev();
        }
        return sb.toString();
    }

    /**
     * Checks if linked list has only one element
     *
     * @return true if list contains a single element,
     * otherwise false
     */
    private boolean isSingle() {
        return size==1;
    }

    /**
     * Gets the node with the specified id and returns it
     * for further manipulation
     * @param id the reference to the node
     * @return node with the specified id
     * @throws ListException
     */
    private ListNode<T> getNodeWithId(String id) throws ListException{
        if(isEmpty()) throw new ListException("List is empty");
        ListNode<T> current = head;
        while(current!=null){
            if(current.getData().getId().equals(id)){
                return current;
            }
            current=current.getNext();
        }
        throw new ListException("Unable to find id");
    }

    /**
     * Removes the node that is passed through this method. It
     * handles all potential edge cases before removing the node
     * @param node the node to be removed
     */
    private void removeNode(ListNode<T> node) {
        if (node == null) return;

        if (isSingle()) {
            head = tail = null;
        } else {
            if (node.getPrev() != null) node.getPrev().setNext(node.getNext());
            if (node.getNext() != null) node.getNext().setPrev(node.getPrev());

            if (node == head) head = node.getNext();
            if (node == tail) tail = node.getPrev();
        }

        node.setNext(null);
        node.setPrev(null);

        size--;
    }
}
