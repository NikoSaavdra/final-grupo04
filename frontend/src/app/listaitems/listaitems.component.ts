import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Tipo } from '../tipo';
import * as bootstrap from 'bootstrap';
import { ModificaritemComponent } from '../modificaritem/modificaritem.component';
import { ChangeDetectorRef } from '@angular/core';
import { EliminarItemComponent } from '../eliminarItem/eliminarItem.component';


@Component({
  selector: 'app-listaitems',
  imports: [CommonModule, RouterLink, FormsModule, ModificaritemComponent, EliminarItemComponent],
  templateUrl: './listaitems.component.html', 
  styleUrls: ['./listaitems.component.css'],
})
export class ListaitemsComponent {
  listaItems: Item[] = [];
  item: Item = {} as Item;
  tituloBusqueda: string = '';
  tipoBusqueda: string = '';
  ubicacionBusqueda: string = '';
  itemSeleccionado: Item | null = null;
  itemAEliminar: Item | null = null;

  paginaActual: number = 0;
  totalPaginas: number = 0;
  tamanoPagina: number = 5;

  constructor(private itemRestService: ItemRestService, private cdr: ChangeDetectorRef) {
    this.cargarEstado();
    this.buscar();
  }

  buscar(): void {
    this.itemRestService
      .buscarItemsPaginado(this.tituloBusqueda, this.tipoBusqueda, this.ubicacionBusqueda, this.paginaActual, this.tamanoPagina)
      .subscribe(
        (response: any) => {
          
          this.listaItems = response.content;
          this.totalPaginas = response.totalPages;
        },
        (error: any) => {
          console.error('Error al buscar ítems paginados', error);
        }
      );
  }

  cambiarPagina(index: number): void {
    this.paginaActual = index;
    this.buscar();
  }

  avanzarPagina(): void {
    if (this.paginaActual < this.totalPaginas - 1) {
      this.paginaActual++;
      this.buscar();
    }
  }

  retrocederPagina(): void {
    if (this.paginaActual > 0) {
      this.paginaActual--;
      this.buscar();
    }
  }

  trackById(index: number, item: any): any {
    return item.id;
  }

  abrirModal(item: any): void {
    const modalElement = document.getElementById('visualizarModal');
  
    // Cierra si ya estaba abierto
    if (modalElement) {
      const instanciaExistente = bootstrap.Modal.getInstance(modalElement);
      instanciaExistente?.hide();
    }
  
    this.itemSeleccionado = null; // forzar reinicio
    this.cdr.detectChanges();
  
    setTimeout(() => {
      this.itemSeleccionado = { ...item }; // seleccionar nuevo
      this.cdr.detectChanges(); // asegurar actualización visual
      const modal = new bootstrap.Modal(modalElement!);
      modal.show();
    }, 100); // un pequeño delay ayuda
  }
  
  

  cerrarModal(): void {
    this.itemSeleccionado = null;
    const modal = document.getElementById('visualizarModal');
    if (modal) {
      const modalInstance = bootstrap.Modal.getInstance(modal);
      modalInstance?.hide();
    }
  }

  abrirModalEditar(item: Item): void {
    this.itemSeleccionado = { ...item };
    setTimeout(() => {
      const modalElement = document.getElementById('modificarItemModal');
      if (modalElement) {
        const modal = new bootstrap.Modal(modalElement, { backdrop: 'static' });
        modal.show();
      }
    }, 0); // asegura que Angular renderizó
  }
  

  cerrarModalEditar(): void {
    const modalElement = document.getElementById('modificarItemModal');
    if (modalElement) {
      const modalInstance = bootstrap.Modal.getInstance(modalElement);
      modalInstance?.hide();
    }
    // Limpieza manual de backdrop (por si queda pegado)
    document.body.classList.remove('modal-open');
    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
    this.itemSeleccionado = null;
  }
  

  abrirModalEliminar(item: Item): void {
    this.itemAEliminar = null; 
    const modalElement = document.getElementById('confirmarEliminarModal');
    const modalInstance = bootstrap.Modal.getInstance(modalElement!);
    modalInstance?.hide();
    this.itemAEliminar = { ...item };
    setTimeout(() => {
      const newModal = new bootstrap.Modal(modalElement!);
      newModal.show();
    }, 200);
  }

  confirmarEliminar(): void {
    if (this.itemAEliminar?.id !== undefined) {
      this.borrar(this.itemAEliminar.id);
      this.cerrarModalEliminar();
    } else {
      console.error('El ID del ítem a eliminar no está definido.');
    }
  }
  

  cerrarModalEliminar(): void {
    const modal = document.getElementById('confirmarEliminarModal');
    if (modal) {
      const bootstrapModal = bootstrap.Modal.getInstance(modal);
      bootstrapModal?.hide();
    }
    this.itemAEliminar = null;
  }

  borrar(id: number): void {
    
    if (this.itemAEliminar && this.itemAEliminar.estado === false) {
      const modalAdvertencia = new bootstrap.Modal(document.getElementById('modalNoEliminar')!);
      modalAdvertencia.show();
      return;
    }

    this.itemRestService.eliminarItem(id).subscribe({
      next: () => {
        this.listaItems = this.listaItems.filter(item => item.id !== id);
        this.itemAEliminar = null;
      },
      error: (err) => {
        console.error('Error al eliminar el ítem:', err);
      },
    });
  }

  actualizarLista(itemEditado: Item): void {
    const index = this.listaItems.findIndex(i => i.id === itemEditado.id);
    if (index !== -1) {
      this.listaItems[index] = itemEditado;
    } else {
      this.listaItems.push(itemEditado);
    }
  }

  guardarCambios(): void {
    if (this.itemSeleccionado) {
      const index = this.listaItems.findIndex(item => item.id === this.itemSeleccionado?.id);
      if (index !== -1) {
        // 🔁 Actualiza los campos editables directamente
        this.listaItems[index].titulo = this.itemSeleccionado.titulo;
        this.listaItems[index].ubicacion = this.itemSeleccionado.ubicacion;
        this.listaItems[index].estado = this.itemSeleccionado.estado;
        // Reemplazamos el item editado en la lista
      this.listaItems[index] = { ...this.itemSeleccionado };
      }
  
      // ✅ Cierra el modal de edición y refresca la tabla
      this.cerrarModalEditar();
      this.cdr.detectChanges();
    }
  }
  

  guardarEstado(): void {
    const estado = {
      listaItems: this.listaItems,
      tituloBusqueda: this.tituloBusqueda,
      tipoBusqueda: this.tipoBusqueda,
      ubicacionBusqueda: this.ubicacionBusqueda,
    };
    sessionStorage.setItem('estadoTabla', JSON.stringify(estado));
  }

  cargarEstado(): void {
    const estadoGuardado = sessionStorage.getItem('estadoTabla');
    if (estadoGuardado) {
      const estado = JSON.parse(estadoGuardado);
      this.listaItems = estado.listaItems || [];
      this.tituloBusqueda = estado.tituloBusqueda || '';
      this.tipoBusqueda = estado.tipoBusqueda || '';
      this.ubicacionBusqueda = estado.ubicacionBusqueda || '';
    }
  }

  navegarAtras(): void {
    this.guardarEstado();
    window.history.back();
  }
  
}