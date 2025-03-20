import { Component } from '@angular/core';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';

@Component({
  selector: 'app-listaprestamos',
  imports: [],
  templateUrl: './listaprestamos.component.html',
  styleUrl: './listaprestamos.component.css'
})
export class ListaprestamosComponent {
  listaPrestamosActivos: Prestamo[]=[];

  constructor(private prestamoRestService: PrestamoRestService){

    prestamoRestService.listarPrestamosActivos().subscribe((datos)=>{
      this.listaPrestamosActivos=datos;
    })
  }

  devolverItem(id: number){
    this.prestamoRestService.devolverItem(id).subscribe(()=>{
      this.prestamoRestService.listarPrestamosActivos().subscribe((datos)=>{
        this.listaPrestamosActivos=datos;
      })
    })
  }

}
