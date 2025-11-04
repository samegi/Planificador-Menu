import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
export interface Receta{ id:number; nombre:string; descripcion?:string; imagenUrl?:string; }
@Injectable({providedIn:'root'})
export class RecetaService{
  constructor(private http:HttpClient){}
  listar():Observable<Receta[]>{ return this.http.get<Receta[]>('assets/recetas.json'); }
  porId(id:number):Observable<Receta|undefined>{ return this.listar().pipe(map(xs=>xs.find(x=>x.id===id))); }
}
