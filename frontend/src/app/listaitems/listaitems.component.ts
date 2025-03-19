import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { Tipo } from '../tipo';
import { Prestamo } from '../prestamo';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listaitems',
  imports: [CommonModule],
  templateUrl: './listaitems.component.html',
  styleUrl: './listaitems.component.css'
})
export class ListaitemsComponent {
  items: Item[] = [];
  item: Item = { } as Item
  mensaje: string = '';
  tituloBusqueda: string = '';
  tipoBusqueda: string = '';
  ubicacionBusqueda: string = '';

  constructor(private itemService: ItemRestService) { }

  ngOnInit(): void {
    this.listarItems();
  }

//metodo a componente formularioitem
  crearItem(): void {
    this.itemService.crearItem(this.item).subscribe(
      (response: any) => {
        this.mensaje = 'Item creado exitosamente!';
        console.log(response);
        this.listarItems();
      },
      (error: any) => {
        this.mensaje = 'Error al crear el item';
        console.error(error);
      }
    );
  }


  listarItems(): void {
    this.itemService.listarItemsDisponibles().subscribe(
      (response:any) => {
        this.items = response;
      },
      (error:any) => {
        console.error('Error al listar los ítems', error);
      }
    );
  }


  buscarItems(): void {
    this.itemService.buscarItems(this.tituloBusqueda, this.tipoBusqueda, this.ubicacionBusqueda).subscribe(
      (response:any) => {
        this.items = response;
      },
      (error:any) => {
        console.error('Error al buscar ítems', error);
      }
    );
  }

  eliminarItem(id: number): void {
    this.itemService.eliminarItem(id).subscribe(
      () => {
        this.mensaje = 'Item eliminado exitosamente!';
        this.listarItems();
      },
      (error:any) => {
        this.mensaje = 'Error al eliminar el item';
        console.error(error);
      }
    );
  }
}
