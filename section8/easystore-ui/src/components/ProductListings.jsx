import Dropdown from "./Dropdown";
import ProductCard from "./ProductCard";
import SearchBox from "./SearchBox";

const sortList = ["Popularity", "Price: Low to High", "Price: High to Low"];

export default function ProductListings({ products }) {
  return (
    <div className="max-w-[1152px] mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-center gap-4 pt-12">
        <SearchBox label="Search" placeholder="Search products..." value="" />
        <Dropdown label="Sort by" options={sortList} value="Popularity" />
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-y-8 gap-x-6 py-12">
        {products.length > 0 ? (
          products.map((product) => (
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
