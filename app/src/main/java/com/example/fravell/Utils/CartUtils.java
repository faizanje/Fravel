package com.example.fravell.Utils;

import com.example.fravell.Models.CartItem;
import com.example.fravell.Models.Product;

public class CartUtils {

    public static void addToCart(Product product, String size, int quantity) {
        CartItem cartItem = new CartItem(product, size, quantity);
        FirebaseUtils.getCartReference()
                .child(product.getId())
                .setValue(cartItem);
    }

    public static void increment(CartItem cartItem) {
        addToCart(cartItem.getProduct(), cartItem.getVariant(), cartItem.getQuantity() + 1);
    }


    public static void decrement(CartItem cartItem) {
        addToCart(cartItem.getProduct(), cartItem.getVariant(), cartItem.getQuantity() - 1);
    }


    public static void removeItem(CartItem cartItem) {
        FirebaseUtils.getCartReference()
                .child(cartItem.getProduct().getId())
                .removeValue();
    }
}
