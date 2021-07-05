import useStats from "../hooks/useStats";
import StatsCard from "../components/StatsCard";
import LoadingSpinner from "../components/LoadingSpinner";

export default function StatsPage() {
  const { stats, isLoading } = useStats();

  return (
    <div>
      <h2>Stats</h2>
      {isLoading && <LoadingSpinner />}
      {stats && stats.map((item) => <StatsCard recommendationDetails={item} />)}
    </div>
  );
}
