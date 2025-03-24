import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crearprestamo',
  imports: [FormsModule],
  templateUrl: './crearprestamo.component.html',
  styleUrl: './crearprestamo.component.css'
})
export class CrearprestamoComponent {

  prestamo: Prestamo={}as Prestamo;

  constructor(private prestamoRestService:PrestamoRestService, private router:Router){
    }
  public  crearPrestamo(){
    const itemId = this.prestamo.itemId; // Asumiendo que prestamo es un objeto con un campo itemId
  const persona = this.prestamo.persona;
  const fechaPrevistaDevolucion = this.prestamo.fechaprevistadevolucion.toString();

    this.prestamoRestService.crearPrestamo(itemId, persona, fechaPrevistaDevolucion).subscribe((datos)=>{
        console.log("Prestamo insertado");
        this.router.navigate(["/listaprestamos"]);
      });
  }

}