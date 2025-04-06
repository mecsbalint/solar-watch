
function Solarwatch() {

    // localStorage.setItem("solarWatchJwt", "null");

    return (
        <>
            <h1>SOLARWATCH</h1>
            <p>{localStorage.getItem("solarWatchJwt")}</p>
        </>
    )
}

export default Solarwatch;
