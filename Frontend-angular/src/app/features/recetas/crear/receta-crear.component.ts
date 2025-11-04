import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-receta-crear',
  imports: [CommonModule, FormsModule],
  template: `
    <div class="section">
      <h2>Nueva receta</h2>

      <div *ngFor="let ing of ingredientes">
        <label>{{ ing.nombre }}</label>
        <div style="display:flex;align-items:center;gap:8px;">
          <button type="button" class="btn" (click)="dec(ing)">−</button>
          <span>{{ ing.cant }}</span>
          <button type="button" class="btn" (click)="inc(ing)">+</button>
        </div>
      </div>
    </div>
  `
})
export class RecetaCrearComponent {
  ingredientes = [
    { nombre: 'Arroz', cant: 1 },
    { nombre: 'Pollo', cant: 1 },
    { nombre: 'Verduras', cant: 1 }
  ];

  // ✅ Métodos en lugar de usar ++ o Math directamente
  inc(ing: { cant: number }) {
    ing.cant = (ing.cant ?? 0) + 1;
  }

  dec(ing: { cant: number }) {
    ing.cant = Math.max(1, (ing.cant ?? 1) - 1);
  }
}
