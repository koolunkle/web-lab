import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import { Bounce, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { adminMessagesLoader } from "./api/admin-messages-loader.js";
import { adminOrdersLoader } from "./api/admin-orders-loader.js";
import { contactAction } from "./api/contact-action.js";
import { contactLoader } from "./api/contact-loader.js";
import { loginAction } from "./api/login-action.js";
import { ordersLoader } from "./api/orders-loader.js";
import { productDetailLoader } from "./api/product-detail-loader.js";
import { productsLoader } from "./api/products-loader.js";
import { profileAction } from "./api/profile-action.js";
import { profileLoader } from "./api/profile-loader.js";
import { registerAction } from "./api/register-action.js";
import App from "./App.jsx";
import About from "./components/About.jsx";
import AdminMessages from "./components/admin/AdminMessages.jsx";
import AdminOrders from "./components/admin/AdminOrders.jsx";
import Cart from "./components/Cart.jsx";
import CheckoutForm from "./components/CheckoutForm.jsx";
import Contact from "./components/Contact.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import Home from "./components/Home.jsx";
import HydrateFallback from "./components/HydrateFallback.jsx";
import AdminRoute from "./components/layout/AdminRoute.jsx";
import ProtectedRoute from "./components/layout/ProtectedRoute.jsx";
import Login from "./components/Login.jsx";
import Orders from "./components/Orders.jsx";
import OrderSuccess from "./components/OrderSuccess";
import ProductDetail from "./components/ProductDetail.jsx";
import Profile from "./components/Profile.jsx";
import Register from "./components/Register.jsx";
import "./index.css";
import { AuthProvider } from "./store/auth-provider.jsx";
import store from "./store/store.js";

const stripePromise = loadStripe(
  "pk_test_51TThULJ6lFKdCkWimZSR7tX69JBMJ3erRhJgMmmUfH0MoCO5v0kV9RnyzUiBOC6I8cpTlxm1rJNppkhqXmJwXEJN00C4fzC6Cy",
);

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
    <Route
      path="/contact"
      element={<Contact />}
      action={contactAction}
      loader={contactLoader}
    />
    <Route path="/login" element={<Login />} action={loginAction} />
    <Route path="/register" element={<Register />} action={registerAction} />
    <Route path="/cart" element={<Cart />} />
    <Route
      path="/products/:productId"
      element={<ProductDetail />}
      loader={productDetailLoader}
    />
    <Route element={<ProtectedRoute />}>
      <Route
        path="/checkout"
        element={<CheckoutForm />}
        handle={{ sticky: true }}
      />
      <Route path="/order-success" element={<OrderSuccess />} />
      <Route
        path="/profile"
        element={<Profile />}
        loader={profileLoader}
        action={profileAction}
        shouldRevalidate={({ actionResult }) => {
          return !actionResult?.success;
        }}
      />
      <Route path="/orders" element={<Orders />} loader={ordersLoader} />
      <Route element={<AdminRoute />}>
        <Route
          path="/admin/orders"
          element={<AdminOrders />}
          loader={adminOrdersLoader}
        />
        <Route
          path="/admin/messages"
          element={<AdminMessages />}
          loader={adminMessagesLoader}
        />
      </Route>
    </Route>
  </Route>,
);

const appRouter = createBrowserRouter(routeDefinitions);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <Elements stripe={stripePromise}>
      <AuthProvider>
        {/* <CartProvider>
          <RouterProvider router={appRouter} />
        </CartProvider> */}
        <Provider store={store}>
          <RouterProvider router={appRouter} />
        </Provider>
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
    </Elements>
  </StrictMode>,
);
