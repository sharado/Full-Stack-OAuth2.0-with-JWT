import { Children, Component } from "react";
import HomePage from "./components/ProtectedRoute/HomePage";
import Login from "./components/LoginWithRedirectSavedInState";
import OAuthButtons from "./components/OAuthButtons";
import Secured from "./components/Secured";

const routes = [
  {
    path: "",
    element: <HomePage />,
    children: [],
  },
  {
    path: "login",
    element: <Login />,
  },
  {
    path: "logout",
    // element: <Logout />
  },
  {
    path: "secured",
    element: <Secured />,
  },
  {
    path: "oauth/pathname/hello/:product/:productId",
    element: <OAuthButtons />,
  },
];

export default routes;
