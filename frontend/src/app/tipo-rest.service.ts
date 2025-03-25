import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tipo } from './tipo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TipoRestService {

  private apiUrl = 'http://localhost:4200/api/tipo';

  constructor(private httpClient:HttpClient) { }

  crearTipo(tipo: Tipo): Observable<Tipo> {
    const url = `${this.apiUrl}`;


    return this.httpClient.post<Tipo>(url, tipo, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }

  listarTipos(): Observable<Tipo[]> {
    return this.httpClient.get<Tipo[]>(this.apiUrl);
  }

  actualizarTipo(id: number, nombre: string): Observable<Tipo> {
    const url = `${this.apiUrl}/${id}`;


    return this.httpClient.put<Tipo>(url, { nombre }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }


  eliminarTipo(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;


    return this.httpClient.delete<void>(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }

}
