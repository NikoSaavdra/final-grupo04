import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Prestamo } from '../prestamo';
import { PrestamoRestService } from '../prestamo-rest.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crearprestamo',
  imports: [FormsModule, RouterLink],
  templateUrl: './crearprestamo.component.html',
  styleUrl: './crearprestamo.component.css'
})
export class CrearprestamoComponent {

  prestamo: Prestamo = {} as Prestamo;
  fechadev: any = { fechaPrevistaDevolucion: '2025-03-25' };

  constructor(private prestamoRestService: PrestamoRestService, private router: Router) {
  }

  crearPrestamo(){
      const itemId = this.prestamo.itemId;
      const persona = this.prestamo.persona;
      let fechadev: string | null = null;
    
      console.log(this.prestamo);

      this.prestamoRestService.crearPrestamo(itemId, persona, this.fechadev).subscribe(
        (datos) => {
          console.log("Prestamo insertado", datos);
          this.router.navigate(["/listaprestamos"]);
        },
        (error) => {
          console.error('Error al crear el préstamo:', error);
          if (error.error) {
            console.error('Detalles del error:', error.error);
          }
        }
      );
}
  }