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
    if (data.descripcion) {
      params.append('descripcion', data.descripcion);
    }

    return http<Receta>(`${BASE}/recetas?${params.toString()}`, {
      method: 'POST',
    });
  },

  actualizar(id: number, data: RecetaPayload): Promise<Receta> {
    const params = new URLSearchParams();
    params.append('nombre', data.nombre);
    if (data.descripcion) {
      params.append('descripcion', data.descripcion);
    }

    return http<Receta>(`${BASE}/recetas/${id}?${params.toString()}`, {
      method: 'PUT',
    });
  },

  eliminar(id: number): Promise<void> {
    return http<void>(`${BASE}/recetas/${id}`, { method: 'DELETE' });
  },
};
