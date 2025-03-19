import { Component, OnInit } from '@angular/core';

import { ItemRestService } from '../item-rest.service';
import { Item } from '../item';
import { Prestamo } from '../prestamo';
import { Tipo } from '../tipo';

@Component({
  selector: 'app-listaitems',
  imports: [],
  templateUrl: './listaitems.component.html',
  styleUrls: ['./listaitems.component.css']
})
export class ItemComponent implements OnInit {

  items: Item[] = [];  // Almacenará la lista de ítems
  item: Item = { } as Item  // Formulario para crear un ítem
  mensaje: string = '';  // Mensaje para mostrar al usuario
  tituloBusqueda: string = '';  // Filtro de búsqueda por título
  tipoBusqueda: string = '';  // Filtro de búsqueda por tipo
  ubicacionBusqueda: string = '';  // Filtro de búsqueda por ubicación

  constructor(private itemService: ItemRestService) { }

  ngOnInit(): void {
    this.listarItems();  // Cargar los ítems cuando se inicialice el componente
  }

  // Método para crear un nuevo ítem
  crearItem(): void {
    this.itemService.crearItem(this.item).subscribe(
      (response: any) => {
        this.mensaje = 'Item creado exitosamente!';
        console.log(response);
        this.listarItems();  // Actualizar la lista después de crear el ítem
      },
      (error: any) => {
        this.mensaje = 'Error al crear el item';
        console.error(error);
      }
    );
  }

  // Método para listar los ítems disponibles
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

  // Método para buscar ítems con filtros
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

  // Método para actualizar un ítem
  actualizarItem(id: number): void {
    const itemToUpdate: Item = new Item{titulo: string, ubicacion: string, estado: boolean, tipo: Tipo, prestamo: Prestamo}; // Ejemplo de datos para actualización
    this.itemService.actualizarItem(id, itemToUpdate).subscribe(
      (response:any) => {
        this.mensaje = 'Item actualizado exitosamente!';
        console.log(response);
        this.listarItems();  // Actualizar la lista después de actualizar el ítem
      },
      (error:any) => {
        this.mensaje = 'Error al actualizar el item';
        console.error(error);
      }
    );
  }

  // Método para eliminar un ítem
  eliminarItem(id: number): void {
    this.itemService.eliminarItem(id).subscribe(
      () => {
        this.mensaje = 'Item eliminado exitosamente!';
        this.listarItems();  // Actualizar la lista después de eliminar el ítem
      },
      (error:any) => {
        this.mensaje = 'Error al eliminar el item';
        console.error(error);
      }
    );
  }
}

