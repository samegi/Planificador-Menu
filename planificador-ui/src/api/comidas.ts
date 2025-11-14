// src/api/comidas.ts
import { BASE, http } from './http';
import type { Comida } from './types';

// El backend tiene algo así:
//
// @PostMapping("/dias/{diaId}/comidas")
// public Comida crearComida(@PathVariable Long diaId,
//                           @RequestParam Long recetaId,
//                           @RequestParam String hora)

export type CrearComidaPayload = {
  diaId: number;
  recetaId: number;
  hora: string; // "HH:mm"
};

export const ComidasApi = {
  // Agregar una comida a un día
  agregar(payload: CrearComidaPayload): Promise<Comida> {
    const query = new URLSearchParams({
      recetaId: String(payload.recetaId),
      hora: payload.hora,
    }).toString();

    // IMPORTANTE: recetaId y hora VAN EN EL QUERY STRING
    return http<Comida>(`${BASE}/dias/${payload.diaId}/comidas?${query}`, {
      method: 'POST',
    });
  },

  // Eliminar una comida por id
  eliminar(id: number): Promise<void> {
    return http<void>(`${BASE}/comidas/${id}`, {
      method: 'DELETE',
    });
  },
};
