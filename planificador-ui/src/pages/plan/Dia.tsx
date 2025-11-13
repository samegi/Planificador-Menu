// src/pages/plan/Dia.tsx
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { PlanApi } from '../../api/plan';
import { RecetasApi } from '../../api/recetas';
import type { Dia, Comida, Receta } from '../../api/types';

export default function DiaPage() {
  const { id } = useParams();
  const diaId = Number(id);
  const nav = useNavigate();

  const [dia, setDia] = useState<Dia | null>(null);
  const [comidas, setComidas] = useState<Comida[]>([]);
  const [recetas, setRecetas] = useState<Receta[]>([]);
  const [selReceta, setSelReceta] = useState<string>('');
  const [hora, setHora] = useState<string>('12:00');

  // edición
  const [editId, setEditId] = useState<number | null>(null);
  const [editRecetaId, setEditRecetaId] = useState<string>('');
  const [editHora, setEditHora] = useState<string>('12:00');

  useEffect(() => {
    if (!diaId) return;
    (async () => {
      try {
        const [d, cs, rs] = await Promise.all([
          PlanApi.getDia(diaId),
          PlanApi.getComidas(diaId),
          RecetasApi.listar(),
        ]);
        setDia(d);
        setComidas(cs);
        setRecetas(rs);
      } catch (err) {
        console.error('[DiaPage] Error cargando datos', err);
        alert('No se pudo cargar la información del día. Mira la consola.');
      }
    })();
  }, [diaId]);

  const recargarComidas = async () => {
    if (!diaId) return;
    const cs = await PlanApi.getComidas(diaId);
    setComidas(cs);
  };

  const fechaRaw =
    (dia as any)?.fecha ??
    (dia as any)?.date ??
    (dia as any)?.fechaISO ??
    (dia as any)?.diaFecha ??
    '';
  const fechaBonita = fechaRaw
    ? new Date(fechaRaw).toLocaleDateString()
    : '—';

  const agregarComida = async () => {
    if (!selReceta || !hora || !diaId) return;
    try {
      await PlanApi.addComida({
        diaId,
        recetaId: Number(selReceta),
        hora,
      });
      setSelReceta('');
      setHora('12:00');
      await recargarComidas();
    } catch (err) {
      console.error('[DiaPage] Error al agregar comida', err);
      alert('No se pudo agregar la comida. Mira la consola.');
    }
  };

  const empezarEditar = (c: Comida) => {
    setEditId(c.id);
    const rid = (c as any).receta?.id ?? '';
    setEditRecetaId(String(rid));
    const hhmm = (c.hora || '12:00').slice(0, 5);
    setEditHora(hhmm);
  };

  const cancelarEditar = () => {
    setEditId(null);
    setEditRecetaId('');
    setEditHora('12:00');
  };

  const guardarEdicion = async () => {
    if (!editId || !editRecetaId || !editHora) return;
    try {
      await PlanApi.updateComida(editId, {
        recetaId: Number(editRecetaId),
        hora: editHora,
      });
      await recargarComidas();
      cancelarEditar();
    } catch (err) {
      console.error('[DiaPage] Error al guardar comida', err);
      alert('No se pudo guardar la comida. Mira la consola.');
    }
  };

  const eliminarComida = async (comidaId: number) => {
    if (!confirm('¿Eliminar esta comida?')) return;
    try {
      await PlanApi.deleteComida(comidaId);
      await recargarComidas();
    } catch (err) {
      console.error('[DiaPage] Error al eliminar comida', err);
      alert('No se pudo eliminar la comida. Mira la consola.');
    }
  };

  if (!dia) return <div className="p-4">Cargando día…</div>;

  return (
    <div className="p-4 space-y-6">
      <button className="text-sm underline" onClick={() => nav('/plan')}>
        ← Volver al plan
      </button>

      <h2 className="text-2xl font-semibold">{fechaBonita}</h2>

      {/* listado de comidas */}
      <div className="space-y-2">
        <h3 className="font-semibold">Comidas del día</h3>
        <ul className="space-y-2">
          {comidas.map((c) => {
            const nombreReceta =
              (c as any).receta?.nombre ?? '—';
            const enEdicion = editId === c.id;

            return (
              <li
                key={c.id}
                className="border rounded p-3 flex justify-between items-center gap-4"
              >
                <div>
                  <div className="font-medium">{nombreReceta}</div>
                  <div className="text-sm opacity-70">
                    Hora: {c.hora || '—'}
                  </div>
                </div>

                <div className="flex gap-2">
                  <button
                    className="px-3 py-1 text-sm border rounded text-blue-700 border-blue-300"
                    onClick={() => empezarEditar(c)}
                  >
                    Editar
                  </button>
                  <button
                    className="px-3 py-1 text-sm border rounded text-red-700 border-red-300"
                    onClick={() => eliminarComida(c.id)}
                  >
                    Eliminar
                  </button>
                </div>

                {enEdicion && (
                  <div className="w-full mt-3 flex flex-col gap-2">
                    <div className="flex gap-2 items-center">
                      <select
                        className="border rounded p-2 flex-1"
                        value={editRecetaId}
                        onChange={(e) =>
                          setEditRecetaId(e.target.value)
                        }
                      >
                        <option value="">
                          Selecciona receta…
                        </option>
                        {recetas.map((r) => (
                          <option key={r.id} value={r.id}>
                            {r.nombre}
                          </option>
                        ))}
                      </select>
                      <input
                        type="time"
                        className="border rounded p-2"
                        value={editHora}
                        onChange={(e) =>
                          setEditHora(e.target.value)
                        }
                      />
                    </div>
                    <div className="flex gap-2">
                      <button
                        className="px-3 py-1 text-sm rounded bg-purple-600 text-white"
                        onClick={guardarEdicion}
                      >
                        Guardar
                      </button>
                      <button
                        className="px-3 py-1 text-sm rounded border"
                        onClick={cancelarEditar}
                      >
                        Cancelar edición
                      </button>
                    </div>
                  </div>
                )}
              </li>
            );
          })}
        </ul>
      </div>

      {/* agregar comida */}
      <div className="space-y-2">
        <h3 className="font-semibold">Agregar comida</h3>
        <div className="flex flex-wrap gap-2 items-center">
          <select
            className="border rounded p-2 min-w-[180px]"
            value={selReceta}
            onChange={(e) => setSelReceta(e.target.value)}
          >
            <option value="">Selecciona receta…</option>
            {recetas.map((r) => (
              <option key={r.id} value={r.id}>
                {r.nombre}
              </option>
            ))}
          </select>

          <input
            type="time"
            className="border rounded p-2"
            value={hora}
            onChange={(e) => setHora(e.target.value)}
          />

          <button
            onClick={agregarComida}
            className="border rounded px-3 py-2 hover:bg-gray-50"
          >
            Añadir
          </button>
        </div>
      </div>
    </div>
  );
}
