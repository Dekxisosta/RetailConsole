package core.domain.sales;

import common.util.*;

/**
 * Generates sales record IDs. This class extends the id generator
 * abstraction which needs designated prefixes
 * @see IDGenerator
 */
public class SalesRecordIDGenerator extends IDGenerator {
    @Override
    public String getPrefix(){
        return "SAL";
    }
}
