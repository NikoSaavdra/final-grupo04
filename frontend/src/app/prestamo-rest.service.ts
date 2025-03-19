import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Prestamo } from './prestamo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrestamoRestService {

  private apiUrl = 'http://localhost:8080/api/prestamo';

  constructor(private httpClient:HttpClient) { }

  crearPrestamo(itemId: number, persona: string, fechaPrevistaDevolucion: string): Observable<Prestamo> {
    const params = new HttpParams()
      .set('itemId', itemId.toString())
      .set('persona', persona)
      .set('fechaPrevistaDevolucion', fechaPrevistaDevolucion); // Asegúrate de enviar la fecha en formato adecuado

    // Realizamos la solicitud POST
    return this.httpClient.post<Prestamo>(this.apiUrl, params, {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded', // Porque estamos enviando parámetros en la URL
      })
    });
  }

  devolverItem(id: number): Observable<Prestamo> {
    const url = `${this.apiUrl}/devolver/${id}`; // URL con el id del préstamo

    // Realizamos la solicitud PUT
    return this.httpClient.put<Prestamo>(url, null, {  // Usamos null porque no hay cuerpo en la solicitud
      headers: new HttpHeaders({
        'Content-Type': 'application/json', // El tipo de contenido que estamos enviando
      })
    });
  }


  istarPrestamosActivos(persona?: string, fecha?: string): Observable<Prestamo[]> {
    let params = new HttpParams();

    // Si se proporciona el parámetro 'persona', lo agregamos a los parámetros
    if (persona) {
      params = params.set('persona', persona);
    }

    // Si se proporciona el parámetro 'fecha', lo agregamos a los parámetros
    if (fecha) {
      params = params.set('fecha', fecha);
    }

    // Realizamos la solicitud GET con los parámetros opcionales
    return this.httpClient.get<Prestamo[]>(`${this.apiUrl}/activos`, { params });
  }

}
