import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,
    headers: {
        "Content-Type": "application/json",
    },
})

/**
 * URLs that must NEVER trigger refresh
 * - login
 * - refresh itself
 * - public identity check
 */
const PUBLIC_URLS = [
    "/api/login",
    "/api/auth/refresh",
    "/api/me",
];

axiosInstance.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;

        if (
            error.response?.status === 401 &&
            !originalRequest._retry &&
            !PUBLIC_URLS.some(url => originalRequest.url?.includes(url))
        ) {
            originalRequest._retry = true;

            try {
                await axiosInstance.post("/api/auth/refresh");
                return axiosInstance(originalRequest);
            } catch (refreshError) {
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);




export default axiosInstance;