import { useState } from "react";
import Dropdown from "./Dropdown";
import ProductCard from "./ProductCard";
import SearchBox from "./SearchBox";

const sortList = ["Popularity", "Price: Low to High", "Price: High to Low"];

export default function ProductListings({ products }) {
  const [searchText, setSearchText] = useState("");
  const [selectedSort, setSelectedSort] = useState("Popularity");

  function handleSearchChange(inputSearch) {
    setSearchText(inputSearch);
  }

  function handleSortSort(sortType) {
    setSelectedSort(sortType);
  }

  let filteredAndSortedProducts = Array.isArray(products)
    ? products.filter(
        (product) =>
          product.name.toLowerCase().includes(searchText.toLowerCase()) ||
          product.description.toLowerCase().includes(searchText.toLowerCase()),
      )
    : [];

  switch (selectedSort) {
    case "Price: Low to High":
      filteredAndSortedProducts = filteredAndSortedProducts.sort(
        (a, b) => parseFloat(a.price) - parseFloat(b.price),
      );
      break;
    case "Price: High to Low":
      filteredAndSortedProducts = filteredAndSortedProducts.sort(
        (a, b) => parseFloat(b.price) - parseFloat(a.price),
      );
      break;
    case "Popularity":
    default:
      filteredAndSortedProducts = filteredAndSortedProducts.sort(
        (a, b) => parseInt(b.popularity) - parseInt(a.popularity),
      );
      break;
  }

  return (
    <div className="max-w-[1152px] mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-center gap-4 pt-12">
        <SearchBox
          label="Search"
          placeholder="Search products..."
          value={searchText}
          handleSearch={(value) => handleSearchChange(value)}
        />
        <Dropdown
          label="Sort by"
          options={sortList}
          value={selectedSort}
          handleSort={(value) => handleSortSort(value)}
        />
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-y-8 gap-x-6 py-12">
        {filteredAndSortedProducts.length > 0 ? (
          filteredAndSortedProducts.map((product) => (
            <ProductCard key={product.productId} product={product} />
          ))
        ) : (
          <p className="text-center font-primary font-bold text-lg text-primary">
            No products found
          </p>
        )}
      </div>
    </div>
  );
}
