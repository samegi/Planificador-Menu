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
  macronutriente?: string; // opcional si quieres manejarlo
};

export const RecetasApi = {
  listar(): Promise<Receta[]> {
    return getRecetas();
  },

  obtener(id: number): Promise<Receta> {
    return getReceta(id);
  },

  // POST /recetas?nombre=...&descripcion=...&macronutriente=...
  crear(data: RecetaPayload): Promise<Receta> {
    const params = new URLSearchParams();
    params.append('nombre', data.nombre);
    if (data.descripcion) {
      params.append('descripcion', data.descripcion);
    }
    if (data.macronutriente) {
      params.append('macronutriente', data.macronutriente);
    }

    return http<Receta>(`${BASE}/recetas?${params.toString()}`, {
      method: 'POST',
    });
  },

  // PUT /recetas/{id}?nombre=...&descripcion=...&macronutriente=...
  actualizar(id: number, data: RecetaPayload): Promise<Receta> {
    const params = new URLSearchParams();
    params.append('nombre', data.nombre);
    if (data.descripcion) {
      params.append('descripcion', data.descripcion);
    }
    if (data.macronutriente) {
      params.append('macronutriente', data.macronutriente);
    }

    return http<Receta>(`${BASE}/recetas/${id}?${params.toString()}`, {
      method: 'PUT',
    });
  },

  async eliminar(id: number): Promise<void> {
    // DELETE suele devolver 204 sin cuerpo, mejor no usar http<T> aquí
    const res = await fetch(`${BASE}/recetas/${id}`, {
      method: 'DELETE',
    });
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`DELETE /recetas/${id} -> HTTP ${res.status} ${text}`);
    }
  },
};
