import React, { useState } from 'react'
import { Button, Card, Col, Container, Form, Row } from 'react-bootstrap';
import './styles.scss';
import { Navigate, useLocation, useNavigate } from 'react-router';
import OAuthButtons from '../OAuthButtons';
import axios from 'axios';
import axiosInstance from '../../api/apiUtils';

const Login = () => {

    const { pathname, search, state } = useLocation();
    const navigate = useNavigate();

    console.log("pathname : ", pathname, " search : ", search, "state: ", state);

    const [show, setShow] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const isValid = username && password;

    const redirectTo = state || "/";
    console.log("redirectTo: ", redirectTo);

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("after preventDefault");
        const payload = { username, password };

        try {
            console.log("before axios post");
            const { data, status } = await axiosInstance.post("/api/login", payload);
            console.log(data);
            console.log("after axios post");
            if (status === 200) {
                console.log("Login successful");
                navigate(redirectTo, { state: { username: data?.username }, replace: true });
            }
        } catch (err) {
            console.log("Login unsuccessful");
            console.log(err);
        }

    }

    return (
        <>
            <Container fluid className='d-flex justify-content-center align-items-center' style={{ marginTop: '5%' }}>
                <Form className='d-flex flex-column align-items-center login' style={{ width: '30%', minWidth: '300px', border: '1px solid grey', borderRadius: '25px', padding: '15px' }}>
                    <Form.Text className='text mb-3'>Sign In</Form.Text>
                    <Form.Group className="mb-3" controlId="formBasicEmail" style={{ width: '70%' }}>
                        <Form.Label style={{ fontWeight: '500', color: '#655c5c' }}>Username</Form.Label>
                        <Form.Control onChange={(e) => setUsername(e.target.value)} type="text" placeholder="Enter username" />
                    </Form.Group>

                    <Form.Group className="mb-3 position-relative" controlId="formBasicPassword" style={{ width: '70%' }}>
                        <Form.Label style={{ fontWeight: '500', color: '#655c5c' }}>Password</Form.Label>
                        {/* <Form.Control type="password" placeholder="Password" /> */}

                        <div className="position-relative">
                            <Form.Control
                                onChange={(e) => setPassword(e.target.value)}
                                type={show ? "text" : "password"}
                                placeholder="Password"
                                className='password-input'
                            />

                            <Button onClick={() => setShow(s => !s)} variant='secondary'
                                className="position-absolute end-0 top-50 translate-middle-y p-2 password-toggle"
                                style={{ fontSize: '13px', borderRadius: '5px' }}>
                                {show ? "Hide" : "Show"}
                            </Button>
                        </div>
                    </Form.Group>


                    <Form.Group className="mb-3" controlId="formBasicCheckbox">
                        <Form.Check style={{ fontWeight: '500', color: '#655c5c' }} type="checkbox" label="Remember Me" />
                    </Form.Group>
                    <Button disabled={!isValid} onClick={handleSubmit} variant="primary" type="submit" className='' style={{ width: '45%' }}>
                        Submit
                    </Button>

                    {/* OR Divider */}
                    <div
                        className="d-flex align-items-center my-4"
                        style={{ width: '70%' }}
                    >
                        <div className="flex-grow-1 border-top border-dark opacity-10" />
                        <span className="mx-2 text-muted fw-semibold small">OR</span>
                        <div className="flex-grow-1 border-top border-dark opacity-10" />
                    </div>
                    <OAuthButtons state={redirectTo} />
                </Form>


            </Container>
        </>
    )
}

export default Login