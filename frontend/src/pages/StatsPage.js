import useStats from "../hooks/useStats";
import StatsCard from "../components/StatsCard";

export default function StatsPage(){

    const {stats} = useStats();

    return(
        <div>
            <h2>Stats</h2>

            {stats && stats.map((item) =>
                <StatsCard recommendationDetails={item}/>)
            }
                </div>
    )
}