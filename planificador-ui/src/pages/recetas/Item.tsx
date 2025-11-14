// src/pages/recetas/RecetaItem.tsx
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

  const [loading, setLoading] = useState(!isNew);
  const [saving, setSaving] = useState(false);

  // Cargar receta cuando NO es nueva
  useEffect(() => {
    if (!isNew) {
      (async () => {
        try {
          const data = await RecetasApi.obtener(recetaId);
          setReceta(data);
        } catch (err) {
          console.error('[RecetaItem] Error cargando receta', err);
          alert('Error cargando la receta.');
        } finally {
          setLoading(false);
        }
      })();
    }
  }, [isNew, recetaId]);

  // Guardar receta
  async function guardar() {
    if (!receta.nombre.trim()) {
      alert('El nombre es obligatorio');
      return;
    }

    const payload = {
      nombre: receta.nombre,
      descripcion: receta.descripcion ?? '',
    };

    try {
      setSaving(true);

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
    } finally {
      setSaving(false);
    }
  }

  async function eliminar() {
    if (isNew) return;

    const ok = confirm('¿Seguro que quieres eliminar esta receta?');
    if (!ok) return;

    try {
      await RecetasApi.eliminar(recetaId);
      alert('Receta eliminada');
      navigate('/recetas');
    } catch (err) {
      console.error('[RecetaItem] Error eliminando receta', err);
      alert('No se pudo eliminar la receta.');
    }
  }

  if (loading) {
    return (
      <div className="page">
        <div className="page-header">
          <button onClick={() => navigate('/recetas')}>&larr; Volver</button>
          <h1>Cargando receta…</h1>
          <div />
        </div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="page-header">
        <button onClick={() => navigate('/recetas')} className="secondary">
          &larr; Volver
        </button>
        <h1>{isNew ? 'Nueva receta' : 'Editar receta'}</h1>
        <div />
      </div>

      <div className="card" style={{ maxWidth: 600, margin: '0 auto' }}>
        <div className="field">
          <label>Nombre</label>
          <input
            type="text"
            className="w-full border p-2 rounded"
            value={receta.nombre}
            onChange={(e) =>
              setReceta({ ...receta, nombre: e.target.value })
            }
            placeholder="Ej: Ensalada de pollo"
          />
        </div>

        <div className="field" style={{ marginTop: 16 }}>
          <label>Descripción</label>
          <textarea
            className="w-full border p-2 rounded"
            rows={3}
            value={receta.descripcion ?? ''}
            onChange={(e) =>
              setReceta({ ...receta, descripcion: e.target.value })
            }
            placeholder="Descripción corta de la receta…"
          />
        </div>

        <div
          className="actions"
          style={{
            marginTop: 24,
            display: 'flex',
            justifyContent: 'space-between',
            gap: 8,
          }}
        >
          {!isNew && (
            <button
              type="button"
              className="danger"
              onClick={eliminar}
            >
              Eliminar
            </button>
          )}

          <div style={{ marginLeft: 'auto' }}>
            <button
              type="button"
              className="secondary"
              onClick={() => navigate('/recetas')}
              style={{ marginRight: 8 }}
            >
              Cancelar
            </button>
            <button
              type="button"
              className="primary"
              onClick={guardar}
              disabled={saving}
            >
              {saving ? 'Guardando…' : 'Guardar'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
