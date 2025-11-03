import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <header style="display:flex; align-items:center; gap:16px; padding:12px 16px; border-bottom:1px solid #eee; position:sticky; top:0; background:#fff; z-index:10;">
      <div style="font-weight:700;">Meal-Flow</div>
      <nav style="display:flex; gap:12px;">
        <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact:true}">Planner</a>
        <a routerLink="/recetas" routerLinkActive="active">Recetas</a>
        <a routerLink="/ingredientes" routerLinkActive="active">Ingredientes</a>
      </nav>
    </header>
    <main style="padding:16px;"><router-outlet></router-outlet></main>
  `
})
export class ShellComponent {}
