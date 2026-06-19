import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/apiUtils";
import { useLocation, useNavigate } from "react-router";

const Secured = () => {
    const [data, setData] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();

    console.log(data);

    useEffect(() => {
        const fetchSecured = async () => {
            try {
                const res = await axiosInstance.get("/api/secured");
                setData(res.data);
            } catch (err) {
                if (err.response?.status === 401) {
                    navigate("/login", {
                        state: location.pathname,
                        replace: true
                    });
                }
            }
        };

        fetchSecured();
    }, []);



    return <div>{data}</div>;
};

export default Secured;
