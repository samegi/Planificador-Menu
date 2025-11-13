// src/api/plan.ts
import { BASE, http } from './http';
import type { Dia, Comida } from './types';

// ------- Tipos auxiliares -------
export type AddComidaPayload = {
  diaId: number;
  recetaId: number;
  hora: string; // "HH:mm"
};

export type UpdateComidaPayload = {
  recetaId: number;
  hora: string; // "HH:mm"
};

export const PlanApi = {
  // =============== DÍAS ===============

  // GET /dias
  getDias(): Promise<Dia[]> {
    return http<Dia[]>(`${BASE}/dias`);
  },

  // Alias porque en Plan.tsx llamas PlanApi.dias()
  dias(): Promise<Dia[]> {
    return http<Dia[]>(`${BASE}/dias`);
  },

  // GET /dias/{id}
  getDia(id: number): Promise<Dia> {
    return http<Dia>(`${BASE}/dias/${id}`);
  },

  // POST /dias   body: { "fecha": "YYYY-MM-DD" }
  // En Plan.tsx llamas: PlanApi.createDia(key) donde key es "2025-11-13"
  createDia(fechaKey: string): Promise<Dia> {
    // El backend tiene @RequestBody Dia dia,
    // así que le mandamos un objeto con la propiedad "fecha"
    const body = { fecha: fechaKey };

    return http<Dia>(`${BASE}/dias`, {
      method: 'POST',
      body: JSON.stringify(body),
    });
  },

  // =============== COMIDAS ===============

  // GET /dias/{diaId}/comidas
  getComidas(diaId: number): Promise<Comida[]> {
    return http<Comida[]>(`${BASE}/dias/${diaId}/comidas`);
  },

  // POST /dias/{diaId}/comidas?recetaId=...&hora=...
  addComida(payload: AddComidaPayload): Promise<Comida> {
    const query = new URLSearchParams({
      recetaId: String(payload.recetaId),
      hora: payload.hora,
    }).toString();

    return http<Comida>(
      `${BASE}/dias/${payload.diaId}/comidas?${query}`,
      { method: 'POST' }
    );
  },

 // PUT /comidas/{id}  body: { hora: "...", receta: { id: ... } }
updateComida(id: number, data: UpdateComidaPayload): Promise<Comida> {
  const body = {
    hora: data.hora,            // "HH:mm"
    receta: { id: data.recetaId },
    // dia no lo mandamos: el back lo toma del registro existente
  };

  return http<Comida>(`${BASE}/comidas/${id}`, {
    method: 'PUT',
    body: JSON.stringify(body),
  });
},


  // DELETE /comidas/{id}
 // DELETE /comidas/{id}
async deleteComida(id: number): Promise<void> {
  const res = await fetch(`${BASE}/comidas/${id}`, {
    method: 'DELETE',
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`DELETE /comidas/${id} -> HTTP ${res.status} ${text}`);
  }
}
};
