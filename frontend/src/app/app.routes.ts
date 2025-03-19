import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ItemComponent } from './listaitems/listaitems.component';



export const routes: Routes = [
    { path: '', component: AppComponent },
    { path: "listaitems", component:ItemComponent },
];
