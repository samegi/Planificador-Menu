// src/api/adapters.ts
// Este adaptador centraliza las funciones del backend y tolera nombres distintos.

import * as plan from './plan';
import * as recetas from './recetas';

export const planApi = {
  // busca el método disponible sin romper el tipado
  getDias: (plan as any).getDias ?? (plan as any).fetchDias ?? (plan as any).dias,
  getDia: (plan as any).getDia ?? (plan as any).fetchDia ?? (plan as any).dia,
  getComidas:
    (plan as any).getComidas ??
    (plan as any).fetchComidas ??
    (plan as any).comidas ??
    (plan as any).listComidas,
  addComida:
    (plan as any).addComida ??
    (plan as any).crearComida ??
    (plan as any).postComida ??
    (plan as any).addMeal,
};

export const recetasApi = {
  getRecetas:
    (recetas as any).getRecetas ??
    (recetas as any).fetchRecetas ??
    (recetas as any).list ??
    (recetas as any).listarRecetas,
  getReceta:
    (recetas as any).getReceta ??
    (recetas as any).fetchReceta ??
    (recetas as any).detalle ??
    (recetas as any).find,
};
