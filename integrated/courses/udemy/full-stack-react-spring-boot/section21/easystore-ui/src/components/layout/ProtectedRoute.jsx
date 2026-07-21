import { useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import { Outlet, useLocation, useMatches, useNavigate } from "react-router-dom";
import { selectIsAuthenticated } from "../../store/auth-slice";

export default function ProtectedRoute() {
  // const { isAuthenticated } = useAuth();
  const isAuthenticated = useSelector(selectIsAuthenticated);

  const location = useLocation();
  const matches = useMatches();
  const navigate = useNavigate();

  const wasAuthenticated = useRef(isAuthenticated);

  useEffect(() => {
    if (!isAuthenticated) {
      const isSticky = matches.some((match) => match.handle?.sticky);
      const from =
        isSticky && !wasAuthenticated.current ? location.pathname : "/home";

      navigate("/login", { state: { from }, replace: true });
    }
    wasAuthenticated.current = isAuthenticated;
  }, [isAuthenticated, location.pathname, matches, navigate]);

  if (!isAuthenticated) {
    return null;
  }

  return <Outlet />;
}
