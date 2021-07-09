import styled from "styled-components/macro";
import { useState } from "react";
import useSignUp from "../hooks/useSignUp";
import {NavLink} from "react-router-dom";

export default function SignUpPage() {
    const {checkUserName, signUp, availability, success} = useSignUp();
    const [error, setError] = useState()
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
        passwordcheck: "",
    });

    const handleChange = (event) => {
        setCredentials({...credentials, [event.target.name]: event.target.value});
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (credentials.password === "" || credentials.passwordcheck === "" || credentials.username === "") {
            setError("Please fill out all fields");
            return;
        }
        if (credentials.password !== credentials.passwordcheck) {
            setError("Passwords do not match");
            return;
        } else {
            checkUserName(credentials.username);
            if (availability) {
                setError("Username is already in use. Please choose another one");
                return;
            }
            signUp(credentials.username, credentials.password)
        }
    };

    return (
        <div>
            <h2>Enter your details to sign up!</h2>
            {error && (!success) && <p>{error}</p>}
            <LoginForm onSubmit={handleSubmit}>
                {(!success) &&
                    <div>
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
                <label htmlFor="passwordcheck">
                    <h3>Reenter password:</h3>
                    <input
                        id="passwordcheck"
                        name="passwordcheck"
                        type="password"
                        placeholder="password"
                        value={credentials.passwordcheck}
                        onChange={handleChange}
                    />
                </label>
                <button>Sign up</button>

                        <div className={"login"}>
                            <h3>You already have an account?</h3>
                            <StyledNavLink to={"/login"}>
                                <button>Log in!</button>
                            </StyledNavLink>
                        </div>
                    </div>
                }
                {success && <div>
                    <p>Sign up successful!</p>
                    <StyledNavLink to={"/login"}>
                        <button className={"btn-login"}>Go to login</button>
                    </StyledNavLink>
                </div>}
            </LoginForm>
        </div>
    );
}

const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
  
  .btn-login{
    width: 50vw;
  }

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

  .login{
    h3{
      margin-top: 8vh;
      margin-button: 3vh;
    }
`;

const StyledNavLink = styled(NavLink)`
text-decoration: none;
color: #eae9f1;

&:focus, &:hover, &:visited, &:link, &:active {
    text-decoration: none;
    color: #eae9f1;

    `;
