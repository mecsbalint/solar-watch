import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

function Registration() {
    const navigate = useNavigate();
    
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [nameErrorMsg, setNameErrorMsg] = useState("");

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) || navigate("/solarwatch");
    }, [navigate]);

    async function onSubmit(e) {
        e.preventDefault();

        const registrationObj = {
            name: name,
            password: password
        }

        const response = await fetch("api/registration", {
                method: "POST", 
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(registrationObj)
            }
        );

        if (response.status === 409) {
            setName("");
            setNameErrorMsg("This username is already taken!")
        } else if (response.status === 201) {
            navigate("/login")
        }
    }

    return (
        <fieldset className="fieldset w-xs bg-base-200 border border-base-300 p-4 rounded-box">
            <form onSubmit={e => onSubmit(e)}>
                <legend className="fieldset-legend text-2xl">Registration</legend>

                <div className="my-5">
                    <label className="fieldset-label">Name</label>
                    <input type="text" className={`input ${nameErrorMsg && "input-error"}`} placeholder="username" onChange={event => {
                        setName(event.target.value);
                        setNameErrorMsg("");
                        }} 
                        value={name} />
                    <p className="text-error">{nameErrorMsg}</p>
                </div>

                <div className="my-5">
                    <label className="fieldset-label">Password</label>
                    <input type="password" className="input" placeholder="password" onChange={event => setPassword(event.target.value)} value={password}/>
                </div>

                <button type="submit" className="btn btn-neutral mt-4">Sign Up</button>
            </form>
            <Link className="text text-secondary my-1" to="/login">You have already an account?</Link>
        </fieldset>
    );
}

export default Registration;
