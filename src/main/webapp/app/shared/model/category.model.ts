import { IBlog } from 'app/shared/model//blog.model';

export interface ICategory {
    id?: number;
    name?: string;
    categoryBlogs?: IBlog[];
}

export class Category implements ICategory {
    constructor(public id?: number, public name?: string, public categoryBlogs?: IBlog[]) {}
}
