import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecetaService, Receta } from '../../../core/services/receta.service';

@Component({
  selector: 'app-recetas-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section style="padding: 16px;">
      <h1 style="font-size: 1.25rem; font-weight: 600; margin-bottom: 12px;">Recetas</h1>
      <p *ngIf="cargando" style="color:#666">Cargando...</p>

      <ng-container *ngIf="!cargando">
        <ng-container *ngIf="recetas.length > 0; else vacio">
          <div style="display:grid; gap:12px; grid-template-columns:repeat(auto-fill,minmax(240px,1fr));">
            <div *ngFor="let r of recetas" style="border:1px solid #eee; border-radius:14px; padding:12px; background:#fff; box-shadow:0 4px 10px rgba(0,0,0,.04)">
              <div style="font-weight:700">{{ r.nombre || '(sin nombre)' }}</div>
              <div style="font-size:.85rem; color:#555">{{ r.descripcion || 'Sin descripción' }}</div>
              <div style="font-size:.75rem; color:#999; margin-top:.5rem">ID: {{ r.id }}</div>
            </div>
          </div>
        </ng-container>
        <ng-template #vacio><p style="color:#999">No hay recetas registradas.</p></ng-template>
      </ng-container>
    </section>
  `
})
export class RecetasListComponent implements OnInit {
  recetas: Receta[] = [];
  cargando = true;

  constructor(private recetaService: RecetaService) {}

  ngOnInit(): void {
    this.recetaService.listar().subscribe({
      next: (data: Receta[]) => { this.recetas = data; this.cargando = false; },
      error: (err: unknown) => { console.error('Error al cargar recetas:', err); this.cargando = false; }
    });
  }
}
