package kata.supermarket;

public class Discount {

    private DiscountCode discountCode;
    private ProductType productType;

    public Discount(DiscountCode discountCode, ProductType productType) {
        this.discountCode = discountCode;
        this.productType = productType;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public ProductType getProductGroup() {
        return productType;
    }

    public void setProductGroup(ProductType productType) {
        this.productType = productType;
    }
}