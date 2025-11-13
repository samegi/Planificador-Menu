import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { RecetasApi } from '../../api/recetas';
import type { Receta } from '../../api/types';

export default function RecetaItem() {
  const navigate = useNavigate();
  const { id } = useParams();
  const recetaId = Number(id);
  const isNew = isNaN(recetaId);

  const [receta, setReceta] = useState<Receta>({
    id: 0,
    nombre: '',
    descripcion: '',
  });

  // Cargar receta cuando NO es nueva
  useEffect(() => {
    if (!isNew) {
      RecetasApi.obtener(recetaId).then(setReceta).catch((err) => {
        console.error('[RecetaItem] Error cargando receta', err);
        alert('Error cargando la receta.');
      });
    }
  }, [isNew, recetaId]);

  // Guardar receta
  async function guardar() {
    if (!receta.nombre.trim()) {
      alert('El nombre es obligatorio');
      return;
    }

    try {
      const payload = {
        nombre: receta.nombre,
        descripcion: receta.descripcion ?? '',
      };

      if (isNew) {
        await RecetasApi.crear(payload);
      } else {
        await RecetasApi.actualizar(recetaId, payload);
      }

      alert('Receta guardada correctamente');
      navigate('/recetas');
    } catch (err) {
      console.error('[RecetaItem] Error guardando receta', err);
      alert('No se pudo guardar la receta. Mira la consola.');
    }
  }

  return (
    <div className="p-4 max-w-3xl mx-auto">
      <button onClick={() => navigate('/recetas')}>&larr; Volver a recetas</button>

      <h2 className="text-2xl font-bold mt-4">
        {isNew ? 'Nueva receta' : 'Editar receta'}
      </h2>

      <div className="mt-6 space-y-4">
        {/* Nombre */}
        <div>
          <label>Nombre</label>
          <input
            type="text"
            className="w-full border p-2 rounded"
            value={receta.nombre}
            onChange={(e) =>
              setReceta({ ...receta, nombre: e.target.value })
            }
          />
        </div>

        {/* Descripción */}
        <div>
          <label>Descripción</label>
          <textarea
            className="w-full border p-2 rounded"
            rows={3}
            value={receta.descripcion ?? ''}
            onChange={(e) =>
              setReceta({ ...receta, descripcion: e.target.value })
            }
          />
        </div>

        <button
          className="px-4 py-2 bg-blue-500 text-white rounded"
          onClick={guardar}
        >
          Guardar
        </button>
      </div>
    </div>
  );
}
