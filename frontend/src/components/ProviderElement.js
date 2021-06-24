import styled from "styled-components";

export default function ProviderElement({list}){

    return (
        <Provider>
            {list && list.map((provider) => (
                <div id={"element"} key={provider.provider_name}>
                    <p>{provider.provider_name}</p>
                    <img src={"https://image.tmdb.org/t/p/original" + provider.logo_path} alt={"Logo"}/>
                </div>
            ))}
        </Provider>
    )

}


const Provider = styled.div`
  display: flex;
  flex-wrap: wrap;
 
  #element{
    width: 50%;
    img{
      width: 10vw;
    }
  }

`