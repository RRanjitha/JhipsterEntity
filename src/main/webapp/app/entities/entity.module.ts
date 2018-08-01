import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HellopghCategoryModule } from './category/category.module';
import { HellopghBlogModule } from './blog/blog.module';

import { HellopghZonalModule } from './zonal/zonal.module';
import { HellopghSectorModule } from './sector/sector.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HellopghCategoryModule,
        HellopghBlogModule,
        HellopghZonalModule,
        HellopghSectorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HellopghEntityModule {}
