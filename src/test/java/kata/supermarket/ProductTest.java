package kata.supermarket;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void singleItemHasExpectedUnitPriceFromProduct() {
        final BigDecimal price = new BigDecimal("2.49");
        Discount noDiscount = new Discount(DiscountCode.NO_DISCOUNT, ProductType.INDIVIDUAL);
        assertEquals(price, new Product(price, noDiscount, ProductType.INDIVIDUAL, "single item").oneOf().price());
    }
}