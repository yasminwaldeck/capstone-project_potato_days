import { useContext } from "react";
import { Route, Redirect } from "react-router-dom";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function PrivateRoute(props) {
  const { token } = useContext(TypeAndAuthContext);

  return token ? <Route {...props} /> : <Redirect to={"/login"} />;
}
