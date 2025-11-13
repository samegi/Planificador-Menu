export default function Estadisticas(){
  return (
    <div className="max-w-md mx-auto p-6 space-y-4">
      <h2 className="text-2xl font-semibold">Estadísticas</h2>
      <div className="text-gray-600">Placeholder: aquí irán gráficos (recetas más usadas, costo aprox., etc.).</div>
      <div className="grid gap-3">
        <div className="card">
          <div className="font-medium">Top recetas</div>
          <div className="text-sm text-gray-500">Próximamente…</div>
        </div>
        <div className="card">
          <div className="font-medium">Ingredientes frecuentes</div>
          <div className="text-sm text-gray-500">Próximamente…</div>
        </div>
      </div>
    </div>
  );
}
