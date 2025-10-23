package core.domain.api.model;

/**
 * This serves as an integral api for the system's execution.
 * The record class implements a getId method, wherein ID is
 * considered an immutable field which enables unique identification
 * of an item / object
 */
public interface Record{
    String getId();
}
