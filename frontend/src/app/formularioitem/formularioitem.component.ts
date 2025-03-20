import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemRestService } from '../item-rest.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formularioitem',
  imports: [FormsModule],
  templateUrl: './formularioitem.component.html',
  styleUrl: './formularioitem.component.css'
})
export class FormularioitemComponent {

  item:Item={}as Item;

  constructor(private itemRestService:ItemRestService, private router:Router){
  }
 public  crearItem(){
    this.itemRestService.crearItem(this.item).subscribe((datos)=>{
        console.log("Item insertado");
        this.router.navigate([""]);
      });
  }
  }

