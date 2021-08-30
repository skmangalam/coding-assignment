package kata.supermarket;

import java.math.BigDecimal;

/**
 * This is the representation of products which can be counted.
 */
public class Product implements IProduct{

    private final BigDecimal pricePerUnit;
    private final Discount discountCode;
    private final String name;
    private final ProductType productType;

    public Product(final BigDecimal pricePerUnit, Discount discountCode, ProductType productType, String name) {
        this.pricePerUnit = pricePerUnit;
        this.discountCode = discountCode;
        this.name = name;
        this.productType = productType;
    }

    public ItemByUnit oneOf() {
        return new ItemByUnit(this, 1);
    }

    @Override
    public String getName() {
        return name;
    }

    public Discount discount() {
        return discountCode;
    }

    @Override
    public ProductType getProductType() {
        return productType;
    }

    public BigDecimal pricePerUnit() {
        return pricePerUnit;
    }
}