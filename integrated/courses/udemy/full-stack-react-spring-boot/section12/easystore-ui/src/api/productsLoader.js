import apiClient from "./apiClient";

export async function productsLoader() {
  try {
    const response = await apiClient.get("products"); // Axios GET Request
    return response.data;
  } catch (error) {
    throw new Response(
      error.response?.data?.errorMessage ||
        error.message ||
        "Failed to fetch products. Please try again.",
      { status: error.status || 500 },
    );
  }
}
