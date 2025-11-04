import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // ✅ AGREGA ESTO

@Component({
  standalone: true,
  selector: 'app-ingredientes-list',
  imports: [CommonModule, FormsModule], // ✅ AGREGA FormsModule AQUÍ
  template: `
    <input
      type="text"
      placeholder="Buscar"
      [(ngModel)]="q"
      (input)="filtrar()"
      class="input"
    />
    <ul>
      <li *ngFor="let ing of ingredientesFiltrados">{{ ing }}</li>
    </ul>
  `
})
export class IngredientesListComponent {
  q = '';
  ingredientes = ['Arroz', 'Pasta', 'Pollo', 'Carne', 'Leche'];
  ingredientesFiltrados = [...this.ingredientes];

  filtrar() {
    const query = this.q.toLowerCase();
    this.ingredientesFiltrados = this.ingredientes.filter(i =>
      i.toLowerCase().includes(query)
    );
  }
}
