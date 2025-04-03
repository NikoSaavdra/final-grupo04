import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import * as bootstrap from 'bootstrap';

import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { TipoRestService } from '../tipo-rest.service';
import { ItemDTO } from '../ItemDTO';
import { ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-formularioitem',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './formularioitem.component.html',
  styleUrls: ['./formularioitem.component.css']
})
export class FormularioitemComponent implements OnInit {
  @ViewChild('formRef') formRef!: NgForm;

  item: Item = {
    id: 0,
    titulo: '',
    ubicacion: '',
    fechaAdquisicion: new Date().toISOString().split('T')[0], 
    estado: true,
    tipoId: 0,
    formato: '',
    prestamo: null as any
  };

  resetearFormulario(): void {
    this.item = {
      id: 0,
      titulo: '',
      ubicacion: '',
      fechaAdquisicion: new Date().toISOString().split('T')[0],
      estado: true,
      tipoId: 0,
      formato: '',
      prestamo: null as any
    };
    this.formatosDisponibles = [];
  }
  

  tipos: any[] = [];
  formatosDisponibles: string[] = [];
  itemCreado: any = null;

  constructor(
    private itemRestService: ItemRestService,
    private tipoRestService: TipoRestService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.tipoRestService.listarTipos().subscribe({
      next: (data) => {
        this.tipos = data;
      },
      error: (err) => {
        console.error('Error al cargar tipos:', err);
      }
    });
  }

  cargarFormatos(): void {
    const tipoSeleccionado = this.tipos.find(t => t.id === this.item.tipoId);
    this.formatosDisponibles = tipoSeleccionado ? tipoSeleccionado.formatos : [];
    this.item.formato = '';
  }

  crearItem(): void {
    const nuevoItem: ItemDTO = {
      titulo: this.item.titulo,
      ubicacion: this.item.ubicacion,
      tipoId: this.item.tipoId,
      formato: this.item.formato
    };

    this.itemRestService.crearItem(nuevoItem).subscribe({
      next: (data) => {
        this.itemCreado = data;
        const modal = new bootstrap.Modal(document.getElementById('itemCreadoModal')!);
        modal.show();
      },
      error: (err) => {
        console.error('Error al crear el artÃ­culo:', err);
      }
    });
  }
}