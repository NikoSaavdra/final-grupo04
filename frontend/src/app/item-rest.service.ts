import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from './item';
import { map, Observable } from 'rxjs';
import { Prestamo } from './prestamo';
import { ItemData } from './item-data';
import { Tipo } from './tipo';

@Injectable({
  providedIn: 'root'
})
export class ItemRestService {

  private apiUrl = "http://localhost:4200/api/item";

  constructor(private http: HttpClient) { }

  

  listarItems(): Observable<Item[]> {
    return this.http.get<ItemData[]>(this.apiUrl).pipe(
      
      map(items => {
        return items.map(item => {
        
          let trozosFechas:string[]=item.fechaAdquisicion.split("-");
          let fechaAdquisicion= new Date(parseInt(trozosFechas[0]),parseInt(trozosFechas[1])-1,parseInt(trozosFechas[2]));
          return new Item(item.id,item.titulo,item.ubicacion,fechaAdquisicion,item.estado,item.tipoId,item.formato,{} as Prestamo)
        });
      })
    );
  }
  

  public crearItem(item: Item): Observable<Item> {
    return this.http.post<Item>(this.apiUrl, item)
  }

  buscarItems(titulo?: string, tipo?: string, ubicacion?: string, ordenarPor: string = 'titulo'): Observable<Item[]> {
    let params = new HttpParams();
    
    if (titulo) {
      params = params.set('titulo', titulo);
    }
    if (tipo) {
      params = params.set('tipo', tipo);
    }
    if (ubicacion) {
      params = params.set('ubicacion', ubicacion);
    }
    params = params.set('ordenarPor', ordenarPor);
    
    return this.http.get<Item[]>(`${this.apiUrl}/buscar`, { params });
  }


  actualizarItem(id: number, itemDetails: Item): Observable<Item> {
    const url = `${this.apiUrl}/${id}`;

    return this.http.put<Item>(url, itemDetails, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
  getItemById(id: number): Observable<Item> {
    return this.http.get<Item>(`${this.apiUrl}/${id}`);
  }

  eliminarItem(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;


    return this.http.delete<void>(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
}