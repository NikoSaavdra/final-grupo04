import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-listaitems',
  imports: [CommonModule, RouterLink],
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

  buscarItems(): void {
    this.itemRestService.buscarItems(this.tituloBusqueda, this.tipoBusqueda, this.ubicacionBusqueda).subscribe(
      (response: any) => {
        this.item = response;
      },
      (error: any) => {
        console.error('Error al buscar ítems', error);
      }
    );
  }
}