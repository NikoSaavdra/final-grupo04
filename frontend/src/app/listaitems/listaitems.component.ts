import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listaitems',
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './listaitems.component.html',
  styleUrl: './listaitems.component.css'
})
export class ListaitemsComponent {
  listaItems: Item[] = [];
  item: Item = { } as Item
  tituloBusqueda: string = '';
  tipoBusqueda: string = '';
  ubicacionBusqueda: string = '';
  

  constructor(private itemRestService: ItemRestService) {

    itemRestService.listarItems().subscribe((datos) => {
      this.listaItems = datos;
    })
  }

  obtenerEstado(tipoId: string): string {
    if (tipoId === 'libro') {
      return 'Disponible';
    }
    if (tipoId === 'revista') {
      return 'En revisión';
    }
    if (tipoId === 'dvd') {
      return 'En alquiler';
    }
    return 'Estado desconocido';
  }

  borrar(id: number){
    this.itemRestService.eliminarItem(id).subscribe(()=>{
      this.itemRestService.buscarItems().subscribe((datos)=>{
        this.listaItems=datos;
      })
    })
  }

  buscar(): void {

    const filtroTitulo = this.tituloBusqueda ? this.tituloBusqueda : '';
    const filtroTipo = this.tipoBusqueda ? this.tipoBusqueda : '';
    const filtroUbicacion = this.ubicacionBusqueda ? this.ubicacionBusqueda : '';
    
    this.itemRestService.buscarItems(this.tituloBusqueda, this.tipoBusqueda, this.ubicacionBusqueda).subscribe(
      (response: any) => {
        console.log(response);
        this.listaItems = response;
      },
      (error: any) => {
        console.error('Error al buscar ítems', error);
      }
    );
  }
}