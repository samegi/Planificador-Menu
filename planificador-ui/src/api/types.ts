// src/api/types.ts
export type Id = number;

export interface Receta {
  id: Id;
  nombre: string;
  descripcion?: string;
  macronutriente?: string;
}

export interface Comida {
  id: Id;
  hora: string;
  receta?: Receta;
  nombreReceta?: string;  // 👈 coincide con el JSON
  recetaId?: number;
}


export interface Dia {
  id: Id;
  fecha: string;         // "YYYY-MM-DD"
  comidas?: Comida[];
}
