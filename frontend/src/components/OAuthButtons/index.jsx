import React from 'react'
import { useLocation, useParams, useSearchParams } from 'react-router'
import { FcGoogle } from 'react-icons/fc';
import { FaGithub } from 'react-icons/fa';
import { Button } from 'react-bootstrap';
import Login from '../LoginWithRedirectSavedInState';

const OAuthButtons = ({ state }) => {

  // routes path   : {baseURL}/oauth/pathname/hello/:product/:productId",
  //url: http://localhost:5173/oauth/pathname/hello/iPhone/13?key1=value1&key2=value2

  /* const { pathname, search } = useLocation();
  console.log('pathname : ', pathname);   //pathname :  /oauth/pathname/hello/iPhone/13
  console.log('search : ', search);       //search :  ?key1=value1&key2=value2

  const params = useParams();
  console.log('product : ', params.product);   //product :  iPohone
  console.log('productId : ', params.productId); //productId :  13

  const [searchParams, setSearchParams] = useSearchParams();

  console.log('key1 : ', searchParams.get("key1"));   //key1 :  value1
  console.log('key2 : ', searchParams.get("key2"));   //key2 :  value2

  const handleChange = (e) => {
    setSearchParams((prev) => {
      const params = new URLSearchParams(prev); // clone
      params.set("key2", e.target.value);       // mutate ONE key
      return params;                            
    });
  }; */

  const BASE_URL = import.meta.env.VITE_BACKEND_URL;
  const redirectTo = state || location.pathname + location.search;

  document.cookie = `redirect_after_login=${encodeURIComponent(redirectTo)}; Max-Age=600; ; path=/; SameSite=Lax`;

  console.log(document.cookie);
  return (
    <>
      {/* <div>OAuthButtons</div> */}

      {/* <input type='text' onChange={handleChange}></input> */}

      <div className="d-grid gap-2">
        <Button
          as="a"
          href={`${BASE_URL}/oauth2/authorization/google`}
          variant='outline-secondary'
          className="d-flex align-items-center justify-content-center gap-2"
          style={{ minWidth: '240px' }}
        >
          {<FcGoogle size={20} />}
          <span>Google</span>
        </Button>

        <Button
          as="a"
          href={`${BASE_URL}/oauth2/authorization/github`}
          variant='dark'
          className="d-flex align-items-center justify-content-center gap-2"
          style={{ minWidth: '240px' }}
        >
          {<FaGithub size={20} />}
          <span>GitHub</span>
        </Button>

      </div>
    </>
  )
}

export default OAuthButtons