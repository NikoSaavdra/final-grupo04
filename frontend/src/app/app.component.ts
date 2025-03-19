import { Component } from '@angular/core';
import { ItemComponent } from './listaitems/listaitems.component';
import { RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,AppComponent,ItemComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'coleccion-app';
}
