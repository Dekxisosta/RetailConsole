package core.shared.id;

/**
 * Base utility class for generating prefixed alphanumeric IDs.
 * Subclasses should specify their own prefix by implementing getPrefix()
 *
 * Perfect implementation for creating immutable IDs. This does not
 * utilize a randomizer, but merely increments counter variables
 * for alpha and numeric characters
 *
 * @version 1.0
 */
public abstract class IDGenerator {
    private int alphaCounter = 0;
    private int counter = 1;

    /** @return generated id with specified format (e.g. PRE-A0692) */
    public String generateID() {
        return String.format("%s-%s%04d", getPrefix(), getAlphaCode(), getNumberCode());
    }

    /**
     * Must be extended by subclasses.
     * This will determine the prefix of the id generated
     * @return prefix of the id
     */
    protected abstract String getPrefix();

    /**
     * Gets a number code and increments its corresponding counter
     * @return number code
     */
    private int getNumberCode() {
        return counter++;
    }

    /**
     * Gets an alpha code, increments when numeric counter
     * exceeds its threshold of 4 digit combinations
     * @return alpha code
     */
    private char getAlphaCode() {
        if (counter > 9999) {
            ++alphaCounter;
            counter = 1;
        }
        return (char) ('A' + alphaCounter);
    }
}