import { useEffect, useState } from "react";
import apiClient from "../api/apiClient";
import PageHeading from "./PageHeading";
import ProductListings from "./ProductListings";

// Hooks
export default function Home() {
  const [products, setProducts] = useState([]);

  // Run once when the component mounts
  // Mounting is th process of creating and adding the component into DOM
  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    const response = await apiClient.get("products"); // Axios GET Request
    setProducts(response.data); // Update products state with fetched data
  };

  return (
    <div className="max-w-[1152px] mx-auto px-6 py-8">
      <PageHeading title="Explore Eazy Stickers!">
        Add a touch of creativity to your space wih our wide range of fun and
        unique stickers. Perfect for any occasion!
      </PageHeading>
      <ProductListings products={products} />
    </div>
  );
}
