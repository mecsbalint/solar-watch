import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import CitySearchForm from "../components/CitySearchForm";
import SunriseSunsetDisplay from "../components/SunriseSunsetDisplay";

function Solarwatch() {
    const navigate = useNavigate();

    const [sunriseSunsetData, setSunriseSunsetData] = useState(null);
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        [null, "null"].includes(localStorage.getItem("solarWatchJwt")) && navigate("/login");
    }, [navigate]);

    async function submitForm(event, submitObj) {
        event.preventDefault();

        setErrorMsg("");

        const response = await fetch(`/api/solarwatch?city=${submitObj.city}`, {headers: {Authorization: `Bearer ${localStorage.getItem("solarWatchJwt")}`}});

        switch (response.status) {
            case 401:
                localStorage.setItem("solarWatchJwt", "null");
                navigate("/login");
                break;
            case 404:
                setErrorMsg("There is no city with this name");
                setSunriseSunsetData(null);
                break;
            case 200:
                response.json().then(responseBody => setSunriseSunsetData(responseBody));
                break;
        }
    }

    return (
        <>
            <CitySearchForm onSubmit={submitForm} errorMsg={errorMsg}/>
            {sunriseSunsetData && <SunriseSunsetDisplay sunriseSunsetData={sunriseSunsetData}/>}
        </>
    )
}

export default Solarwatch;
