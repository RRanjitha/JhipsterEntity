/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HellopghTestModule } from '../../../test.module';
import { ZonalComponent } from 'app/entities/zonal/zonal.component';
import { ZonalService } from 'app/entities/zonal/zonal.service';
import { Zonal } from 'app/shared/model/zonal.model';

describe('Component Tests', () => {
    describe('Zonal Management Component', () => {
        let comp: ZonalComponent;
        let fixture: ComponentFixture<ZonalComponent>;
        let service: ZonalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HellopghTestModule],
                declarations: [ZonalComponent],
                providers: []
            })
                .overrideTemplate(ZonalComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZonalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZonalService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Zonal(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.zonals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
