import { Item } from "./item";

export class Prestamo {

    constructor(
        public id:number,
        public persona:string,
        public fechaprestamo:Date,
        public fechaprevistadevolucion:Date,
        public fechadevolucion:Date,
        public activo:boolean,
        public items:Item[],
    ){}
}
