import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tipo } from './tipo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TipoRestService {

  private apiUrl = 'http://localhost:8080/api/tipos';

  constructor(private httpClient:HttpClient) { }

  crearTipo(tipo: Tipo): Observable<Tipo> {
    const url = `${this.apiUrl}`;  // URL para crear un nuevo tipo

    // Realizamos la solicitud POST
    return this.httpClient.post<Tipo>(url, tipo, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json', // Indicamos que el contenido es JSON
      })
    });
  }

  listarTipos(): Observable<Tipo[]> {
    return this.httpClient.get<Tipo[]>(this.apiUrl); // Realizamos la solicitud GET
  }

  actualizarTipo(id: number, nombre: string): Observable<Tipo> {
    const url = `${this.apiUrl}/${id}`;  // La URL con el id para actualizar un tipo

    // Realizamos la solicitud PUT
    return this.httpClient.put<Tipo>(url, { nombre }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json', // Indicamos que el contenido es JSON
      })
    });
  }


  eliminarTipo(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;  // URL con el ID para eliminar el tipo

    // Realizamos la solicitud DELETE
    return this.httpClient.delete<void>(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json', // Indicamos que el contenido es JSON
      })
    });
  }

}
