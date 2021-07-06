import styled from "styled-components/macro";
import Portrait from "../resources/Portrait_Placeholder.png";

export default function Cast({ castlist }) {
  return (
    <>
      <h3>Cast:</h3>
      <CastCard>
        {castlist.slice(0, 12).map((cast) => (
          <div key={cast.id}>
            <p>
              {cast.name} as {cast.character}
            </p>
            {cast.profile_path ? (
              <img
                src={"https://image.tmdb.org/t/p/w500" + cast.profile_path}
                alt={"Profile"}
              />
            ) : (
              <img src={Portrait} alt={"Alternative profile"} />
            )}
          </div>
        ))}
      </CastCard>
    </>
  );
}

const CastCard = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 10px;
  align-items: center;
  justify-items: center;
  p {
    height: 8vh;
  }
  img {
    width: 20vw;
    min-height: 15vh;
  }
`;
