import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../core/auth/auth.service';
import { BottomNavComponent } from './ui/bottom-nav.component';


@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, BottomNavComponent],
  template: `
    <div class="app-wrap">
      <header class="topbar">
        <div class="topbar-inner">
          <div class="brand">Meal Flow</div>
          <nav class="tabs">
            <a routerLink="/home" routerLinkActive="active">Inicio</a>
            <a routerLink="/recetas" routerLinkActive="active">Recetas</a>
            <a routerLink="/planner" routerLinkActive="active">Plan</a>
            <span style="flex:1"></span>
            <a *ngIf="!auth.isLoggedIn()" routerLink="/login">Entrar</a>
            <button *ngIf="auth.isLoggedIn()" class="btn" (click)="logout()">Salir</button>
          </nav>
        </div>
      </header>

      <main class="page">
        <router-outlet></router-outlet>
      </main>

      <app-bottom-nav></app-bottom-nav>
    </div>
  `
})
export class ShellComponent {
  auth = inject(AuthService); private router = inject(Router);
  logout(){ this.auth.logout(); this.router.navigateByUrl('/home'); }
}
