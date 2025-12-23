import { useState } from "react";
import products from "../data/products";
import PageHeading from "./PageHeading";
import ProductListings from "./ProductListings";

export default function Home() {
  const [isActive] = useState(() => Math.random() > 0.5);

  // const h1Styling = {
  //   textAlign: "center",
  //   color: isActive ? "black" : "red",
  // };

  return (
    <>
      <h1
        className={`my-heading ${
          isActive ? "primary-color" : "secondary-color"
        }`}
      >
        Demo of Global CSS Scope from Home
      </h1>
      {/* <h1 style={h1Styling}>Demo of Global CSS Scope from Home</h1> */}
      {/* <h1 className="my-heading">Demo of Global CSS Scope from Home</h1> */}
      <div className="home-container">
        <PageHeading title="Explore Eazy Stickers!">
          Add a touch of creativity to your space wih our wide range of fun and
          unique stickers. Perfect for any occasion!
        </PageHeading>
        <ProductListings products={products} />
      </div>
    </>
  );
}
