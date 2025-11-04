import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <h1 class="h1">Inicio de sesión</h1>
    <form class="card section" (ngSubmit)="submit()">
      <label>Usuario<input class="input" [(ngModel)]="u" name="u" /></label>
      <label style="margin-top:8px;">Contraseña
        <input type="password" class="input" [(ngModel)]="p" name="p" />
      </label>
      <button class="btn primary" style="margin-top:12px;" type="submit">Ingresar</button>
      <a routerLink="/register" class="muted" style="display:block;margin-top:8px;">Registrarse</a>
      <p *ngIf="err" style="color:#e11d48;margin:8px 0 0">{{err}}</p>
    </form>
  `
})
export class LoginComponent {
  u = ''; p = ''; err = '';
  constructor(private auth: AuthService, private router: Router, private ar: ActivatedRoute) {}
  submit() {
    if (this.auth.login(this.u, this.p)) {
      const red = this.ar.snapshot.queryParamMap.get('redirect') || '/home';
      this.router.navigateByUrl(red);
    } else { this.err = 'Usuario y contraseña requeridos'; }
  }
}
