// src/pages/plan/Plan.tsx
import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PlanApi } from '../../api/plan';
import type { Dia } from '../../api/types';

function toISODate(d: Date): string {
  return d.toISOString().slice(0, 10); // YYYY-MM-DD
}

function getDiaFecha(dia: Dia): string | null {
  return (
    (dia as any).fecha ??
    (dia as any).fechaISO ??
    (dia as any).diaFecha ??
    null
  );
}

export default function PlanPage() {
  const [dias, setDias] = useState<Dia[]>([]);
  const [loading, setLoading] = useState(true);
  const [weekOffset, setWeekOffset] = useState(0); // 0 = semana actual
  const nav = useNavigate();

  // cargar días desde el backend solo una vez
  useEffect(() => {
    (async () => {
      try {
        const data = await PlanApi.dias();
        setDias(data);
      } catch (err) {
        console.error('[Plan] Error cargando días', err);
        alert('No se pudieron cargar los días. Mira la consola.');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const today = useMemo(() => {
    const d = new Date();
    d.setHours(0, 0, 0, 0);
    return d;
  }, []);

  // lunes de la semana con el offset
  const weekStart = useMemo(() => {
    const d = new Date(today);
    const day = d.getDay(); // 0=dom,1=lun,...
    const diffToMonday = (day + 6) % 7; // 0 si es lunes
    d.setDate(d.getDate() - diffToMonday + weekOffset * 7);
    d.setHours(0, 0, 0, 0);
    return d;
  }, [today, weekOffset]);

  // construimos la semana (7 días)
  const semana = useMemo(() => {
    const arr: { date: Date; iso: string; diaBD: Dia | null }[] = [];

    for (let i = 0; i < 7; i++) {
      const d = new Date(weekStart);
      d.setDate(weekStart.getDate() + i);
      d.setHours(0, 0, 0, 0);
      const iso = toISODate(d);

      const diaBD =
        dias.find((dia) => {
          const f = getDiaFecha(dia);
          return f?.startsWith(iso);
        }) ?? null;

      arr.push({ date: d, iso, diaBD });
    }

    return arr;
  }, [weekStart, dias]);

  const abrirDia = async (iso: string) => {
    // ¿ya existe ese día en BD?
    let dia = dias.find((d) => {
      const f = getDiaFecha(d);
      return f?.startsWith(iso);
    });

    if (!dia) {
      try {
        console.log('[Plan] Creando día...', iso);
        dia = await PlanApi.createDia(iso);
        setDias((prev) => [...prev, dia!]);
      } catch (err) {
        console.error('[Plan] Error en openOrCreateDia', err);
        alert('No se pudo crear el día. Mira la consola.');
        return;
      }
    }

    nav(`/plan/${dia.id}`);
  };

  const irHoy = () => {
    const iso = toISODate(today);
    abrirDia(iso);
  };

  if (loading) {
    return <div className="p-4">Cargando plan…</div>;
  }

  return (
    <div className="p-4 space-y-4">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Plan de comidas</h1>

        <div className="flex gap-2">
          <button
            className="px-3 py-2 rounded border"
            onClick={() => setWeekOffset((w) => w - 1)}
          >
            Semana anterior
          </button>
          <button
            className="px-4 py-2 rounded bg-purple-600 text-white"
            onClick={irHoy}
          >
            Ir al día de hoy
          </button>
          <button
            className="px-3 py-2 rounded border"
            onClick={() => setWeekOffset((w) => w + 1)}
          >
            Semana siguiente
          </button>
        </div>
      </div>

      <div className="space-y-3">
        {semana.map(({ date, iso, diaBD }) => {
          const numComidas = diaBD?.comidas?.length ?? 0;

          const esHoy = iso === toISODate(today);

          const titulo = date.toLocaleDateString('es-ES', {
            weekday: 'long',
            day: 'numeric',
            month: 'long',
            year: 'numeric',
          });

          return (
            <div
              key={iso}
              onClick={() => abrirDia(iso)}
              className={`cursor-pointer border rounded-xl px-4 py-3 shadow-sm hover:shadow-md transition
                ${esHoy ? 'bg-purple-50 border-purple-300' : 'bg-white'}
              `}
            >
              <div className="text-lg font-semibold capitalize">{titulo}</div>
              <div className="text-sm text-gray-600">
                {diaBD
                  ? numComidas === 0
                    ? 'Sin comidas registradas'
                    : `${numComidas} comida${numComidas === 1 ? '' : 's'}`
                  : 'Sin comidas registradas'}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
