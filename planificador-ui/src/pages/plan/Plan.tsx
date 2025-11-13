// src/pages/plan/Plan.tsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PlanApi } from '../../api/plan';
import type { Dia } from '../../api/types';

// --- utils fecha ---
const addDays = (d: Date, n: number) => {
  const x = new Date(d);
  x.setDate(x.getDate() + n);
  return x;
};

const ymd = (d: Date) =>
  `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(
    d.getDate(),
  ).padStart(2, '0')}`;

const startOfWeekMon = (d: Date) => {
  const x = new Date(d);
  const day = x.getDay(); // 0=Dom
  const diff = day === 0 ? -6 : 1 - day; // lunes como inicio
  return addDays(x, diff);
};

const sameYMD = (a: Date, b: Date) => ymd(a) === ymd(b);

// helpers para leer la fecha que venga del back
function fechaRawDe(d: Dia | (Dia & Record<string, any>)) {
  return (
    (d as any).fecha ??
    (d as any).date ??
    (d as any).fechaISO ??
    (d as any).diaFecha ??
    (d as any).f ??
    null
  );
}

function toDateSafe(s: string | null) {
  if (!s) return null;
  const dt = new Date(s);
  return isNaN(+dt) ? null : dt;
}

export default function Plan() {
  const [dias, setDias] = useState<Dia[]>([]);
  const [anchorDate, setAnchorDate] = useState<Date>(new Date());
  const [loadingKey, setLoadingKey] = useState<string | null>(null);
  const nav = useNavigate();

  // cargar todos los días
  useEffect(() => {
    PlanApi.dias()
      .then(setDias)
      .catch((err) => {
        console.error('[Plan] Error cargando días', err);
        alert('No se pudieron cargar los días. Mira la consola.');
      });
  }, []);

  // index por YYYY-MM-DD
  const diasIndex = new Map<string, Dia>();
  for (const d of dias) {
    const raw = fechaRawDe(d);
    const dt = toDateSafe(raw);
    if (!dt) continue;
    diasIndex.set(ymd(dt), d);
  }

  const weekStart = startOfWeekMon(anchorDate);
  const weekDays = Array.from({ length: 7 }, (_, i) => addDays(weekStart, i));
  const weekEnd = weekDays[6];
  const today = new Date();

  const goPrevWeek = () => setAnchorDate(addDays(weekStart, -7));
  const goNextWeek = () => setAnchorDate(addDays(weekStart, 7));
  const goToday = () => setAnchorDate(new Date());

  // abrir o crear día y luego navegar
  const openOrCreateDia = async (dateObj: Date) => {
    const key = ymd(dateObj);

    try {
      // 1) ya existe
      const existente = diasIndex.get(key);
      if (existente) {
        const id =
          (existente as any).id ??
          (existente as any).ID ??
          (existente as any).Id;
        if (id !== undefined && id !== null) {
          nav(`/plan/${id}`);
          return;
        }
      }

      // 2) crear si no existe
      setLoadingKey(key);
      console.log('[Plan] Creando día…', key);

      const creado = await PlanApi.createDia(key); // <--- fecha como string "YYYY-MM-DD"

      let targetId =
        (creado as any).id ??
        (creado as any).ID ??
        (creado as any).Id ??
        null;

      // 3) por si acaso: recargar y buscar por fecha
      if (targetId == null) {
        const nuevos = await PlanApi.dias();
        setDias(nuevos);
        const match = nuevos.find((d: any) => {
          const raw = fechaRawDe(d);
          const dt = toDateSafe(raw);
          return dt && ymd(dt) === key;
        });
        targetId =
          match &&
          ((match as any).id ?? (match as any).ID ?? (match as any).Id);
      }

      if (targetId == null) {
        console.error('[Plan] No pude resolver ID tras crear día', {
          key,
          creado,
        });
        alert(
          'No se pudo abrir el día recién creado. Revisa la consola (F12 > Console).',
        );
        return;
      }

      nav(`/plan/${targetId}`);
    } catch (err) {
      console.error('[Plan] Error en openOrCreateDia:', err);
      alert(
        'No se pudo abrir/crear el día. Revisa la consola (F12 > Console).',
      );
    } finally {
      setLoadingKey(null);
    }
  };

  return (
    <div className="p-4 space-y-4">
      <div className="flex items-center justify-between gap-2">
        <h2 className="text-2xl font-semibold">Plan semanal</h2>
        <div className="flex gap-2">
          <button
            onClick={goPrevWeek}
            className="px-3 py-1.5 rounded border bg-white hover:bg-gray-50"
          >
            ← Semana
          </button>
          <button
            onClick={goToday}
            className="px-3 py-1.5 rounded border bg-white hover:bg-gray-50"
          >
            Hoy
          </button>
          <button
            onClick={goNextWeek}
            className="px-3 py-1.5 rounded border bg-white hover:bg-gray-50"
          >
            Semana →
          </button>
        </div>
      </div>

      <div className="text-sm text-gray-600">
        {weekStart.toLocaleDateString()} — {weekEnd.toLocaleDateString()}
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
        {weekDays.map((d) => {
          const key = ymd(d);
          const dia = diasIndex.get(key);
          const esHoy = sameYMD(d, today);
          const comidasCount =
            (dia as any)?.comidas?.length ??
            (dia as any)?.cantidadComidas ??
            0;
          const isLoading = loadingKey === key;

          return (
            <button
              key={key}
              onClick={() => openOrCreateDia(d)}
              className={[
                'text-left border rounded-lg p-4 transition bg-white hover:bg-purple-50 cursor-pointer',
                esHoy ? 'ring-2 ring-purple-500' : '',
                isLoading ? 'opacity-60 cursor-wait' : '',
              ].join(' ')}
              disabled={isLoading}
              title={
                dia
                  ? 'Abrir día'
                  : 'Crear día y abrir (se crea automáticamente si no existe)'
              }
            >
              <div className="flex items-center justify-between">
                <div className="font-medium">
                  {d.toLocaleDateString(undefined, {
                    weekday: 'short',
                    day: '2-digit',
                    month: 'short',
                  })}
                </div>
                {esHoy && (
                  <span className="text-xs bg-purple-100 text-purple-800 px-2 py-0.5 rounded-full">
                    Hoy
                  </span>
                )}
              </div>

              <div className="text-sm text-gray-600 mt-1">
                {comidasCount} {comidasCount === 1 ? 'comida' : 'comidas'}
              </div>

              {!dia && (
                <div className="text-xs text-gray-500 mt-2">
                  (Se creará al entrar)
                </div>
              )}
            </button>
          );
        })}
      </div>
    </div>
  );
}
