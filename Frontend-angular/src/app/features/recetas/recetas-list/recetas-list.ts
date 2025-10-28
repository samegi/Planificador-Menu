import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecetaService, Receta } from '../../core/services/receta.service';

@Component({
  selector: 'app-recetas-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section style="padding: 16px">
      <h1>Listado de Recetas</h1>
      <p *ngIf="cargando">Cargando...</p>
      <ul *ngIf="!cargando && recetas.length; else vacio">
        <li *ngFor="let r of recetas">
          {{ r.id }} — {{ r.nombre || '(sin nombre)' }}
        </li>
      </ul>
      <ng-template #vacio>No hay recetas disponibles</ng-template>
    </section>
  `
})
export class RecetasListComponent implements OnInit {
  recetas: Receta[] = [];
  cargando = true;

  constructor(private recetaService: RecetaService) {}

  ngOnInit() {
    this.recetaService.listar().subscribe({
      next: (data) => { this.recetas = data; this.cargando = false; },
      error: (err) => { console.error('Error al cargar recetas:', err); this.cargando = false; }
    });
  }
}
