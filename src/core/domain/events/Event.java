package core.domain.events;

import common.datastructures.*;

/**
 *  A lightweight event dispatcher similar to Roblox's BindableEvent.
 *  Listeners can connect to events and react when fired.
 *  @param <T> the type of data passed to listeners
 */
public class Event<T> {
    private final LinkedList<Listener<T>> listeners;

    /**
     * Mimics java's consumer functional interface.
     * This lets other modules handle data fired by an event
     * (e.g. Recording a sale reduces stock on inventory module)
     *
     * @param <T> data type to be handled
     */
    @FunctionalInterface
    public interface Listener<T> {
        void handle(T data);
    }

    /**
     *
     * @param listeners
     */
    public Event(LinkedList<Listener<T>> listeners){
        this.listeners = new LinkedList<>();
    }

    /**
     * Connects a listener to this event.
     * @param listener function that handles event data
     */
    public void subscribe(Listener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Fires the event, invoking all connected listeners.
     *
     * This part is not as efficient as it should be,
     * since we're limited to the implementation of a linked list,
     * and the extension of the iterable class requires
     *
     *
     * @param data event data to send to listeners
     */
    public void fire(T data) {
        for (Listener<T> listener : listeners.toArray()) {
            listener.handle(data);
        }
    }
}