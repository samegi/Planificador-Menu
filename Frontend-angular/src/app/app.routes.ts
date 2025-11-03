import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell.component';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/planner/planner.component').then(m => m.PlannerComponent)
      },
      {
        path: 'recetas',
        loadComponent: () =>
          import('./features/recetas/recetas-list/recetas-list').then(m => m.RecetasListComponent)
      },
      {
        path: 'ingredientes',
        loadComponent: () =>
          import('./features/ingredientes/ingredientes-list/ingredientes-list').then(m => m.IngredientesListComponent)
      },
      { path: '**', redirectTo: '' }
    ]
  }
];
