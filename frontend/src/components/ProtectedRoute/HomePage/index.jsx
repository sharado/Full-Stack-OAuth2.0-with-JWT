import { Button, Container } from 'react-bootstrap'
import { Link, Navigate, NavLink, replace, useLocation, useNavigate } from 'react-router'
import Login from '../../LoginWithRedirectSavedInState';
import { useEffect, useState } from 'react';
import axiosInstance from '../../../api/apiUtils';

const HomePage = () => {

  return (
    <>
      <Container fluid className='d-flex flex-column justify-content-center align-items-center' style={{ marginTop: '15%' }}>
        <div className='mb-4' style={{ fontSize: '28px', fontWeight: 'bold' }}>Welcome to the Home Page</div>
        {/* <NavLink to={"/login"} state={pathname} replace>Sign In</NavLink> */}
        {/* if(true)
          <Navigate to="/login" state={{from : pathname}} replace /> */}
        
        {/* {username ?
          (<>
            Welcome, {username}
            <Button onClick={logout} style={{ fontSize: '18px', fontWeight: 'bold', marginTop: '20px' }} variant='outline-danger'>Log Out</Button>
          </>)
          : <Button onClick={() => navigate("/login", { state: pathname })} style={{ fontSize: '18px', fontWeight: 'bold' }} variant='outline-primary'>Sign In</Button>
        } */}
      </Container>
    </>
  )
}

export default HomePage