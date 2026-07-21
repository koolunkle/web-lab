import { useSelector } from "react-redux";
import { Navigate, Outlet } from "react-router-dom";
import { selectUser } from "../../store/auth-slice";

export default function AdminRoute() {
  // const { user } = useAuth();
  const  user  = useSelector(selectUser);
  const isAdmin = user?.roles?.includes("ROLE_ADMIN");

  return isAdmin ? <Outlet /> : <Navigate to="/home" replace />;
}
