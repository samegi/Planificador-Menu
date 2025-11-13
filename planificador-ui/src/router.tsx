// src/router.tsx
import { createBrowserRouter } from 'react-router-dom';
import AppLayout from './components/AppLayout';

import Home from './pages/Home';
import Plan from './pages/plan/Plan';
import DiaPage from './pages/plan/Dia';
import Recetas from './pages/recetas/Recetas';
import RecetaItem from './pages/recetas/Item';
import Estadisticas from './pages/Estadisticas';

export const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      { path: '/', element: <Home /> },
      { path: '/plan', element: <Plan /> },
      { path: '/plan/:id', element: <DiaPage /> },

      { path: '/recetas', element: <Recetas /> },

      // 👉 importantísimo: ruta para crear
      { path: '/recetas/nueva', element: <RecetaItem /> },

      // 👉 y luego la de editar/ver por id
      { path: '/recetas/:id', element: <RecetaItem /> },

      { path: '/estadisticas', element: <Estadisticas /> },
    ],
  },
]);
