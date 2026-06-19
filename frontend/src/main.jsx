import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.jsx'
import "react-router";
import { createBrowserRouter, RouterProvider } from 'react-router';
import routes from './routes.jsx';

const router = createBrowserRouter([
  {
    path: "",
    element: <App />,
    children: routes
  }
]
)

createRoot(document.getElementById('root')).render(
  // <StrictMode>
    <RouterProvider router={router} />
  // </StrictMode>
)
