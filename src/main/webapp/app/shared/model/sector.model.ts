import { Moment } from 'moment';

export interface ISector {
    id?: number;
    name?: string;
    date?: Moment;
    content?: string;
    zonalName?: string;
    zonalId?: number;
}

export class Sector implements ISector {
    constructor(
        public id?: number,
        public name?: string,
        public date?: Moment,
        public content?: string,
        public zonalName?: string,
        public zonalId?: number
    ) {}
}
