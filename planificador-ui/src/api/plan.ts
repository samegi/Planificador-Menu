// src/api/plan.ts
import { BASE, http } from './http';
import type { Dia, Comida } from './types';

export type AddComidaPayload = {
  diaId: number;
  recetaId: number;
  hora: string;   // "HH:mm"
};

export type UpdateComidaPayload = {
  recetaId: number;
  hora: string;   // "HH:mm"
};

export const PlanApi = {
  // ----- DÍAS -----
  dias(): Promise<Dia[]> {
    return http<Dia[]>(`${BASE}/dias`);
  },

  getDia(id: number): Promise<Dia> {
    return http<Dia>(`${BASE}/dias/${id}`);
  },

  // POST /api/dias  body: { "fecha": "YYYY-MM-DD" }
   createDia(fechaYmd: string): Promise<Dia> {
    return http<Dia>(`${BASE}/dias/crear?fecha=${fechaYmd}`, {
      method: 'POST',
    });
  },

  // ----- COMIDAS -----
  getComidas(diaId: number): Promise<Comida[]> {
    return http<Comida[]>(`${BASE}/dias/${diaId}/comidas`);
  },

  // POST /api/dias/{diaId}/comidas?recetaId=..&hora=..
  addComida(payload: AddComidaPayload): Promise<Comida> {
    const params = new URLSearchParams({
      recetaId: String(payload.recetaId),
      hora: payload.hora,
    });

    return http<Comida>(`${BASE}/dias/${payload.diaId}/comidas?${params}`, {
      method: 'POST',
    });
  },

  // PUT /api/comidas/{id}  (body JSON)
  updateComida(id: number, payload: UpdateComidaPayload): Promise<Comida> {
    const body = JSON.stringify({
      hora: payload.hora,
      receta: { id: payload.recetaId },
    });

    return http<Comida>(`${BASE}/comidas/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body,
    });
  },

  // DELETE /api/comidas/{id}
  async deleteComida(id: number): Promise<void> {
    const res = await fetch(`${BASE}/comidas/${id}`, {
      method: 'DELETE',
    });

    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`DELETE /comidas/${id} -> HTTP ${res.status} ${text}`);
    }
  },
};
