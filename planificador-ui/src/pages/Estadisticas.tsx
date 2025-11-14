import { useNavigate } from "react-router-dom";

export default function Estadisticas() {
  const navigate = useNavigate();

  return (
    <div className="max-w-md mx-auto p-6 space-y-4">
      <h2 className="text-2xl font-semibold">Estadísticas</h2>
      <div className="text-gray-600">
        Placeholder: aquí irán gráficos (recetas más usadas, costo aprox., etc.).
      </div>

      <div className="grid gap-3">
        {/* 👇 Card modificada */}
        <div
          className="card cursor-pointer"
          onClick={() => navigate("/estadisticas/macronutrientes")}
        >
          <div className="font-medium">Top recetas</div>
          <div className="text-sm text-gray-500">Ver gráfica…</div>
        </div>

        <div className="card">
          <div className="font-medium">Ingredientes frecuentes</div>
          <div className="text-sm text-gray-500">Próximamente…</div>
        </div>
      </div>
    </div>
  );
}
