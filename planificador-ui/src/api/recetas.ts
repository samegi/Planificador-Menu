// src/api/recetas.ts
import { BASE, http } from './http';
import type { Receta } from './types';

export type RecetaPayload = {
  nombre: string;
  descripcion?: string;
};

export const RecetasApi = {
  listar(): Promise<Receta[]> {
    return http<Receta[]>(`${BASE}/recetas`);
  },

  obtener(id: number): Promise<Receta> {
    return http<Receta>(`${BASE}/recetas/${id}`);
  },

  crear(data: RecetaPayload): Promise<Receta> {
    const params = new URLSearchParams();
    params.append('nombre', data.nombre);
    if (data.descripcion && data.descripcion.trim() !== '') {
      params.append('descripcion', data.descripcion.trim());
    }

    // 👇 AÑADIMOS SIEMPRE ALGÚN MACRONUTRIENTE
    // OJO: pon aquí exactamente uno de los nombres de tu enum:
    // por ejemplo: PROTEINA, CARBOHIDRATO, GRASA…
    params.append('macronutriente', 'CARBOHIDRATO');

    return http<Receta>(`${BASE}/recetas?${params.toString()}`, {
      method: 'POST',
    });
  },

  actualizar(id: number, data: RecetaPayload): Promise<Receta> {
    const params = new URLSearchParams();
    params.append('nombre', data.nombre);
    if (data.descripcion && data.descripcion.trim() !== '') {
      params.append('descripcion', data.descripcion.trim());
    }

    // 👇 Igual para actualizar
    params.append('macronutriente', 'CARBOHIDRATO');

    return http<Receta>(`${BASE}/recetas/${id}?${params.toString()}`, {
      method: 'PUT',
    });
  },

  eliminar(id: number): Promise<void> {
    return http<void>(`${BASE}/recetas/${id}`, { method: 'DELETE' });
  },
};
