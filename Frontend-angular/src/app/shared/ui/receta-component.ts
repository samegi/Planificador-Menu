import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Receta } from '../../core/services/receta.service';

@Component({
  selector: 'app-receta-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <article class="card">
      <div class="img" *ngIf="r?.imagenUrl; else ph" [style.backgroundImage]="'url('+r!.imagenUrl+')'"></div>
      <ng-template #ph><div class="img ph"></div></ng-template>

      <div class="body">
        <h3>{{ r?.nombre || '(sin nombre)' }}</h3>
        <p>{{ r?.descripcion || 'Sin descripción' }}</p>
        <div class="meta">ID: {{ r?.id }}</div>
        <button class="btn" (click)="agregar.emit(r!.id)">Agregar al planner</button>
      </div>
    </article>
  `,
  styles: [`
    .card{border:1px solid #eee;border-radius:16px;overflow:hidden;background:#fff;box-shadow:0 8px 20px rgba(0,0,0,.04);display:flex;flex-direction:column}
    .img{height:140px;background:#f4f6f8;background-size:cover;background-position:center}
    .img.ph{background:linear-gradient(180deg,#f5f5f5,#ededed)}
    .body{padding:12px 14px}
    h3{font-size:1rem;margin:0 0 6px 0}
    p{font-size:.85rem;color:#555;margin:0 0 8px 0;line-height:1.35}
    .meta{font-size:.72rem;color:#9aa0a6}
    .btn{margin-top:8px;padding:6px 10px;border:1px solid #ddd;border-radius:10px;background:#fafafa;cursor:pointer}
  `]
})
export class RecetaCardComponent {
  @Input() r!: Receta;
  // Nota: si luego quieres propagar evento: @Output() agregar = new EventEmitter<number>();
  agregar = { emit: (_:number)=>{} };
}
