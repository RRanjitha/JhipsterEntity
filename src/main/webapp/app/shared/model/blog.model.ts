export interface IBlog {
    id?: number;
    name?: string;
    content?: string;
    categoryName?: string;
    categoryId?: number;
}

export class Blog implements IBlog {
    constructor(
        public id?: number,
        public name?: string,
        public content?: string,
        public categoryName?: string,
        public categoryId?: number
    ) {}
}
