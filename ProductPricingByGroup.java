import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// ---------------------------
//
// HOMEWORK
//
// You can use either Groovy or Java.
//
// Design a routine that will calculate the average Product price per Group.
//
// The Price of each Product is calculated as:
// Cost * (1 + Margin)
//
// Assume there can be a large number of products and a large number of
// categories.
//
// Plus points:
// - use Groovy and its closures
// - make the category look-up performance effective
// - use method Collection.inject

// [Product, Group, Cost]

//def products=[['A','G1',20.1],['B','G2',98.4],['C','G1',49.7],['D','G3',35.8],['E','G3',105.5],['F','G1',55.2],['G','G1',12.7],['H','G3',88.6],['I','G1',5.2],['J','G2',72.4]]

// Category classification based on product Cost
// [Category, Cost range from (inclusive), Cost range to (exclusive)]
// i.e. if a Product has Cost between 0 and 25, it belongs to category C1

//def category=[['C3',50,75],['C4',75,100],['C2',25,50],['C5',100,null],['C1',0,25]]

// Margins for each product Category
// [Category, Margin (either percentage or absolute value)]

//def margins=['C1':'20%','C2':'30%','C3':'0.47','C4':'50%','C5':'0.66']

// ---------------------------
//
// YOUR CODE GOES BELOW THIS LINE
//
// Assign the 'result' variable so the assertion at the end validates
//
// ---------------------------

// ---------------------------

public class ProductPricingByGroup {
    public static void main(String[] arg) {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("A", "G1", 20.1));
        products.add(new Product("B", "G2", 98.4));
        products.add(new Product("C", "G1", 49.7));
        products.add(new Product("D", "G3", 35.8));
        products.add(new Product("E", "G3", 105.5));
        products.add(new Product("F", "G1", 55.2));
        products.add(new Product("G", "G1", 12.7));
        products.add(new Product("H", "G3", 88.6));
        products.add(new Product("I", "G1", 5.2));
        products.add(new Product("J", "G2", 72.4));

        Map<String, String> margins = Map.of("C1", "20%",
                "C2", "30%",
                "C3", "0.47",
                "C4", "50%",
                "C5", "0.66");

        List<Category> category = new ArrayList<Category>();
        category.add(new Category("C3", 50, 75));
        category.add(new Category("C4", 75, 100));
        category.add(new Category("C2", 25, 50));
        category.add(new Category("C5", 100, null));
        category.add(new Category("C1", 0, 25));

        for (int i = 0; i < products.size(); i++) {
            Double productCost = products.get(i).cost;
            Category categoryOfProduct = category.stream()
                    .filter(categoryItem -> productCost >= categoryItem.minCost
                            && (categoryItem.maxCost == null || productCost < categoryItem.maxCost))
                    .findAny()
                    .orElse(null);

            String productMargin = margins.get(categoryOfProduct.catgoryName);
            Double productMarginNew;

            if (productMargin.endsWith("%")) {
                productMarginNew = Double.valueOf(productMargin.substring(0, productMargin.length() - 1)) / 100;
            } else {
                productMarginNew = Double.valueOf(productMargin);
            }
            Double priceForProduct = productCost * (1 + productMarginNew);
            products.get(i).price = priceForProduct;
        }

        Map<String, List<Product>> productsGrouped = products.stream()
                .collect(Collectors.groupingBy(w -> w.groupName));
        Map<String, Double> result = new HashMap<String, Double>();
        productsGrouped.forEach((key, value) -> {
            Double sum = value.stream().map(x -> x.price).reduce(0.0, Double::sum);
            result.put(key, sum / value.size());

        });
        NumberFormat formatter = new DecimalFormat("#0.0");

        assert Double.valueOf(formatter.format(result.get("G1"))) == 38.3 : "It doesn't work";
        assert Double.valueOf(formatter.format(result.get("G2"))) == 127.0 : "It doesn't work";
        assert Double.valueOf(formatter.format(result.get("G3"))) == 118.2 : "It doesn't work";

        System.out.println("It works!");

    }
}

class Product {
    String productName;
    String groupName;
    Double cost;
    Double price;

    Product() {
        productName = "";
        groupName = "";
        cost = 0.0;
        price = 0.0;
    }

    Product(String productName, String groupName, Double cost) {
        this.productName = productName;
        this.groupName = groupName;
        this.cost = cost;
    }

    Product(String productName, String groupName, Double cost, Double price) {
        this.productName = productName;
        this.groupName = groupName;
        this.cost = cost;
        this.price = price;
    }
}

class Category {
    String catgoryName;
    Integer minCost;
    Integer maxCost;

    Category() {
        catgoryName = "";
        minCost = 0;
        maxCost = 0;
    }

    Category(String catgoryName, Integer minCost, Integer maxCost) {
        this.catgoryName = catgoryName;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }
}
