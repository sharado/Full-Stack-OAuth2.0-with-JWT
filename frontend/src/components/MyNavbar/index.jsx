import { useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { NavLink, useLocation, useNavigate } from 'react-router';
import axiosInstance from '../../api/apiUtils';
import './styles.scss';

const PUBLIC_ROUTES = ["/login", "/register"];

const MyNavbar = () => {

    const [username, setUsername] = useState(null);
    const navigate = useNavigate();

    const { pathname, search, state } = useLocation();

    const logout = async () => {
        try {
            await axiosInstance.post("/api/logout");
            setUsername(null);
            navigate("/");
        } catch (err) {
            console.error("Logout failed", err);
        }
    };

    useEffect(() => {
        // 🚫 Do NOT call /api/me on public pages
        if (PUBLIC_ROUTES.includes(pathname)) {
            setUsername(null);
            return;
        }

        const fetchUserName = async () => {
            try {
                const res = await axiosInstance.get("/api/me");
                console.log(res);
                setUsername(res.data.username);
            } catch (error) {
                console.log(error);
                setUsername(null);
            }
        }
        fetchUserName();
        console.log("useEffect inside HomePage which fetch userName is running");

    }, [pathname]);

    return (
        <Navbar expand="md" className="bg-dark text-white" variant='dark'>
            <Container fluid>
                <Navbar.Brand as={NavLink} to={"/"}>Life Essentials</Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="me-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                        <Nav.Link as={NavLink} to={"/"}>Home</Nav.Link>
                        <Nav.Link as={NavLink} to={"/secured"}>Secured</Nav.Link>
                        <NavDropdown title="Link" id="navbarScrollingDropdown">
                            <NavDropdown.Item href="#action3">Action</NavDropdown.Item>
                            <NavDropdown.Item href="#action4">
                                Another action
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action5">
                                Something else here
                            </NavDropdown.Item>
                        </NavDropdown>
                        <Nav.Link href="#" disabled>
                            Link
                        </Nav.Link>
                    </Nav>
                    <Form.Control
                        type="search"
                        placeholder="Search"
                        className="me-2"
                        aria-label="Search"
                        style={{ maxWidth: '220px' }}
                    />
                    <Button variant="success" className='me-5'>Search</Button>

                    {username ? (
                        <NavDropdown
                            className='no-arrow me-3'
                            align="end"
                            title={
                                <div
                                    style={{
                                        width: "35px",
                                        height: "35px",
                                        borderRadius: "50%",
                                        backgroundColor: "#ffeeee",
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                        color: "#000000",
                                        fontWeight: "bold",
                                        cursor: "pointer",
                                    }}
                                >
                                    {username.charAt(0).toUpperCase()}
                                </div>
                            }
                            id="profile-dropdown"
                        >
                            <NavDropdown.Item disabled>
                                Signed in as <strong>{username}</strong>
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item onClick={logout}>
                                Logout
                            </NavDropdown.Item>
                        </NavDropdown>
                    ) : (
                        <>
                            <Button
                                className='me-2'
                                variant="outline-light"
                                onClick={() => navigate("/login")}
                                style={{ minWidth: '74px' }}
                            >
                                Sign In
                            </Button>
                            <Button
                                variant="outline-light"
                                onClick={() => navigate("/register")}
                                style={{ minWidth: '81px' }}
                            >
                                Sign Up
                            </Button>
                        </>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default MyNavbar