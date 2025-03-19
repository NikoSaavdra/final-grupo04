import { Component } from '@angular/core';

import { RouterOutlet } from '@angular/router';
import { ListaitemsComponent } from './listaitems/listaitems.component';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ListaitemsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'coleccion-app';
}
