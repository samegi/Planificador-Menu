// src/api/types.ts
export type Id = number;

export interface Receta {
  id: Id;
  nombre: string;
  descripcion?: string;
}

export interface Comida {
  id: Id;
  hora: string;            // "HH:mm"
  receta?: Receta;
  recetaNombre?: string;   // opcional si tu back lo manda así
}

export interface Dia {
  id: Id;
  fecha: string;           // "YYYY-MM-DD"
  comidas?: Comida[];
}
