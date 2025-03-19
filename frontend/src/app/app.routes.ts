import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ListaitemsComponent } from './listaitems/listaitems.component';
import { BrowserModule } from '@angular/platform-browser';

export const routes: Routes = [
    { path: '', component:AppComponent },  // Ruta inicial
  { path: 'listaitems', component: ListaitemsComponent },  // Ruta a tu componente de lista de ítems
];



