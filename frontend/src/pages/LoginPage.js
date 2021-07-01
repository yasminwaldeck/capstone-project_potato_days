import styled from "styled-components/macro";
import {useContext, useState} from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";


export default function LoginPage(){

    const [credentials, setCredentials] = useState({username: '', password: ''})
    const { login } = useContext(TypeAndAuthContext)

    const handleChange = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value})
    }

    const handleSubmit = event => {
        event.preventDefault()
        login(credentials)
    }

    return (
        <div>
            <h2>Please log in!</h2>
            <LoginForm onSubmit={handleSubmit}>
                <label htmlFor="username">
                    <h3>Username:</h3>
                    <input id="username" name="username" type="text" placeholder="username" value={credentials.username} onChange={handleChange}/>
                </label>
                <label htmlFor="password">
                    <h3>Password:</h3>
                    <input id="password" name="password" type="password" placeholder="password" value={credentials.password} onChange={handleChange}/>
                </label>
                <button>Login</button>
            </LoginForm>
        </div>
    )
}

const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
  
  button{
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
    background: #00916E;
  }

  button:after {
    content: '';
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
    background: #00664E;
  }
`