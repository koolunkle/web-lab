import { redirect } from "react-router-dom";
import apiClient from "./api-client";

export async function adminOrdersLoader() {
  const user = JSON.parse(localStorage.getItem("user"));
  if (!user?.roles?.includes("ROLE_ADMIN")) {
    return redirect("/home");
  }

  try {
    const response = await apiClient.get("/admin/orders"); // Axios GET Request
    return response.data;
  } catch (error) {
    throw new Response(
      error.response?.data?.errorMessage ||
        error.message ||
        "Failed to fetch orders. Please try again.",
      { status: error.status || 500 },
    );
  }
}
