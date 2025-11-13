// src/api/plan.ts
import { http, BASE } from './http';
import type { Dia, Comida } from './types';

// OJO: tus controladores están bajo /api/dias y /api/comidas
// porque en http.ts pusimos BASE = 'http://localhost:8080/api'

export const PlanApi = {
  // --- DÍAS ---

  // GET /api/dias
  dias: () => http<Dia[]>(`${BASE}/dias`),

  // GET /api/dias/{id}
  getDia: (id: number) => http<Dia>(`${BASE}/dias/${id}`),

  // POST /api/dias  body: { "fecha": "YYYY-MM-DD" }
  // IMPORTANTE: la firma acepta UN string (fecha) porque
  // en Plan.tsx llamas PlanApi.createDia(key)
  createDia: (fecha: string) =>
    http<Dia>(`${BASE}/dias`, {
      method: 'POST',
      body: JSON.stringify({ fecha }),
    }),

  // --- COMIDAS LIGADAS A UN DÍA ---

  // GET /api/dias/{id}/comidas
  getComidas: (diaId: number) =>
    http<Comida[]>(`${BASE}/dias/${diaId}/comidas`),

  // POST /api/dias/{id}/comidas
  // body esperado por tu ComidaController: { "receta": {"id":...}, "hora": "HH:mm" }
  addComida: (data: { diaId: number; recetaId: number; hora: string }) =>
    http<Comida>(`${BASE}/dias/${data.diaId}/comidas`, {
      method: 'POST',
      body: JSON.stringify({
        receta: { id: data.recetaId },
        hora: data.hora,
      }),
    }),

  // PUT /api/comidas/{id}
  updateComida: (id: number, data: { recetaId: number; hora: string }) =>
    http<Comida>(`${BASE}/comidas/${id}`, {
      method: 'PUT',
      body: JSON.stringify({
        receta: { id: data.recetaId },
        hora: data.hora,
      }),
    }),

  // DELETE /api/comidas/{id}
  deleteComida: (id: number) =>
    http<void>(`${BASE}/comidas/${id}`, {
      method: 'DELETE',
    }),
};
