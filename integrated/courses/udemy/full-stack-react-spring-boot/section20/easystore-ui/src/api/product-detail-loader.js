import apiClient from "./api-client";

export async function productDetailLoader({ params }) {
  try {
    const response = await apiClient.get(`products/${params.productId}`);
    return response.data;
  } catch (error) {
    throw new Response(
      error.response?.data?.errorMessage ||
        error.message ||
        "Failed to fetch product. Please try again.",
      { status: error.response?.status || 500 },
    );
  }
}
