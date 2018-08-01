import { Moment } from 'moment';
import { ISector } from 'app/shared/model//sector.model';

export interface IZonal {
    id?: number;
    name?: string;
    date?: Moment;
    zonalSectors?: ISector[];
}

export class Zonal implements IZonal {
    constructor(public id?: number, public name?: string, public date?: Moment, public zonalSectors?: ISector[]) {}
}
