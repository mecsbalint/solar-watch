import sunriseIcon from "../assets/sunrise_icon.png";
import sunsetIcon from "../assets/sunset_icon.png";

function SunriseSunsetDisplay({sunriseSunsetData}) {

    return (
            <div className="stats stats-vertical lg:stats-horizontal shadow bg-base-100 ">
                <div className="stat">
                    <div className="stat-figure text-secondary">
                    <div className="avatar online">
                        <div className="w-16 rounded-full">
                        <img src={sunriseIcon} />
                        </div>
                    </div>
                    </div>
                    <div className="stat-title">Sunrise</div>
                    <div className="stat-value text-2xl">{sunriseSunsetData.sunRise}</div>
                </div>

                <div className="stat">
                    <div className="stat-figure text-secondary">
                        <div className="avatar online">
                            <div className="w-16 rounded-full">
                            <img src={sunsetIcon} />
                            </div>
                        </div>
                    </div>
                    <div className="stat-title">Sunset</div>
                    <div className="stat-value text-2xl">{sunriseSunsetData.sunSet}</div>
                </div>
            </div>
    )
}

export default SunriseSunsetDisplay;
