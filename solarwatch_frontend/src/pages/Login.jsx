import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
    const navigate = useNavigate();

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) || navigate("/solarwatch");
    }, [navigate]);

    return (
        <h1>LOGIN</h1>
    )
}

export default Login;
