import { useParams } from "react-router-dom";

export default function ProductDetail() {
  const params = useParams();
  return <div>{params.productId}</div>;
}
