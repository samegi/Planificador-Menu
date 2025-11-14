// src/api/types.ts
export type Id = number;

export interface Receta {
  id: Id;
  nombre: string;
  descripcion?: string;

  // campos de texto que se ven en tu formulario
  ingredientes?: string;
  instrucciones?: string;

  macronutriente?: string; // si lo usas
}

export interface Comida {
  id: Id;
  hora: string;
  receta?: Receta;
  nombreReceta?: string;
  recetaId?: number;
}

export interface Dia {
  id: Id;
  fecha: string;   // "YYYY-MM-DD"
  comidas?: Comida[];
}
