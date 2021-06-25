import styled from "styled-components";
import  Portrait from "../resources/Portrait_Placeholder.png"

export default function Crew({crewlist}){

    return(
        <div>
            <h3>Crew:</h3>
            <CrewCard>
                {crewlist.slice(0, 6).map((crew) => (
                    <div key={crew.id}>
                        <p>{crew.name}, {crew.department}</p>
                        {crew.profile_path ? <img src={"https://image.tmdb.org/t/p/w500" + crew.profile_path} alt={"Profile"}/>
                        : <img src={Portrait} alt={"Alternative profile"}/>}
                    </div>
                ))}
            </CrewCard>
        </div>
    )
}

const CrewCard = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 10px;
  align-items: center;
  justify-items: center;
  p{
    height: 8vh
  }
  img{
    width: 20vw;
    min-height: 15vh;
  }
`