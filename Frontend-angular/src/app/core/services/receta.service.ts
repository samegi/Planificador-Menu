import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Receta {
  id: number;
  nombre: string;
  descripcion?: string;
}

@Injectable({ providedIn: 'root' })
export class RecetaService {
  // usa proxy si lo correrás con --proxy-config; si no, pon http://localhost:8080/api/recetas
  private baseUrl = '/api/recetas';

  constructor(private http: HttpClient) {}

  listar(): Observable<Receta[]> {
    return this.http.get<Receta[]>(this.baseUrl);
  }
}
