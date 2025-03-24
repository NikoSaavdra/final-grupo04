export class PrestamoData {

        constructor(
            public itemId:number,
            public persona:string,
            public fechaPrestamo:string,
            public fechaPrevistaDevolucion:string,
            public fechaDevolucion:string,
            public activo:boolean,
        ){}
}
