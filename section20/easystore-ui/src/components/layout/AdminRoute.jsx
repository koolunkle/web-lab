import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../store/auth-context";

export default function AdminRoute() {
  const { user } = useAuth();
  const isAdmin = user?.roles?.includes("ROLE_ADMIN");

  return isAdmin ? <Outlet /> : <Navigate to="/home" replace />;
}
