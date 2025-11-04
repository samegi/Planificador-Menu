import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

type Meal = 'Desayuno' | 'Almuerzo' | 'Cena' | 'Snacks';
type Day =
  | 'Lunes' | 'Martes' | 'Miércoles' | 'Jueves'
  | 'Viernes' | 'Sábado' | 'Domingo';

interface WeekPlan {
  [day: string]: { [meal in Meal]: string[] };
}

const DAYS: Day[] = ['Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo'];
const MEALS: Meal[] = ['Desayuno','Almuerzo','Cena','Snacks'];
const LS_KEY = 'mf-week-plan';

@Component({
  selector: 'app-planner',
  standalone: true,
  imports: [CommonModule],
  template: `
    <header class="header">
      <h1 class="h1">Planner semanal</h1>
      <div class="actions">
        <button class="btn" (click)="resetWeek()">Limpiar semana</button>
      </div>
    </header>

    <!-- Lista vertical por día (móvil 430px) -->
    <section class="days">
      <article class="card day" *ngFor="let d of days; trackBy: trackDay">
        <h2 class="day-title">{{ d }}</h2>

        <div class="meal" *ngFor="let m of meals">
          <div class="meal-head">
            <div class="meal-name">{{ m }}</div>
            <button class="btn primary" (click)="addItem(d, m)">Agregar</button>
          </div>

          <ul class="meal-list" *ngIf="plan[d][m]?.length; else empty">
            <li *ngFor="let item of plan[d][m]; let i = index" class="meal-item">
              <span class="bullet"></span>
              <span class="text">{{ item }}</span>
              <button class="link danger" (click)="removeItem(d, m, i)">Quitar</button>
            </li>
          </ul>

          <ng-template #empty>
            <p class="muted small">Añade una receta</p>
          </ng-template>
        </div>
      </article>
    </section>
  `,
  styles: [`
    .header{display:flex; justify-content:space-between; align-items:center; gap:10px; margin-bottom:10px}
    .actions{display:flex; gap:8px}
    .days{display:grid; gap:12px}
    .day{padding:12px}
    .day-title{margin:0 0 6px 0; font-size:1rem; font-weight:700}
    .meal{border:1px solid var(--border); border-radius:12px; padding:10px; margin-top:10px; background:#fff}
    .meal-head{display:flex; justify-content:space-between; align-items:center; gap:8px; margin-bottom:8px}
    .meal-name{font-weight:600}
    .meal-list{list-style:none; padding:0; margin:0}
    .meal-item{display:flex; align-items:center; gap:8px; padding:6px 0; border-bottom:1px solid #f2f2f2}
    .meal-item:last-child{border-bottom:none}
    .bullet{width:6px; height:6px; border-radius:50%; background:var(--brand); flex:0 0 auto}
    .text{flex:1 1 auto}
    .small{font-size:.9rem}
    .link{background:transparent; border:none; color:var(--brand); padding:4px 6px; border-radius:8px; cursor:pointer}
    .link:hover{text-decoration:underline}
    .danger{color:#e11d48}
  `]
})
export class PlannerComponent implements OnInit {
  days: Day[] = DAYS;
  meals: Meal[] = MEALS;
  plan: WeekPlan = this.newWeek();

  ngOnInit(): void {
    const raw = localStorage.getItem(LS_KEY);
    if (raw) {
      try { this.plan = JSON.parse(raw); } catch { this.plan = this.newWeek(); }
    }
  }

  trackDay = (_: number, d: Day) => d;

  addItem(day: Day, meal: Meal): void {
    const name = prompt(`Agregar a ${meal} de ${day}:`, '');
    if (!name || !name.trim()) return;
    this.plan[day][meal].push(name.trim());
    this.persist();
  }

  removeItem(day: Day, meal: Meal, idx: number): void {
    this.plan[day][meal].splice(idx, 1);
    this.persist();
  }

  resetWeek(): void {
    if (!confirm('¿Vaciar todo el planner de la semana?')) return;
    this.plan = this.newWeek();
    this.persist();
  }

  private persist(): void {
    localStorage.setItem(LS_KEY, JSON.stringify(this.plan));
  }

  private newWeek(): WeekPlan {
    const base: WeekPlan = {};
    for (const d of DAYS) {
      base[d] = { Desayuno: [], Almuerzo: [], Cena: [], Snacks: [] };
    }
    return base;
  }
}
