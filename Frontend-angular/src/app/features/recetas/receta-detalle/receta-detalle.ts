import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RecetaService, Receta } from '../../../core/services/receta.service';

@Component({
  standalone: true,
  selector: 'app-receta-detalle',
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <a routerLink="/recetas" class="muted">← Catálogo</a>

    <article class="card" style="overflow:hidden;margin-top:8px" *ngIf="r as rec">
      <div
        class="thumb"
        [style.background-image]="rec.imagenUrl ? 'url(' + rec.imagenUrl + ')' : ''"
        style="height:200px">
      </div>

      <div class="section">
        <h1 class="h1" style="margin-top:0">{{ rec.nombre }}</h1>
        <p class="small" style="color:#475062">{{ rec.descripcion }}</p>

        <div style="display:flex; gap:8px; margin-top:10px">
          <a class="btn" routerLink="/ingredientes">Ingredientes</a>
          <button class="btn primary" (click)="openDialog()">Agregar a la semana</button>
        </div>
      </div>
    </article>

    <dialog #dlg style="border:none;border-radius:16px;padding:0;max-width:360px;width:90%">
      <form method="dialog" class="section">
        <div class="h2">Agregar al plan</div>

        <label>Día
          <select class="input" [(ngModel)]="day" name="day">
            <option *ngFor="let d of days" [value]="d">{{ d }}</option>
          </select>
        </label>

        <label style="margin-top:8px;">Comida
          <select class="input" [(ngModel)]="meal" name="meal">
            <option *ngFor="let m of meals" [value]="m">{{ m }}</option>
          </select>
        </label>

        <div style="display:flex;gap:8px;margin-top:12px;justify-content:flex-end">
          <button class="btn" value="cancel">Cancelar</button>
          <button class="btn primary" (click)="addToPlan(); dlg.close()">Agregar</button>
        </div>
      </form>
    </dialog>
  `,
})
export class RecetaDetalleComponent implements OnInit {
  r?: Receta;

  readonly days = ['Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
  readonly meals = ['Desayuno','Almuerzo','Cena','Snacks'];

  day: string = 'Lunes';
  meal: string = 'Almuerzo';

  @ViewChild('dlg') dlg!: ElementRef<HTMLDialogElement>;

  constructor(private ar: ActivatedRoute, private srv: RecetaService) {}

  ngOnInit(): void {
    const id = Number(this.ar.snapshot.paramMap.get('id'));
    this.srv.porId(id).subscribe(x => this.r = x);
  }

  openDialog(): void {
    this.dlg?.nativeElement.showModal();
  }

  addToPlan(): void {
    if (!this.r) return;

    const key = 'mf-week-plan';
    const raw = localStorage.getItem(key);
    const base: any = raw ? JSON.parse(raw) : {};

    if (!base[this.day]) {
      base[this.day] = { Desayuno: [], Almuerzo: [], Cena: [], Snacks: [] };
    }

    base[this.day][this.meal].push(this.r.nombre);
    localStorage.setItem(key, JSON.stringify(base));
    alert(`Agregada a ${this.meal} de ${this.day}`);
  }
}
