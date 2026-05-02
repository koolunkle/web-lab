import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import { Bounce, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { contactAction } from "./api/contactAction.js";
import { loginAction } from "./api/loginAction.js";
import { productsLoader } from "./api/productsLoader.js";
import App from "./App.jsx";
import About from "./components/About.jsx";
import Cart from "./components/Cart.jsx";
import CheckoutForm from "./components/CheckoutForm.jsx";
import Contact from "./components/Contact.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import Home from "./components/Home.jsx";
import HydrateFallback from "./components/HydrateFallback.jsx";
import Login from "./components/Login.jsx";
import ProductDetail from "./components/ProductDetail.jsx";
import "./index.css";
import { AuthProvider } from "./store/auth-provider.jsx";
import { CartProvider } from "./store/cart-provider.jsx";

const routeDefinitions = createRoutesFromElements(
  <Route
    path="/"
    element={<App />}
    errorElement={<ErrorPage />}
    HydrateFallback={HydrateFallback}
  >
    <Route index element={<Home />} loader={productsLoader} />
    <Route path="/home" element={<Home />} loader={productsLoader} />
    <Route path="/about" element={<About />} />
    <Route path="/contact" element={<Contact />} action={contactAction} />
    <Route path="/login" element={<Login />} action={loginAction} />
    <Route path="/cart" element={<Cart />} />
    <Route path="/checkout" element={<CheckoutForm />} />
    <Route path="/products/:productId" element={<ProductDetail />} />
  </Route>,
);

const appRouter = createBrowserRouter(routeDefinitions);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <AuthProvider>
      <CartProvider>
        <RouterProvider router={appRouter} />
      </CartProvider>
    </AuthProvider>
    <ToastContainer
      position="top-center"
      autoClose={3000}
      hideProgressBar={false}
      newestOnTop={false}
      draggable
      pauseOnHover
      theme={localStorage.getItem("theme") === "dark" ? "dark" : "light"}
      transition={Bounce}
    />
  </StrictMode>,
);
