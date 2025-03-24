import { Prestamo } from "./prestamo";
import { Tipo } from "./tipo";

export class Item {

    constructor(

        public id:number,
        public titulo:string,
        public ubicacion:string,
        public fechaadquisicion : Date,
        public estado:boolean,
        public tipoId:number,
        public formato :string,
        public prestamo:Prestamo
    ){}

    public static fromJson(json: any): Item {
        const item = new Item(
            json.id,
            json.titulo,
            json.ubicacion,
            new Date(json.fechaadquisicion), // Aseguramos que la fecha se convierta correctamente
            json.estado,
            json.tipoId,
            json.formato,
            json.prestamo
        );
        return item;
    }
}