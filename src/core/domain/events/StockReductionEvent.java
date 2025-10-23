package core.domain.events;

import core.shared.dto.*;

/**
 * Fired to transfer lightweight Product data
 * to be used by other modules
 * @param <T> ProductDTO
 */
public class StockReductionEvent<T extends ProductDTO> extends Event<ProductDTO> {
    public StockReductionEvent(Listener<ProductDTO>... listeners) {
        super(listeners);
    }
}