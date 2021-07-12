import styled from "styled-components/macro";
import { useContext, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import { NavLink } from "react-router-dom";

export default function LoginPage() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const { login } = useContext(TypeAndAuthContext);

  const handleChange = (event) => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    login(credentials);
  };

  return (
    <LoginForm>
      <h2>Please log in!</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="username">
          <h3>Username:</h3>
          <input
            id="username"
            name="username"
            type="text"
            placeholder="username"
            value={credentials.username}
            onChange={handleChange}
          />
        </label>
        <label htmlFor="password">
          <h3>Password:</h3>
          <input
            id="password"
            name="password"
            type="password"
            placeholder="password"
            value={credentials.password}
            onChange={handleChange}
          />
        </label>
        <button>Login</button>
      </form>
      <div className={"signup"}>
        <h3>You don't have an account yet?</h3>
        <StyledNavLink to={"/signup"}>
          <button>Sign Up!</button>
        </StyledNavLink>
      </div>
    </LoginForm>
  );
}

const LoginForm = styled.div`
  display: flex;
  flex-direction: column;

  button {
    margin: 5vh auto auto auto;
    height: 5vh;
    width: 30vw;
    border: none;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 20px;
    text-transform: uppercase;
    text-align: center;
    font-weight: 700;
    outline: none;
    position: relative;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #00916e;
  }

  button:after {
    content: "";
    position: absolute;
    z-index: -1;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
  }

  button:before {
    line-height: 1;
    position: relative;
  }

  button:hover {
    background: #00664e;
  }

  .signup {
    h3 {
      margin-top: 15vh;
      margin-button: 3vh;
    }
  }
`;

const StyledNavLink = styled(NavLink)`
text-decoration: none;
color: #eae9f1;

&:focus, &:hover, &:visited, &:link, &:active {
    text-decoration: none;
    color: #eae9f1;

    `;
