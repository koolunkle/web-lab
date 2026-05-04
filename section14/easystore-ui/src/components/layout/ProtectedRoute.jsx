import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../../store/auth-context";

export default function ProtectedRoute() {
  const { isAuthenticated } = useAuth();
  const location = useLocation();
  const skipRedirect = location.state?.skipRedirect ?? false;
  const from = skipRedirect ? "/home" : location.pathname;

  return isAuthenticated ? (
    <Outlet />
  ) : (
    <Navigate to="/login" state={{ from, skipRedirect }} replace />
  );
}
