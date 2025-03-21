import { Component, Inject, LOCALE_ID } from '@angular/core';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';
import { CommonModule, DatePipe, formatDate } from '@angular/common';

@Component({
  selector: 'app-listaprestamos',
  imports: [CommonModule],
  templateUrl: './listaprestamos.component.html',
  styleUrl: './listaprestamos.component.css'
})
export class ListaprestamosComponent {
  listaPrestamosActivos: Prestamo[] = [];
  fechaprestamo: Date = new Date();
  currentDate = new Date();
dateString:string;


  constructor(private prestamoRestService: PrestamoRestService, private datePipe: DatePipe,
    @Inject(LOCALE_ID)private locale:string) {
    prestamoRestService.listarPrestamosActivos().subscribe((datos) => {
      this.listaPrestamosActivos = datos;
    });
    this.dateString=formatDate(Date.now(),'yyyy-MM-dd',this.locale);
    //MIRAR ESTO -> formatDate = formatDate(this.fechaprestamo, 'yyyy-MM-dd', this.locale);
    console.log(formatDate);
  }

  getFormattedDate(dateString: string): string {
    // Utiliza el DatePipe para formatear la fecha (por ejemplo, 'dd/MM/yyyy')
    return this.datePipe.transform(dateString, 'dd/MM/yyyy') || '';
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
