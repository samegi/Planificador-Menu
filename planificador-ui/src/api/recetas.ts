// src/api/recetas.ts
import { BASE, http } from './http';
import type { Receta } from './types';

// ---------- Helpers básicos usados por otras partes (Dia.tsx) ----------
export function getRecetas(): Promise<Receta[]> {
  return http<Receta[]>(`${BASE}/recetas`);
}

export function getReceta(id: number): Promise<Receta> {
  return http<Receta>(`${BASE}/recetas/${id}`);
}

// ---------- API de alto nivel para el catálogo ----------
export type RecetaPayload = {
  nombre: string;
  descripcion?: string;
};

export const RecetasApi = {
  listar(): Promise<Receta[]> {
    return getRecetas();
  },

  obtener(id: number): Promise<Receta> {
    return getReceta(id);
  },

  crear(data: RecetaPayload): Promise<Receta> {
    return http<Receta>(`${BASE}/recetas`, {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  actualizar(id: number, data: RecetaPayload): Promise<Receta> {
    return http<Receta>(`${BASE}/recetas/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  async eliminar(id: number): Promise<void> {
    // Ojo: muchos DELETE devuelven 204 sin cuerpo → mejor NO usar http<T>
    const res = await fetch(`${BASE}/recetas/${id}`, {
      method: 'DELETE',
    });
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`DELETE /recetas/${id} -> HTTP ${res.status} ${text}`);
    }
  },
};
