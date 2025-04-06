import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import UserForm from "../components/UserForm";
import backgroundImg from "../assets/registration_bg.jpg";

function Registration() {
    const navigate = useNavigate();

    const [nameErrorMsg, setNameErrorMsg] = useState("");

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) || navigate("/solarwatch");
    }, [navigate]);

    async function onSubmit(event, registrationObj) {
        event.preventDefault();

        setNameErrorMsg("");

        const response = await fetch("api/registration", {
                method: "POST", 
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(registrationObj)
            }
        );

        if (response.status === 409) {
            setNameErrorMsg("This username is already taken!")
        } else if (response.status === 201) {
            navigate("/login")
        }
    }

    return (
        <div className={`bg-cover flex min-h-screen overflow-hidden items-center justify-center`} style={{ backgroundImage: `url(${backgroundImg})`}}>
            <fieldset className="fieldset w-xs bg-base-200 border border-base-300 p-4 rounded-box">
                <div>
                    <legend className="fieldset-legend text-2xl">Registration</legend>
                    <UserForm 
                        submitText={"Sign up"}
                        onSubmit={onSubmit}
                        nameErrorMsg={nameErrorMsg}
                        passwordErrorMsg={""}                 
                    />  
                </div>
                <Link className="text text-secondary my-1" to="/login">Do you have already an account?</Link>
            </fieldset>
        </div>
    );
}

export default Registration;
