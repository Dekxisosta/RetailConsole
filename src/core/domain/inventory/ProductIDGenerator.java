package core.domain.inventory;

import common.util.*;

/**
 * Generates product IDs. This class extends the id generator
 * abstraction which needs designated prefixes
 * @see IDGenerator
 */
public class ProductIDGenerator extends IDGenerator {
    @Override
    public String getPrefix(){
        return "PRD";
    }
}