import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from './item';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemRestService {

  private apiUrl = 'http://localhost:8080/api/items';

  constructor(private httpClient:HttpClient) { }
  
  crearItem(item:Item):Observable<Item>{
    return this.httpClient.post<Item>(this.apiUrl, item, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }

  listarItemsDisponibles(): Observable<Item[]> {
    return this.httpClient.get<Item[]>(this.apiUrl, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }

  buscarItems(titulo?: string, tipo?: string, ubicacion?: string): Observable<Item[]> {
    let params = new HttpParams();

    // Solo agregamos los parámetros si no son nulos o indefinidos
    if (titulo) {
      params = params.set('titulo', titulo);
    }
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    if (ubicacion) {
      params = params.set('ubicacion', ubicacion);
    }

    // Realizamos la solicitud GET con los parámetros
    return this.httpClient.get<Item[]>(this.apiUrl, { params: params });
  }

  actualizarItem(id: number, itemDetails: Item): Observable<Item> {
    const url = `${this.apiUrl}/${id}`; // Construimos la URL con el id del item

    // Realizamos la solicitud PUT con el id y el cuerpo del item a actualizar
    return this.httpClient.put<Item>(url, itemDetails, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }


  eliminarItem(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`; // Construimos la URL con el id del item

    // Realizamos la solicitud DELETE con el id del item
    return this.httpClient.delete<void>(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
}
