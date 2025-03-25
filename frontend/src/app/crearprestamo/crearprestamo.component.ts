import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crearprestamo',
  imports: [FormsModule,RouterLink],
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
  let fechaPrevistaDevolucion = this.prestamo.fechaPrevistaDevolucion.toISOString();;

  if (fechaPrevistaDevolucion) {
    fechaPrevistaDevolucion = fechaPrevistaDevolucion; // Or use toLocaleDateString() if needed
  } else {
    fechaPrevistaDevolucion = ''; // Or handle null as needed (e.g., set an empty string or handle the error)
  }

    this.prestamoRestService.crearPrestamo(itemId, persona, fechaPrevistaDevolucion).subscribe((datos)=>{
        console.log("Prestamo insertado");
        this.router.navigate(["/listaprestamos"]);
      });
  }

}