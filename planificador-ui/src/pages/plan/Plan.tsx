// src/api/plan.ts
import { BASE, http } from '../../api/http';
import type { Dia, Comida } from '../../api/types';

// Tipos para las operaciones de comida
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
  // =======================
  // DÍAS
  // =======================

  // GET /api/dias
  dias(): Promise<Dia[]> {
    return http<Dia[]>(`${BASE}/dias`);
  },

  // GET /api/dias/{id}
  getDia(id: number): Promise<Dia> {
    return http<Dia>(`${BASE}/dias/${id}`);
  },

  // POST /api/dias
  // El backend espera @RequestBody Dia -> le mandamos JSON: { "fecha": "YYYY-MM-DD" }
  createDia(fechaYmd: string): Promise<Dia> {
    const body = JSON.stringify({ fecha: fechaYmd });

    return http<Dia>(`${BASE}/dias`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body,
    });
  },

  // =======================
  // COMIDAS
  // =======================

  // GET /api/dias/{diaId}/comidas
  getComidas(diaId: number): Promise<Comida[]> {
    return http<Comida[]>(`${BASE}/dias/${diaId}/comidas`);
  },

  // POST /api/dias/{diaId}/comidas?recetaId=..&hora=..
  // (esto es exactamente lo que tu DiaController expone)
  addComida(payload: AddComidaPayload): Promise<Comida> {
    const params = new URLSearchParams({
      recetaId: String(payload.recetaId),
      hora: payload.hora,
    });

    const url = `${BASE}/dias/${payload.diaId}/comidas?${params.toString()}`;
    return http<Comida>(url, {
      method: 'POST',
    });
  },

  // PUT /api/comidas/{id}
  // ComidaController.actualizarComida(@PathVariable id, @RequestBody Comida)
  // -> le mandamos JSON: { "hora": "HH:mm", "receta": { "id": X } }
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
