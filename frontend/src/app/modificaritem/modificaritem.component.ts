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
  };
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private itemRestService: ItemRestService
  ) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.item.id = +params['id'];
      this.obtenerItem();
    });
  }


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

  actualizarItem(): void {
    this.itemRestService.actualizarItem(this.item.id, this.item).subscribe(
      (data) => {
        console.log('Item actualizado:', data);
        this.router.navigate(['/list-items']);
      },
      (error) => {
        console.error('Error al actualizar el item:', error);
        this.errorMessage = 'Error al actualizar el item';
      }
    );
  }
}