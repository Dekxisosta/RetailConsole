package core.domain.events;

import core.shared.dto.ProductDTO;

/**
 * Fired to transfer lightweight Product data
 * to be used by other modules
 * @param <T> ProductDTO
 */
public class ProductTransferEvent<T extends ProductDTO> extends Event<ProductDTO> {
    public ProductTransferEvent(Listener<ProductDTO>... listeners) {
        super(listeners);
    }
}