package kata.supermarket;

import java.math.BigDecimal;

public class ItemByUnit implements Item {

    private final Product product;
    private final long noOfUnits;

    ItemByUnit(final Product product, final long noOfUnits) {
        if(noOfUnits < 1)
            throw new IllegalArgumentException();
        this.product = product;
        this.noOfUnits = noOfUnits;
    }

    /**
     * this returns the total price of the item excluding the discounts if any
     * @return
     */
    public BigDecimal price() {
        return product.pricePerUnit().multiply(BigDecimal.valueOf(noOfUnits));
    }

    @Override
    public Product getProduct() {
        return product;
    }

    public long getNoOfUnits() {
        return noOfUnits;
    }
}