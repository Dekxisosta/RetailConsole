package common.datastructures;

/**
 * ListNode model with two pointers and stores a value of datatype T.
 * To achieve a modular design, the group decided to use generic type
 * parameters. This ensures that all three modules can have
 * separate databases to foster a more decentralized design
 *
 * @param <T> data to be inputted
 */
public final class ListNode <T> {
    private ListNode<T> next;
    private ListNode<T> prev;
    private T data;



    /**
     * Public constructor of the ListNode model
     * @param data the data allocated to the list node
     */
    public ListNode(T data) {
        this.data = data;
    }



    /**
     * Gets the node's next referenced node
     * @return the next node
     */
    public ListNode<T> getNext() {
        return next;
    }



    /**
     * Gets the node's previous referenced node
     * @return the previous node
     */
    public ListNode<T> getPrev() {return prev;}



    /**
     * Gets the allocated data of the instantiated list node
     * @return data of the list node
     */
    public T getData() {return data;}



    /**
     * Reallocates new data to the instantiated list node
     * @param data data to be allocated to the node
     */
    public void setData(T data) {
        this.data = data;
    }



    /**
     * Sets a new next pointer reference of another node
     * @param next whose memory address is to be referenced
     *             by the node's internal next pointer field
     */
    public void setNext(ListNode<T> next) {
        this.next = next;
    }



    /**
     * Sets a new previous pointer reference of another node
     * @param prev whose memory address is to be referenced
     *                 by the node's internal previous pointer field
     */
    public void setPrev(ListNode<T> prev) {
        this.prev = prev;
    }



    /**
     * Instead of the node's memory address, it'll instead
     * show the value of its internal data field
     * @return string value of its stored data
     */
    @Override
    public String toString(){
        return data.toString();
    }
}
