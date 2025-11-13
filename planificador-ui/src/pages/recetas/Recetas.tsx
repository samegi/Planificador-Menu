// src/pages/recetas/Recetas.tsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { RecetasApi } from '../../api/recetas';
import type { Receta } from '../../api/types';

export default function RecetasPage() {
  const [lista, setLista] = useState<Receta[]>([]);
  const [q, setQ] = useState('');
  const nav = useNavigate();

  const load = () => RecetasApi.listar().then(setLista);

  useEffect(() => {
    load().catch((err) => {
      console.error('[RecetasPage] Error cargando recetas', err);
      alert('No se pudieron cargar las recetas. Mira la consola.');
    });
  }, []);

  const crear = async () => {
    const nombre = prompt('Nombre de la receta');
    if (!nombre) return;
    try {
      const r = await RecetasApi.crear({ nombre, descripcion: '' });
      await load();
      nav(`/recetas/${r.id}`);
    } catch (err) {
      console.error('[RecetasPage] Error creando receta', err);
      alert('No se pudo crear la receta. Mira la consola.');
    }
  };

  const filtrada =
    q.trim() === ''
      ? lista
      : lista.filter((r) =>
          r.nombre.toLowerCase().includes(q.toLowerCase()),
        );

  return (
    <div className="p-4 space-y-4">
      <div className="flex gap-2">
        <input
          value={q}
          onChange={(e) => setQ(e.target.value)}
          placeholder="Buscar receta…"
          className="border rounded px-3 py-2 flex-1"
        />
        <button
          onClick={crear}
          className="px-4 py-2 rounded bg-purple-600 text-white"
        >
          Nueva
        </button>
      </div>

      <ul className="grid gap-3">
        {filtrada.map((r) => (
          <li
            key={r.id}
            onClick={() => nav(`/recetas/${r.id}`)}
            className="p-3 rounded border hover:bg-purple-50 cursor-pointer"
          >
            <div className="font-semibold">{r.nombre}</div>
            <div className="text-sm text-gray-600">
              {r.descripcion || '—'}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
