package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *  This class overrides the methods to calculate total basket value after
 *  applying discounts
 */
public class DiscountBasket extends Basket {

    private BigDecimal total;
    private BigDecimal discount;

    public DiscountBasket()
    {
        total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        discount = BigDecimal.ZERO;
    }

    @Override
    public BigDecimal total()
    {
        for (Item item : items())
        {
            if (item instanceof ItemByUnit)
            {
                ItemByUnit itemByUnit = (ItemByUnit) item;
                calculateItemByUnitPrice(itemByUnit);
            }
            else if (item instanceof ItemByWeight)
            {
                ItemByWeight itemByWeight = (ItemByWeight) item;
                calculateItemByWeightPrice(itemByWeight);
            }
        }
        return total;
    }

    private void calculateItemByUnitPrice(ItemByUnit item)
    {
        switch (item.getProduct().discount().getDiscountCode())
        {
            case NO_DISCOUNT:
                total = total.add(item.price());
                break;
            case BUY_1_GET_1:
                discountBuyOneGetOne(item);
                break;
            case BUY_2_FOR_1_POUND:
                buyTwoForOnePound(item);
                break;
            case BUY_3_FOR_2:
                buyThreeItemsForTwo(item);
                break;
            default:
                throw new IllegalArgumentException("discount code not supported");
        }
    }

    private void calculateItemByWeightPrice(ItemByWeight item)
    {
        switch (item.getProduct().discount().getDiscountCode())
        {
            case NO_DISCOUNT:
                total = total.add(item.price());
                break;
            case BUY_1_KG_FOR_HALF_PRICE:
                break;
            default:
                throw new IllegalArgumentException("discount code not supported");
        }
    }

    /**
     * The method calculates the discount on the item and effective price
     * and updates the total and discount values accordingly
     *
     * @param item
     */
    private void discountBuyOneGetOne(ItemByUnit item)
    {
        if (item.getNoOfUnits() == 1)
        {
            this.total = this.total.add(item.price());
        }
        else if (item.getNoOfUnits() % 2 == 0)
        {
            BigDecimal effectivePrice = item.price()
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            this.total = total.add(effectivePrice);
            this.discount = discount.add(effectivePrice);
        }
        else
        {
            BigDecimal effectivePrice = item.getProduct().pricePerUnit()
                    .multiply(BigDecimal.valueOf(item.getNoOfUnits() / 2 + 1));
            this.total = total.add(effectivePrice);
            this.discount = discount.add(item.price().subtract(effectivePrice));
        }
    }

    private void buyTwoForOnePound(ItemByUnit item)
    {
        if (item.getNoOfUnits() == 1)
        {
            total = total.add(item.price());
        }
        else if (item.getNoOfUnits() % 2 == 0)
        {
            BigDecimal effectivePrice = BigDecimal.valueOf(item.getNoOfUnits() / 2);
            total = total.add(effectivePrice);
            discount = discount.add(item.price().subtract(effectivePrice));
        }
        else
        {
            BigDecimal effectivePrice = BigDecimal.valueOf(item.getNoOfUnits() / 2)
                    .add(item.getProduct().pricePerUnit());
            total = total.add(effectivePrice);
            discount = discount.add(item.price().subtract(effectivePrice));
        }
    }

    private void buyThreeItemsForTwo(ItemByUnit item)
    {
        long bundleOfThree = item.getNoOfUnits() / 3;
        long noOfItemsLeft = item.getNoOfUnits() % 3;

        BigDecimal bundlePrice = item.getProduct().pricePerUnit()
                .multiply(BigDecimal.valueOf(bundleOfThree * 2));
        BigDecimal leftItemsPrice = item.getProduct().pricePerUnit()
                .multiply(BigDecimal.valueOf(noOfItemsLeft));
        total = total.add(bundlePrice).add(leftItemsPrice);
        discount = discount.add(item.price()
                .subtract(bundlePrice.add(leftItemsPrice)));
    }

    private void buyOneKgForHalfPrice(ItemByWeight item)
    {

    }
}