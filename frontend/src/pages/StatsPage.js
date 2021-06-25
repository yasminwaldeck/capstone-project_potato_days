import useStats from "../hooks/useStats";

export default function StatsPage(){

    const {stats} = useStats();
    return(
        <div>
            <h2>Stats</h2>
        </div>
    )
}