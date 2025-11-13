// src/pages/recetas/Item.tsx
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { RecetasApi } from '../../api/recetas';
import type { Receta } from '../../api/types';

export default function RecetaItem() {
  const { id } = useParams();
  const recetaId = Number(id);
  const nav = useNavigate();

  const [receta, setReceta] = useState<Receta | null>(null);
  const [loading, setLoading] = useState(true);

  // ==============================
  // CARGAR RECETA
  // ==============================
  useEffect(() => {
    if (!recetaId) return;

    RecetasApi.obtener(recetaId)
      .then(setReceta)
      .catch((err) => {
        console.error('[RecetaItem] Error cargando receta', err);
        alert('No se pudo cargar la receta.');
      })
      .finally(() => setLoading(false));
  }, [recetaId]);

  // ==============================
  // ACTUALIZAR CAMPOS
  // ==============================
  const updateField = (field: keyof Receta, value: string) =>
    setReceta((prev) => (prev ? { ...prev, [field]: value } : prev));

  // ==============================
  // GUARDAR CAMBIOS
  // ==============================
  const guardar = async () => {
    if (!receta || !recetaId) return;

    try {
      await RecetasApi.actualizar(recetaId, {
        nombre: receta.nombre,
        descripcion: receta.descripcion ?? '',
      });

      alert('Receta guardada');
    } catch (err) {
      console.error('[RecetaItem] Error guardando receta', err);
      alert('No se pudo guardar la receta.');
    }
  };

  // ==============================
  // ELIMINAR
  // ==============================
  const eliminar = async () => {
    if (!recetaId) return;
    if (!confirm('¿Eliminar esta receta?')) return;

    try {
      await RecetasApi.eliminar(recetaId);
      nav('/recetas');
    } catch (err) {
      console.error('[RecetaItem] Error eliminando receta', err);
      alert('No se pudo eliminar la receta.');
    }
  };

  if (loading || !receta) {
    return <div className="p-4">Cargando receta…</div>;
  }

  return (
    <div className="p-4 space-y-4 max-w-3xl mx-auto">
      <button className="text-sm underline" onClick={() => nav('/recetas')}>
        ← Volver al catálogo
      </button>

      <h2 className="text-3xl font-semibold">Editar receta</h2>

      {/* Nombre */}
      <div className="space-y-2">
        <label className="block text-sm font-medium">Nombre</label>
        <input
          className="w-full border rounded px-3 py-2"
          value={receta.nombre}
          onChange={(e) => updateField('nombre', e.target.value)}
        />
      </div>

      {/* Descripción */}
      <div className="space-y-2">
        <label className="block text-sm font-medium">Descripción</label>
        <textarea
          className="w-full border rounded px-3 py-2 min-h-[80px]"
          value={receta.descripcion ?? ''}
          onChange={(e) => updateField('descripcion', e.target.value)}
        />
      </div>

      <div className="flex gap-3">
        <button
          onClick={guardar}
          className="px-5 py-2 rounded bg-purple-600 text-white"
        >
          Guardar
        </button>

        <button
          onClick={eliminar}
          className="px-5 py-2 rounded border border-red-300 text-red-700"
        >
          Eliminar
        </button>
      </div>
    </div>
  );
}
