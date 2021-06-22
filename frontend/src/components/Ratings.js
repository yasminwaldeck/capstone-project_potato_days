import styled from "styled-components";

export default function Ratings({info}){

    return (
        <>
            <h3>Ratings:</h3>
            {info && (
                <Info>

                    {info.map((rating) => (
                    <div key={rating.source}>
                        <p>{rating.source}</p><p>{rating.value}</p>
                    </div>
                    ))}
                </Info>)}
        </>
    )
}

const Info = styled.div`
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px;
  
`