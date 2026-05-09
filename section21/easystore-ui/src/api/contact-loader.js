import apiClient from "./api-client";

export async function contactLoader() {
  try {
    const response = await apiClient.get("/contacts"); // Axios GET Request
    return response.data;
  } catch (error) {
    throw new Response(
      error.response?.data?.errorMessage ||
        error.message ||
        "Failed to fetch profile details. Please try again.",
      { status: error.status || 500 },
    );
  }
}
