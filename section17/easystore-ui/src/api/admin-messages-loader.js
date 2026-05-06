import apiClient from "./api-client";

export async function adminMessagesLoader() {
  try {
    const response = await apiClient.get("/admin/messages"); // Axios GET Request
    return response.data;
  } catch (error) {
    throw new Response(
      error.response?.data?.errorMessage ||
        error.message ||
        "Failed to fetch messages. Please try again.",
      { status: error.status || 500 },
    );
  }
}
