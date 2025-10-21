package core.domain.sales.model;

import core.domain.api.model.Record;
import core.shared.dto.*;

public class ProductSale implements Record {
    private final ProductDTO product;
    private final int quantity;
    private final double totalPrice;

    public ProductSale(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    @Override
    public String getId() {
        return product.getId();
    }
}
