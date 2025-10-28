import { Routes } from '@angular/router';
import { RecetasListComponent } from './features/recetas/recetas-list/recetas-list';

export const routes: Routes = [
  { path: '', redirectTo: 'recetas', pathMatch: 'full' },
  { path: 'recetas', component: RecetasListComponent }
];
