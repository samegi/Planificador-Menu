import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { RecetaService, Receta } from '../../../core/services/receta.service';

@Component({
  standalone:true, selector:'app-recetas-list',
  imports:[CommonModule,FormsModule,RouterLink],
  template:`
  <h1 class="h1">Catálogo de Recetas</h1>
  <input class="input" placeholder="Buscar..." [(ngModel)]="q" (input)="filtrar()">

  <div class="grid recetas" style="margin-top:12px">
    <article class="card" *ngFor="let r of filtradas">
      <div class="thumb" [style.background-image]="r.imagenUrl ? 'url('+r.imagenUrl+')' : ''"></div>
      <div class="section">
        <div class="h2" style="margin:0 0 6px">{{r.nombre}}</div>
        <div class="muted small" style="min-height:34px">{{r.descripcion}}</div>
        <div style="display:flex; gap:8px; margin-top:10px">
          <a class="btn" [routerLink]="['/recetas', r.id]">Ver</a>
          <a class="btn primary" [routerLink]="['/planner']">+ Plan</a>
        </div>
      </div>
    </article>
  </div>
  `
})
export class RecetasListComponent implements OnInit{
  recetas:Receta[]=[]; filtradas:Receta[]=[]; q='';
  constructor(private srv:RecetaService){}
  ngOnInit(){ this.srv.listar().subscribe(x=>{this.recetas=x; this.filtradas=x;}); }
  filtrar(){ const s=this.q.toLowerCase(); this.filtradas=!s?this.recetas:this.recetas.filter(r=>(r.nombre||'').toLowerCase().includes(s)); }
}
