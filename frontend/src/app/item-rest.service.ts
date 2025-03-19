import { HttpClient, HttpHeaders } from '@angular/common/http';
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
}
