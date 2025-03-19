import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { Tipo } from '../tipo';
import { Prestamo } from '../prestamo';

@Component({
  selector: 'app-listaitems',
  imports: [],
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


  ctualizarItem(id: number): void {

    const itemToUpdate: Item = {
      id: id,
      titulo: 'Nuevo Titulo',
      ubicacion: 'Nueva Ubicacion',
      estado: true,
      tipo: new Tipo(id,'','',[]),
      prestamo: new Prestamo(id,'',fechaDate,'','',true,[]),
    };
  
    this.itemService.actualizarItem(id, itemToUpdate).subscribe(
      (response: any) => {
        this.mensaje = 'Item actualizado exitosamente!';
        console.log(response);
        this.listarItems();
      },
      (error: any) => {
        this.mensaje = 'Error al actualizar el item';
        console.error(error);
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
