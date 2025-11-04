import { Injectable } from '@angular/core';
export interface User { username:string; name:string; }
const LS_USER = 'mf-user';
@Injectable({ providedIn:'root' })
export class AuthService {
  private _user:User|null=null;
  constructor(){ const raw=localStorage.getItem(LS_USER); if(raw){try{this._user=JSON.parse(raw)}catch{}} }
  user(){ return this._user; }
  isLoggedIn(){ return !!this._user; }
  login(u:string,p:string){ if(!u.trim()||!p.trim()) return false; this._user={username:u,name:u}; localStorage.setItem(LS_USER,JSON.stringify(this._user)); return true; }
  register(u:string,_a:string,p:string,rp:string){ if(!u||!p||p!==rp) return false; return this.login(u,p); }
  logout(){ this._user=null; localStorage.removeItem(LS_USER); }
}
