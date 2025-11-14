// src/router.tsx
import { createBrowserRouter } from 'react-router-dom';
import AppLayout from './components/AppLayout';

import Home from './pages/Home';
import Plan from './pages/plan/Plan';
import DiaPage from './pages/plan/Dia';

// 📂 Recetas
import Recetas from './pages/recetas/Recetas';
import RecetaItem from './pages/recetas/Item';

// 📊 Stats
import Estadisticas from './pages/Estadisticas';
import StatsMacronutrientes from './pages/statsMacro/StatsMacronutrientes';

export const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      { path: '/', element: <Home /> },

      { path: '/plan', element: <Plan /> },
      { path: '/plan/:id', element: <DiaPage /> },

      // 👉 CATALOGO DE RECETAS
      { path: '/recetas', element: <Recetas /> },

      // 👉 NUEVA RECETA
      { path: '/recetas/nueva', element: <RecetaItem /> },

      // 👉 EDITAR RECETA
      { path: '/recetas/:id', element: <RecetaItem /> },

      // 👉 STATS
      { path: '/estadisticas', element: <Estadisticas /> },
      { path: '/estadisticas/macronutrientes', element: <StatsMacronutrientes /> },
    ],
  },
]);
