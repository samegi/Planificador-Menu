import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-bottom-nav',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  template: `
    <nav class="bottom-nav">
      <div class="row">
        <a routerLink="/home" routerLinkActive="active"><span class="icon">🏠</span><span>Inicio</span></a>
        <a routerLink="/recetas" routerLinkActive="active"><span class="icon">📚</span><span>Recetas</span></a>
        <a routerLink="/planner" routerLinkActive="active"><span class="icon">🗓️</span><span>Plan</span></a>
        <a routerLink="/ingredientes" routerLinkActive="active"><span class="icon">🛒</span><span>Lista</span></a>
      </div>
    </nav>
  `
})
export class BottomNavComponent {}
