// src/api/comidas.ts
import { BASE, http } from './http';
import type { Comida } from './types';

export const ComidasApi = {
  // crea una comida en un día, asociada a una receta
  crear: (payload: { diaId: number; recetaId: number; hora: string }) =>
    http<Comida>(`${BASE}/comidas?recetaId=${payload.recetaId}`, {
      method: 'POST',
      body: JSON.stringify({
        hora: payload.hora,
        dia: { id: payload.diaId },
      }),
    }),
};
