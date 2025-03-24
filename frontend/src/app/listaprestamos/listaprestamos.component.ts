import { Component, Inject, LOCALE_ID } from '@angular/core';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';
import { CommonModule, DatePipe, formatDate } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-listaprestamos',
  imports: [CommonModule, RouterLink],
  templateUrl: './listaprestamos.component.html',
  styleUrl: './listaprestamos.component.css'
})
export class ListaprestamosComponent {
  listaPrestamosActivos: Prestamo[] = [];
  currentDate = new Date();


  constructor(private prestamoRestService: PrestamoRestService, private datePipe: DatePipe,
    @Inject(LOCALE_ID)private locale:string) {}


    ngOnInit(): void {
    this.prestamoRestService.listarPrestamosActivos().subscribe((datos) => {
      this.listaPrestamosActivos = datos;
    });
  }

  getFormattedDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/yyyy') || '';
  }

  trackById(index: number, item: any): number {
    return item.id; // Asumimos que el identificador único es 'id'
  }

  devolverItem(id: number) {
    this.prestamoRestService.devolverItem(id).subscribe(() => {
      this.prestamoRestService.listarPrestamosActivos().subscribe((datos) => {
        this.listaPrestamosActivos = datos;
      });
    });
  }
}