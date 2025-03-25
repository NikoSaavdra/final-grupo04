import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Prestamo } from './prestamo';
import { map, Observable } from 'rxjs';
import { DatePipe } from '@angular/common';
import { PrestamoData } from './prestamo-data';
import { Item } from './item';

@Injectable({
  providedIn: 'root'
})
export class PrestamoRestService {

  private apiUrl = "http://localhost:4200/api/prestamo";

  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  crearPrestamo(itemId: number, persona: string, fechaPrevistaDevolucion: string): Observable<Prestamo> {
    return this.http.post<Prestamo>(this.apiUrl, {HttpParams})
  }

  devolverItem(id: number): Observable<Prestamo> {
    const url = `${this.apiUrl}/devolver/${id}`; // URL con el id del préstamo

    // Realizamos la solicitud PUT
    return this.http.put<Prestamo>(url, null, {  // Usamos null porque no hay cuerpo en la solicitud
      headers: new HttpHeaders({
        'Content-Type': 'application/json', // El tipo de contenido que estamos enviando
      })
    });
  }


  listarPrestamosActivos(persona?: string, fecha?: string | null): Observable<Prestamo[]> {

    let params = new HttpParams();
    if (persona) {
      params = params.set('persona', persona);
    }
    if (fecha) {
      params = params.set('fecha', fecha);
    }
    return this.http.get<PrestamoData[]>(`${this.apiUrl}/activos`, { params }).pipe(
      map(prestamos => {
        return prestamos.map(prestamo => {
          let fechaPrestamo = this.convertirFecha(prestamo.fechaPrestamo);
          let fechaDevolucion = this.convertirFecha(prestamo.fechaDevolucion);
          let fechaPrevistaDevolucion = this.convertirFecha(prestamo.fechaPrevistaDevolucion);

          // Si alguna fecha es null, asignar una fecha predeterminada (ej. nueva fecha)
          fechaPrestamo = fechaPrestamo ?? new Date();
          fechaDevolucion = fechaDevolucion ?? new Date();
          fechaPrevistaDevolucion = fechaPrevistaDevolucion ?? new Date();

          return new Prestamo(
            prestamo.itemId,
            prestamo.persona,
            fechaPrestamo,
            fechaPrevistaDevolucion,
            fechaDevolucion,
            prestamo.activo
          );
        });
      })
    )
  }
  public convertirFecha(fecha: string | null): Date | null {
    // Verificar si la fecha es nula o vacía antes de procesarla
    if (fecha && fecha.trim() !== '') {
      let trozosFechas: string[] = fecha.split("-");
      if (trozosFechas.length === 3) {
        // Si la fecha tiene el formato correcto, la procesamos
        return new Date(parseInt(trozosFechas[0]), parseInt(trozosFechas[1]) - 1, parseInt(trozosFechas[2]));
      }
    }
    return null;  // Si la fecha es nula o no tiene el formato adecuado, devolvemos null
  }

private convertirFechaCrear (fecha: string | null): Date | null {
  if (fecha && typeof fecha === 'string' && fecha.trim() !== '') {
    let trozosFechas: string[] = fecha.split("-");
    if (trozosFechas.length === 3) {
      return new Date(parseInt(trozosFechas[0]), parseInt(trozosFechas[1]) - 1, parseInt(trozosFechas[2]));
    }
  }
  return null; // Si la fecha no es válida, retornar null
}
}
