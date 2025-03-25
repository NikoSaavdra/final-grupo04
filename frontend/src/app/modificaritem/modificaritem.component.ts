import { Component, OnInit } from '@angular/core';
import { Item } from '../item';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ItemRestService } from '../item-rest.service';
import { Tipo } from '../tipo';
import { Prestamo } from '../prestamo';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modificaritem',
  imports: [FormsModule,RouterLink,CommonModule],
  templateUrl: './modificaritem.component.html',
  styleUrl: './modificaritem.component.css'
})
export class ModificaritemComponent implements OnInit {
  item: Item = {
    id: 0,
    titulo: '',
    ubicacion: '',
    fechaadquisicion: new Date,
    estado: true,
    formato: '',
    prestamo: {} as Prestamo,
    tipoId: 0
  }; // Inicializa con valores predeterminados
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private itemRestService: ItemRestService
  ) { }

  ngOnInit(): void {
    // Obtener el id del item de la URL
    this.route.params.subscribe(params => {
      this.item.id = +params['id'];  // El ID del item es pasado como parámetro
      this.obtenerItem();  // Cargar los detalles del item
    });
  }

  // Método para obtener los detalles del item desde el servidor
  obtenerItem(): void {
    this.itemRestService.getItemById(this.item.id).subscribe(
      (data) => {
        this.item = data;
      },
      (error) => {
        console.error('Error al obtener el item:', error);
        this.errorMessage = 'No se pudo cargar el item';
      }
    );
  }

  // Método para actualizar el item
  actualizarItem(): void {
    this.itemRestService.actualizarItem(this.item.id, this.item).subscribe(
      (data) => {
        console.log('Item actualizado:', data);
        this.router.navigate(['/list-items']);  // Redirigir a la lista de items después de la actualización
      },
      (error) => {
        console.error('Error al actualizar el item:', error);
        this.errorMessage = 'Error al actualizar el item';
      }
    );
  }
}