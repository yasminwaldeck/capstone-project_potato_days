import axios from "axios";
import { useState } from "react";

export default function useSignUp() {
  const [availability, setAvailability] = useState(false);
  const [success, setSuccess] = useState(false);

  const checkUserName = (username) => {
    axios
      .get("/auth/signup/checkusername?username=" + username)
      .then((response) => response.data)
      .then(setAvailability)
      .catch((error) => console.error(error.message));
  };

  const signUp = (username, password) => {
    axios
      .post("/auth/signup", { username, password })
      .then((response) => response.data)
      .then(setSuccess)
      .catch((error) => console.error(error.message));
  };

  return { checkUserName, signUp, availability, success };
}
