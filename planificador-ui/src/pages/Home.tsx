import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { CalendarDays, BookOpen, BarChart3 } from 'lucide-react';

export default function Home() {
  const nav = useNavigate();

  return (
    <div className="max-w-md mx-auto p-6 space-y-8">
      {/* Encabezado */}
      <div className="text-center space-y-2">
        <h2 className="text-3xl font-bold text-gray-800">Inicio</h2>
        <p className="text-gray-600">
          Bienvenido. Usa las opciones para planificar tus comidas, explorar recetas y ver estadísticas.
        </p>
      </div>

      
      {/* Tarjetas principales con animación */}
      <div className="grid gap-4">
        <motion.div
          whileHover={{ scale: 1.03 }}
          whileTap={{ scale: 0.98 }}
          onClick={() => nav('/plan')}
          className="cursor-pointer p-5 rounded-2xl shadow-md bg-gradient-to-r from-purple-600 to-purple-500 text-white flex items-center gap-4 hover:shadow-lg transition"
        >
          <CalendarDays className="w-10 h-10" />
          <div>
            <div className="text-lg font-semibold">Plan Semanal</div>
            <div className="text-sm opacity-80">Revisa o ajusta tus comidas de la semana</div>
          </div>
        </motion.div>

        <motion.div
          whileHover={{ scale: 1.03 }}
          whileTap={{ scale: 0.98 }}
          onClick={() => nav('/recetas')}
          className="cursor-pointer p-5 rounded-2xl shadow-md bg-gradient-to-r from-pink-500 to-pink-400 text-white flex items-center gap-4 hover:shadow-lg transition"
        >
          <BookOpen className="w-10 h-10" />
          <div>
            <div className="text-lg font-semibold">Catálogo de Recetas</div>
            <div className="text-sm opacity-80">Explora ideas nuevas para tus comidas favoritas</div>
          </div>
        </motion.div>

        {/* IMPORTANTE: mismo path que la pestaña de abajo */}
        <motion.div
          whileHover={{ scale: 1.03 }}
          whileTap={{ scale: 0.98 }}
          onClick={() => nav('/estadisticas')}
          className="cursor-pointer p-5 rounded-2xl shadow-md bg-gradient-to-r from-blue-500 to-blue-400 text-white flex items-center gap-4 hover:shadow-lg transition"
        >
          <BarChart3 className="w-10 h-10" />
          <div>
            <div className="text-lg font-semibold">Estadísticas</div>
            <div className="text-sm opacity-80">Analiza tus hábitos y progreso</div>
          </div>
        </motion.div>
      </div>
    </div>
  );
}
