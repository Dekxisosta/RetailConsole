package core.domain.bindables;

import core.shared.dto.*;


public class Events {
    private Events(){}
    public static Event.StockReductionEvent StockReduction = new Event.StockReductionEvent();
    public static Event.ProductAddedEvent ProductAdded = new Event.ProductAddedEvent();
    public static Event.ProductUpdatedEvent ProductUpdated = new Event.ProductUpdatedEvent();
}


/**
 * A lightweight event dispatcher similar to Roblox's BindableEvent.
 * Listeners can connect to events and react when fired.
 * @param <T> the type of data passed to listeners
 */
class Event<T> {
    private Listener<T>[] listeners;
    public static class StockReductionEvent<T extends ProductDTO> extends Event<ProductDTO> {}
    public static class ProductAddedEvent<T extends ProductDTO> extends Event<ProductDTO> {}
    public static class ProductUpdatedEvent<T extends ProductDTO> extends Event<ProductDTO> {}
    /**
     * Functional Interface that ensures data is handled by any listener subscribed to the event.
     * @param <T> the type of data to be handled
     */
    @FunctionalInterface
    public interface Listener<T> {
        void handle(T data);
    }

    /**
     * Default constructor — starts with an empty listener array.
     */
    public Event() {
        this.listeners = (Listener<T>[]) new Listener[0];
    }

    /**
     * Optional constructor — allows passing initial listeners.
     *
     * @param listeners handlers that implement Listener
     */
    @SafeVarargs
    public Event(Listener<T>... listeners) {
        this.listeners = listeners;
    }

    /**
     * Adds a new listener by replacing the listener array with a new one.
     *
     * @param listener the listener to add
     */
    @SuppressWarnings("unchecked")
    public void addListener(Listener<T> listener) {
        int oldLength = listeners.length;
        Listener<T>[] newListeners = (Listener<T>[]) new Listener[oldLength + 1];
        System.arraycopy(listeners, 0, newListeners, 0, oldLength);
        newListeners[oldLength] = listener;
        listeners = newListeners;
    }

    /**
     * Fires the event, invoking all connected listeners.
     *
     * @param data the data to be handled by all listeners
     */
    public void fire(T data) {
        for (Listener<T> listener : listeners) {
            listener.handle(data);
        }
    }
}
