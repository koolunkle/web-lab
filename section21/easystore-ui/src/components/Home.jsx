import { useLoaderData, useLocation } from "react-router-dom";
import PageHeading from "./PageHeading";
import ProductListings from "./ProductListings";

export default function Home() {
  const products = useLoaderData();
  const location = useLocation();

  const username = location.state?.username || "Guest";
  const path = location.pathname;
  
  console.log(username);
  console.log(path);

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
