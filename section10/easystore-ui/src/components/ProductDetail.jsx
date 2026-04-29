import { useLocation, useParams } from "react-router-dom";

export default function ProductDetail() {
  const params = useParams();
  const location = useLocation();

  const product = location.state?.product;
  console.log(product);

  return <div>{product.description}</div>;
}
