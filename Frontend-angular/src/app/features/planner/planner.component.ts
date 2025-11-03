import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-planner',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section>
      <h1 style="font-size:1.25rem; font-weight:600; margin-bottom:12px;">Planner diario</h1>
      <div style="display:grid; gap:12px; grid-template-columns: repeat(auto-fill, minmax(220px,1fr));">
        <div *ngFor="let slot of slots"
             style="border:1px solid #eee; border-radius:14px; padding:12px 14px; background:#fff; box-shadow:0 4px 10px rgba(0,0,0,.04)">
          <div style="font-weight:700; margin-bottom:6px;">{{slot.titulo}}</div>
          <div style="font-size:.85rem; color:#666;">{{slot.desc}}</div>
          <button style="margin-top:10px; padding:6px 10px; border-radius:10px; border:1px solid #ddd; background:#fafafa; cursor:pointer;">
            Agregar receta
          </button>
        </div>
      </div>
    </section>
  `
})
export class PlannerComponent {
  slots = [
    { titulo: 'Desayuno', desc: 'Añade una receta' },
    { titulo: 'Almuerzo',  desc: 'Añade una receta' },
    { titulo: 'Cena',      desc: 'Añade una receta' },
    { titulo: 'Snacks',    desc: 'Añade una receta' }
  ];
}
