import { Item } from "./item";

export class Prestamo {

    constructor(
        public itemId:number,
        public persona:string,
        public fecha:string,
        public fechaprestamo:string,
        public fechaprevistadevolucion:string,
        public fechadevolucion:string,
        public activo:boolean,
        public items:Item[],
    ){}
}
