import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ingredientes-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section>
      <h1 style="font-size:1.25rem; font-weight:600; margin-bottom:12px;">Ingredientes</h1>
      <p style="color:#666">Próximamente…</p>
    </section>
  `
})
export class IngredientesListComponent {}
