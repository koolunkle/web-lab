import { useEffect, useState } from "react";
import { CartContext } from "./cart-context";

export const CartProvider = ({ children }) => {
  // Initialize cart state from localStorages or as an empty array
  const [cart, setCart] = useState(() => {
    try {
      const storedCart = localStorage.getItem("cart");
      return storedCart ? JSON.parse(storedCart) : [];
    } catch (error) {
      console.error("Failed to parse cart from localStorage:", error);
      return [];
    }
  });

  //   Save cart to localStorage whenever it changes
  useEffect(() => {
    try {
      localStorage.setItem("cart", JSON.stringify(cart));
    } catch (error) {
      console.error("Failed to save cart to localStorage:", error);
    }
  }, [cart]);

  const addToCart = (product, quantity) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find(
        (item) => item.productId === product.productId,
      );

      if (existingItem) {
        // Use map() to create a new ary with updated quantity
        return prevCart.map((item) =>
          item.productId === product.productId
            ? { ...item, quantity: item.quantity + quantity }
            : item,
        );
      }

      // If the product is not in the cart, add it
      return [...prevCart, { ...product, quantity }];
    });
  };

  // Function to remove an item from the cart
  const removeFromCart = (productId) => {
    setCart((prevCart) =>
      prevCart.filter((item) => item.productId !== productId),
    );
  };

  // Calculate total quantity
  const totalQuantity = cart.reduce((acc, item) => acc + item.quantity, 0);

  return (
    <CartContext.Provider
      value={{ cart, setCart, addToCart, removeFromCart, totalQuantity }}
    >
      {children}
    </CartContext.Provider>
  );
};
