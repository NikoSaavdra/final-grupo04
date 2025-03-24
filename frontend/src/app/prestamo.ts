import { Item } from "./item";

export class Prestamo {

    constructor(
        public itemId:number,
        public persona:string,
        public fechaPrestamo:Date |null,
        public fechaPrevistaDevolucion:Date |null,
        public fechaDevolucion:Date |null,
        public activo:boolean,
    ){}
}
