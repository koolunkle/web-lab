import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../../store/auth-context";

const adminPaths = ["/admin/orders", "/admin/messages"];

export default function ProtectedRoute() {
  const { isAuthenticated } = useAuth();
  const location = useLocation();
  const from = adminPaths.includes(location.pathname) ? "/home" : location.pathname;

  return isAuthenticated ? (
    <Outlet />
  ) : (
    <Navigate to="/login" state={{ from }} replace />
  );
}
