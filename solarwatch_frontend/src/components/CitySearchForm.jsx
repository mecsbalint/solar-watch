import { useEffect, useState } from "react";

function CitySearchForm({onSubmit, errorMsg}) {

    const [city, setCity] = useState("");
    const [errorState, setErrorState] = useState(false);

    useEffect(() => {
        errorMsg && setErrorState(true);
    }, [errorMsg]);

    return (
        <form onSubmit={event => onSubmit(event, {city: city})}>
            <label className={`input ${errorState ? "input-error" : ""}`}>
                <svg className="h-[1em] opacity-50" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g strokeLinejoin="round" strokeLinecap="round" strokeWidth="2.5" fill="none" stroke="currentColor"><circle cx="11" cy="11" r="8"></circle><path d="m21 21-4.3-4.3"></path></g></svg>
                <input type="search" className="grow" placeholder="City name" onChange={event => {
                    setCity(event.target.value);
                    setErrorState(false);
                    }} />
            </label>
            <button type="submit" className="btn btn-primary">Search</button>
            <p className={`text ${errorState ? "text-error" : ""}`}>{errorState ? errorMsg : ""}</p>
        </form>
    )
}

export default CitySearchForm;
