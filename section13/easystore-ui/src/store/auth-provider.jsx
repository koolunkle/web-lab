import { useCallback, useEffect, useReducer } from "react";
import { AuthContext } from "./auth-context";

// Action types
const LOGIN_SUCCESS = "LOGIN_SUCCESS";
const LOGOUT = "LOGOUT";

const authReducer = (prevState, action) => {
  switch (action.type) {
    case LOGIN_SUCCESS:
      return {
        ...prevState,
        jwtToken: action.payload.jwtToken,
        user: action.payload.user,
        isAuthenticated: true,
      };
    case LOGOUT:
      return {
        ...prevState,
        jwtToken: null,
        user: null,
        isAuthenticated: false,
      };
    default:
      return prevState;
  }
};

export const AuthProvider = ({ children }) => {
  const initialAuthState = (() => {
    try {
      const jwtToken = localStorage.getItem("jwtToken");
      const user = localStorage.getItem("user");

      if (jwtToken && user) {
        return {
          jwtToken,
          user: JSON.parse(user),
          isAuthenticated: true,
        };
      }
    } catch (error) {
      console.error("Failed to parse auth from localstorage", error);
    }
    return {
      jwtToken: null,
      user: null,
      isAuthenticated: false,
    };
  })();

  const [auth, dispatch] = useReducer(authReducer, initialAuthState);

  useEffect(() => {
    try {
      if (auth.isAuthenticated) {
        localStorage.setItem("jwtToken", auth.jwtToken);
        localStorage.setItem("user", JSON.stringify(auth.user));
      } else {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("user");
      }
    } catch (error) {
      console.error("Failed to save to localStorage", error);
    }
  }, [auth]);

  // Action creators
  const loginSuccess = useCallback((jwtToken, user) => {
    dispatch({ type: LOGIN_SUCCESS, payload: { jwtToken, user } });
  }, []);

  const logout = useCallback(() => {
    dispatch({ type: LOGOUT });
  }, []);

  return (
    <AuthContext.Provider
      value={{
        jwtToken: auth.jwtToken,
        user: auth.user,
        isAuthenticated: auth.isAuthenticated,
        loginSuccess,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
