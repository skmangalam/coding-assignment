package kata.supermarket;

/**
 *  Product's interface
 */
public interface IProduct {

    String getName();
    Discount discount();
    ProductType getProductType();
}