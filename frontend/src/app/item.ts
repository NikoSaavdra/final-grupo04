import { Prestamo } from "./prestamo";
import { Tipo } from "./tipo";

export class Item {

    constructor(

    public id:number,
        public titulo:string,
        public ubicacion:string,
        public estado:boolean,
        public tipo:Tipo,
        public prestamo:Prestamo
    ){}
}
