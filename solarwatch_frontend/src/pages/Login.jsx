import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import UserForm from "../components/UserForm";
import backgroundImg from "../assets/login_bg.jpg";

function Login() {
    const navigate = useNavigate();

    const [nameErrorMsg, setNameErrorMsg] = useState("");
    const [passwordErrorMsg, setPasswordErrorMsg] = useState("");

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) || navigate("/solarwatch");
    }, [navigate]);

    async function onSubmit(event, loginObj) {
        event.preventDefault();

        setNameErrorMsg("");
        setPasswordErrorMsg("");

        const response = await fetch("api/login", {
                method: "POST", 
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginObj)
            }
        );

        if (response.status === 401) {
            setNameErrorMsg("Incorrect username or password!");
            setPasswordErrorMsg("Incorrect username or password!");
        } else if (response.status === 200) {
            const responseBody = await response.json();
            localStorage.setItem("solarWatchJwt", responseBody.jwt);
            navigate("/solarwatch")
        }
    }

    return (
        <div className={`bg-cover flex min-h-screen overflow-hidden items-center justify-center`} style={{ backgroundImage: `url(${backgroundImg})`}}>
            <fieldset className="fieldset w-xs bg-base-200 border border-base-300 p-4 rounded-box">
                <div>
                    <legend className="fieldset-legend text-2xl">Login</legend>
                    <UserForm 
                        submitText={"Log in"}
                        onSubmit={onSubmit}
                        nameErrorMsg={nameErrorMsg}
                        passwordErrorMsg={passwordErrorMsg}                  
                    />  
                </div>
                <Link className="text text-secondary my-1" to="/registration">You doesn't have an account?</Link>
            </fieldset>
        </div>
    )
}

export default Login;
