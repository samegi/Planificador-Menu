// src/pages/recetas/Recetas.tsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Receta } from '../../api/types';
import { RecetasApi } from '../../api/recetas';

function Recetas() {
  const [recetas, setRecetas] = useState<Receta[]>([]);
  const [loading, setLoading] = useState(true);
  const [busqueda, setBusqueda] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const data = await RecetasApi.listar();
        setRecetas(data);
      } catch (err) {
        console.error('[RecetasPage] Error cargando recetas', err);
        alert('Error cargando recetas.');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const filtradas = recetas.filter((r) =>
    r.nombre.toLowerCase().includes(busqueda.toLowerCase())
  );

  return (
    <div className="page">
      <div className="page-header">
        <h1>Recetas</h1>
        <button
          className="primary"
          onClick={() => navigate('/recetas/nueva')}
        >
          Nueva receta
        </button>
      </div>

      <input
        placeholder="Buscar receta…"
        value={busqueda}
        onChange={(e) => setBusqueda(e.target.value)}
        style={{ marginBottom: 16, width: '100%' }}
      />

      {loading ? (
        <p>Cargando…</p>
      ) : (
        <ul className="list">
          {filtradas.map((r) => (
            <li
              key={r.id}
              className="card clickable"
              onClick={() => navigate(`/recetas/${r.id}`)}
            >
              <div className="title">{r.nombre}</div>
              {r.descripcion && (
                <p className="muted">{r.descripcion}</p>
              )}
            </li>
          ))}

          {!loading && filtradas.length === 0 && (
            <p>No hay recetas.</p>
          )}
        </ul>
      )}
    </div>
  );
}

export default Recetas;
