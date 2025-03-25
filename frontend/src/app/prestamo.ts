import { Item } from "./item";

export class Prestamo {

    constructor(
        public itemId:number,
        public persona:string,
        public fechaPrestamo:Date ,
        public fechaPrevistaDevolucion:Date ,
        public fechaDevolucion:Date,
        public activo:boolean,
    ){}
}
