import apiClient from "./apiClient";

export async function contactAction({ request }) {
  const data = await request.formData();
  const contactData = {
    name: data.get("name"),
    email: data.get("email"),
    mobileNumber: data.get("mobileNumber"),
    message: data.get("message"),
  };

  try {
    await apiClient.post("/contacts", contactData);
    return { success: true };
    // return redirect("/home");
  } catch (error) {
    throw new Response(
      error.message || "Failed to submit your message. Please try again.",
      { status: error.status || 500 },
    );
  }
}
