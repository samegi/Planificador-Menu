import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  standalone:true, selector:'app-home', imports:[CommonModule,RouterLink],
  template:`
  <h1 class="h1">Bienvenido</h1>
  <div class="grid">
    <a class="card" routerLink="/planner" style="display:block;overflow:hidden">
      <div class="thumb" style="height:100px;background-image:url('assets/img/plan-thumb.jpg')"></div>
      <div class="section"><div class="h2">Plan semanal</div></div>
    </a>
    <a class="card" routerLink="/recetas" style="display:block;overflow:hidden">
      <div class="thumb" style="height:100px;background-image:url('assets/img/recetas-thumb.jpg')"></div>
      <div class="section"><div class="h2">Recetas</div></div>
    </a>
  </div>
  `
})
export class HomeComponent{}
