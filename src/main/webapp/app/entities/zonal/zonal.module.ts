import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HellopghSharedModule } from 'app/shared';
import {
    ZonalComponent,
    ZonalDetailComponent,
    ZonalUpdateComponent,
    ZonalDeletePopupComponent,
    ZonalDeleteDialogComponent,
    zonalRoute,
    zonalPopupRoute
} from './';

const ENTITY_STATES = [...zonalRoute, ...zonalPopupRoute];

@NgModule({
    imports: [HellopghSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ZonalComponent, ZonalDetailComponent, ZonalUpdateComponent, ZonalDeleteDialogComponent, ZonalDeletePopupComponent],
    entryComponents: [ZonalComponent, ZonalUpdateComponent, ZonalDeleteDialogComponent, ZonalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HellopghZonalModule {}
