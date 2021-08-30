package kata.supermarket;

import java.math.BigDecimal;

/**
 * This implementation represents products which
 * can be weighed as the name suggests
 */
public class WeighedProduct implements IProduct{

    private final BigDecimal pricePerKilo;
    private final Discount discountCode;
    private final String name;
    private final ProductType productType;

    public WeighedProduct(BigDecimal pricePerKilo, Discount discountCode, String name, ProductType productType) {
        this.pricePerKilo = pricePerKilo;
        this.discountCode = discountCode;
        this.name = name;
        this.productType = productType;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Discount discount() {
        return discountCode;
    }

    @Override
    public ProductType getProductType() {
        return productType;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}