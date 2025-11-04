import { Routes } from '@angular/router';
import { ShellComponent } from './layout/ui/shell.component';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      // Home principal (redirige a Recetas)
      { path: '', redirectTo: 'recetas', pathMatch: 'full' },
      { path: 'home', loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent) },

      // Recetas
      { path: 'recetas', loadComponent: () => import('./features/recetas/recetas-list/recetas-list').then(m => m.RecetasListComponent) },
      { path: 'recetas/:id', loadComponent: () => import('./features/recetas/receta-detalle/receta-detalle').then(m => m.RecetaDetalleComponent) },
      {
        path: 'recetas/nueva',
        loadComponent: () => import('./features/recetas/crear/receta-crear.component').then(m => m.RecetaCrearComponent),
        canActivate: [authGuard]
      },

      // Planner semanal
      {
        path: 'planner',
        loadComponent: () => import('./features/planner/planner.component').then(m => m.PlannerComponent),
        canActivate: [authGuard]
      },

      // Ingredientes
      {
        path: 'ingredientes',
        loadComponent: () => import('./features/ingredientes/ingredientes-list/ingredientes-list').then(m => m.IngredientesListComponent),
        canActivate: [authGuard]
      },

      // Autenticación
      { path: 'login', loadComponent: () => import('./core/auth/login.component').then(m => m.LoginComponent) },
      { path: 'register', loadComponent: () => import('./core/auth/register.component').then(m => m.RegisterComponent) },

      // Fallback
      { path: '**', redirectTo: 'recetas' }
    ]
  }
];
