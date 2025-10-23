package core.domain.bindables;

import core.shared.dto.*;

/**
 * Public class that houses singleton instances of events.
 * Although, an unclean design pattern when it comes to wiring
 * listeners, does its job and is simple enough to implement
 *
 * @version 1.0
 */
public class Events {
    private Events(){}
    public static Event.StockReductionEvent StockReduction = new Event.StockReductionEvent();
    public static Event.ProductAddedEvent ProductAdded = new Event.ProductAddedEvent();
    public static Event.ProductUpdatedEvent ProductUpdated = new Event.ProductUpdatedEvent();
}


/**
 * A lightweight event dispatcher similar to Roblox's BindableEvent.
 * Listeners can connect to events and react when fired.
 * It is similar to how consumers work in {@link java.util.function.Consumer}
 *
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
     * Adds a new listener by replacing the listener array with a new one.
     *
     * Unlike an arraylist implementation, this is unclean for it
     * creates, transfers and changes references into another container
     * whenever a listener is added.
     *
     * However, since we're merely adding a
     * few amount of listeners to an event. It's not much different from
     * the performance cost of the dynamic memory allocation in
     * a normal arraylist.
     *
     * A linked list is a good candidate for a more dynamic approach in this
     * scenario, but to avoid longer code, the base implementation of the linked
     * list requires the type parameter to extend the {@link core.domain.api.model.Record}
     * which is the basis for all models inside the domain
     *
     * @param listener the listener to add
     */
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
