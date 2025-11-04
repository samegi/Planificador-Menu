import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, FormsModule],
  template: `
    <h1 class="h1">Registro</h1>
    <form class="card section" (ngSubmit)="submit()">
      <label>Usuario<input class="input" [(ngModel)]="u" name="u" /></label>
      <label style="margin-top:8px;">Apellido<input class="input" [(ngModel)]="a" name="a" /></label>
      <label style="margin-top:8px;">Contraseña
        <input type="password" class="input" [(ngModel)]="p" name="p" />
      </label>
      <label style="margin-top:8px;">Repetir contraseña
        <input type="password" class="input" [(ngModel)]="rp" name="rp" />
      </label>
      <button class="btn primary" style="margin-top:12px;" type="submit">Crear cuenta</button>
    </form>
  `
})
export class RegisterComponent {
  u=''; a=''; p=''; rp='';
  constructor(private auth: AuthService, private r: Router) {}
  submit(){ if (this.auth.register(this.u,this.a,this.p,this.rp)) this.r.navigateByUrl('/home'); }
}
