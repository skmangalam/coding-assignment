package kata.supermarket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    private static Discount vegetableDiscount;
    private static Discount buy1Get1Discount;
    private static Discount buy2For1PoundDiscount;
    private static Discount buy3itemsFor2Discount;
    private static Discount noDiscount;

    @BeforeAll
    public static void setup()
    {
        vegetableDiscount = new Discount(DiscountCode.BUY_1_KG_FOR_HALF_PRICE, ProductType.VEGETABLES);
        buy1Get1Discount = new Discount(DiscountCode.BUY_1_GET_1, ProductType.INDIVIDUAL);
        buy2For1PoundDiscount = new Discount(DiscountCode.BUY_2_FOR_1_POUND, ProductType.INDIVIDUAL);
        buy3itemsFor2Discount = new Discount(DiscountCode.BUY_3_FOR_2, ProductType.INDIVIDUAL);
        noDiscount = new Discount(DiscountCode.NO_DISCOUNT, ProductType.INDIVIDUAL);
    }

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new DiscountBasket();
        items.forEach(basket::add);
        System.out.println("expected total : "+expectedTotal);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                fourItemsPricedPerUnit(),
                multipleItemsWithBuy3ForTwoDiscount()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), twoPintsOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(twoPintsOfMilk()));
    }

    private static Arguments fourItemsPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "2.00", Collections.singleton(fourPackOfDigestives()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product(new BigDecimal("0.49"), buy1Get1Discount, ProductType.INDIVIDUAL, "milk").oneOf();
    }

    private static Arguments multipleItemsWithBuy3ForTwoDiscount() {
        return Arguments.of("pens with buy 3 for 2 price", "8.00", Collections.singleton(multiplePens()));
    }

    private static Item multiplePens()
    {
        return new ItemByUnit(new Product(new BigDecimal("2.00"), buy3itemsFor2Discount, ProductType.INDIVIDUAL, "pens"), 5);
    }
    private static Item twoPintsOfMilk() {
        return new ItemByUnit(new Product(new BigDecimal("0.49"), buy1Get1Discount, ProductType.INDIVIDUAL, "milk"), 2);
    }

    private static Item aPackOfDigestives() {
        return new Product(new BigDecimal("1.55"), buy2For1PoundDiscount, ProductType.INDIVIDUAL, "digestive").oneOf();
    }

    private static Item fourPackOfDigestives() {
        return new ItemByUnit(new Product(new BigDecimal("0.60"), buy2For1PoundDiscount, ProductType.INDIVIDUAL, "digestive"), 4);
    }

    private static WeighedProduct aKiloOfAmericanSweets()
    {
        return new WeighedProduct(new BigDecimal("4.99"), noDiscount,
                "american sweets", ProductType.INDIVIDUAL);
    }

    private static WeighedProduct aKiloOfMushrooms()
    {
        return new WeighedProduct(new BigDecimal("4.99"), vegetableDiscount,
                "mushrooms", ProductType.VEGETABLES);
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"), noDiscount,
                "pick and mix", ProductType.INDIVIDUAL);
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}