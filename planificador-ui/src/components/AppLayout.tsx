import { Outlet, NavLink } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Home, CalendarDays, BookOpen, BarChart3 } from 'lucide-react';

function Tab({
  to,
  label,
  Icon,
}: {
  to: string;
  label: string;
  Icon: React.ComponentType<React.SVGProps<SVGSVGElement>>;
}) {
  return (
    <NavLink to={to} end className="flex-1">
      {({ isActive }) => (
        <motion.div
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.97 }}
          className={[
            'mx-1 flex flex-col items-center justify-center gap-1 py-2 rounded-xl border transition',
            isActive
              ? 'bg-purple-600 text-white border-purple-600 shadow-md'
              : 'bg-white text-gray-600 border-gray-200 hover:bg-gray-50',
          ].join(' ')}
        >
          <Icon className="w-5 h-5" />
          <span className="text-xs font-medium">{label}</span>
        </motion.div>
      )}
    </NavLink>
  );
}

export default function AppLayout() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      {/* Contenido principal */}
      <main className="flex-1 container mx-auto p-4">
        <Outlet />
      </main>

      {/* Barra inferior */}
      <nav className="sticky bottom-0 bg-white border-t shadow-sm">
        <div className="max-w-md mx-auto flex items-center p-2">
          <Tab to="/" label="Inicio" Icon={Home} />
          <Tab to="/plan" label="Plan" Icon={CalendarDays} />
          <Tab to="/recetas" label="Recetas" Icon={BookOpen} />
          <Tab to="/estadisticas" label="Stats" Icon={BarChart3} />
        </div>
      </nav>
    </div>
  );
}
