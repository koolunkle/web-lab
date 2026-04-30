import { createContext } from "react";

const initialCartContext = {
  cart: [],
  setCart: () => {},
  addToCart: () => {
    console.log("Product added to cart");
  },
  removeFromCart: () => {},
  totalQuantity: 0,
};

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  return (
    <CartContext.Provider value={initialCartContext}>
      {children}
    </CartContext.Provider>
  );
};
