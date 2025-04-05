import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function MainPage() {
    const navigate = useNavigate();

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) ? navigate("/registration") : navigate("/solarwatch");
    }, [navigate]);

    return (
        <></>
    )
}

export default MainPage;
