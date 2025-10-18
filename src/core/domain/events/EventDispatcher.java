package core.domain.events;

import datastructures.*;

public class EventDispatcher {
    private LinkedList<RemoteEvent<Object[]>> listeners;

    public EventDispatcher(LinkedList<RemoteEvent<Object[]>> listeners) {
        this.listeners = listeners;
    }

    public void emit(){

    }
}
